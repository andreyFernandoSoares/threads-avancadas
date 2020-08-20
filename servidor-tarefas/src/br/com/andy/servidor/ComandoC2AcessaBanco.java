package br.com.andy.servidor;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC2AcessaBanco implements Callable<String> {

	private PrintStream saidaCli;

	public ComandoC2AcessaBanco(PrintStream saidaCli) {
		this.saidaCli = saidaCli;
	}

	@Override
	public String call() throws Exception {
		System.out.println("Servidor recebeu o comando c2 - banco");
		
		saidaCli.println("Processando comando C2");
		
		Thread.sleep(25000);
		
		int numero = new Random().nextInt(100) + 1;
		
		saidaCli.println("Servidor finalizou Comando c2 - banco");
		
		return Integer.toString(numero);
	}
}
