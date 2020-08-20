package br.com.andy.servidor;

import java.net.SocketException;

public class StartServer {
	
	public static void main(String[] args) throws Exception {
		try {
			Servidor servidor = new Servidor();
			servidor.rodar();
		} catch (SocketException e) {
			System.out.println("Finalizando!");
		}
	}
}
