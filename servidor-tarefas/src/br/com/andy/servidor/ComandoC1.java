package br.com.andy.servidor;

import java.io.PrintStream;

public class ComandoC1 implements Runnable {

	private PrintStream saidaCli;

	public ComandoC1(PrintStream saidaCli) {
		this.saidaCli = saidaCli;
	}

	@Override
	public void run() {
		System.out.println("Executando comando c1");
		
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		
		saidaCli.println("Comando c1 executado com sucesso");
	}
}
