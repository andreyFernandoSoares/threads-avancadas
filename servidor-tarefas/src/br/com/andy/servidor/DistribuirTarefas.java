package br.com.andy.servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class DistribuirTarefas implements Runnable {

	private Socket socket;
	private Servidor servidor;
	private ExecutorService threadPool;
	private BlockingQueue<String> filaComandos;

	public DistribuirTarefas(ExecutorService threadPool, BlockingQueue<String> filaComandos, Socket socket, Servidor servidor) {
		this.threadPool = threadPool;
		this.filaComandos = filaComandos;
		this.socket = socket;
		this.servidor = servidor;
	}

	@Override
	public void run() {
		try {
			System.out.println("Distribuindo tarefas para "+this.socket);
			
			Scanner entrada = new Scanner(this.socket.getInputStream());
			
			PrintStream saidaCli = new PrintStream(this.socket.getOutputStream());
			
			while (entrada.hasNextLine()) {
				String comando = entrada.nextLine();
				System.out.println("Pode enviar comandos!");
				
				switch(comando) {
					case "c1": {
						saidaCli.println("Confirmação comando C1");
						ComandoC1 c1 = new ComandoC1(saidaCli);
						threadPool.execute(c1);
						break;
					}
					case "c2": {
						saidaCli.println("Confirmação comando C2");
						ComandoC2ChamaWS c2 = new ComandoC2ChamaWS(saidaCli);
						ComandoC2AcessaBanco c2b = new ComandoC2AcessaBanco(saidaCli);
						Future<String> futureWS = threadPool.submit(c2);
						Future<String> futureBanco = threadPool.submit(c2b);
						
						threadPool.submit(new JuntaResultadoFuture(futureWS, futureBanco, saidaCli));
						break;
					}
					case "c3": {
						this.filaComandos.put(comando);
						saidaCli.println("Comando c3 adicionado a fila");
					}
					case "fim": {
						saidaCli.println("Desligando servidor");
						servidor.parar();
						break;
					}
					default: {
						saidaCli.println("Comando não encontrado");
					}
				}
			}
			
			saidaCli.close();
			entrada.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
