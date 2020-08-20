package br.com.andy.cliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	public static void main(String[] args) throws Exception {
		
		Socket socket = new Socket("localhost", 12345);
		System.out.println("Conexão Estabelecida");
		
		Thread enviaComando = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println("Pode enviar comandos!");
					PrintStream saida = new PrintStream(socket.getOutputStream());
					saida.println("c1");
					
					Scanner tec = new Scanner(System.in);
					
					while (tec.hasNextLine()) {
						String linha = tec.nextLine();
						
						if (linha.equals(""))
							break;
						
						saida.println(linha);
					}
					
					tec.close();
					saida.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}, "Envia");
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println("Recebendo dados do servidor");
					Scanner respServer = new Scanner(socket.getInputStream());
					
					while(respServer.hasNextLine()) 
						System.out.println(respServer.nextLine());
					
					respServer.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}, "Recebe").start();
		
		enviaComando.start();
		enviaComando.join();
		
		System.out.println("Fechando socket do cliente");
		socket.close();
	}
}
