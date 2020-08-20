package br.com.andy.servidor;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC2ChamaWS implements Callable<String> {

	private PrintStream saidaCli;

	public ComandoC2ChamaWS(PrintStream saidaCli) {
		this.saidaCli = saidaCli;
	}

	@Override
	public String call() throws Exception {
		System.out.println("Servidor recebeu o comando c2 - WS");
		
		saidaCli.println("Processando comando C2");
		
		Thread.sleep(20000);
		
		int numero = new Random().nextInt(100) + 1;
		
		saidaCli.println("Servidor finalizou Comando c2 - WS");
		
		return Integer.toString(numero);
	}
}
