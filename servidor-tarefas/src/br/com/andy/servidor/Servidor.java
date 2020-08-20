package br.com.andy.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Servidor {
	
	private ServerSocket servidor;
	private ExecutorService threadPool;
	private AtomicBoolean rodando;
	private BlockingQueue<String> filaComandos;

	public Servidor() throws IOException {
		System.out.println(" --- Iniciando Servidor --- ");
		servidor = new ServerSocket(12345);
		threadPool = Executors.newCachedThreadPool(new FabricaThreads());
		rodando = new AtomicBoolean(true);
		filaComandos = new ArrayBlockingQueue<>(2);
		iniciarConsumidores();
	}

	private void iniciarConsumidores() {
		
		int j = 2;
		
		for (int i = 0; i < j; i++) {
			TarefaConsumir tarefa = new TarefaConsumir(filaComandos);
			threadPool.execute(tarefa);
		}
	}

	public void parar() throws IOException {
		servidor.close();
		threadPool.shutdown();
		rodando.set(false);
	}

	public void rodar() throws IOException {
		while (rodando.get()) {
			Socket socket = servidor.accept();
			System.out.println("Aceitando novo cliente na porta: " + socket.getPort());
			
			DistribuirTarefas distTarefas = new DistribuirTarefas(threadPool, filaComandos, socket, this);
			threadPool.execute(distTarefas);
		}
	}
}
