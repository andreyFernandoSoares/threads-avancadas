package br.com.andy.servidor;

import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JuntaResultadoFuture implements Callable<String> {

	private Future<String> futureWS;
	private Future<String> futureBanco;
	private PrintStream saidaCli;

	public JuntaResultadoFuture(Future<String> futureWS, Future<String> futureBanco, PrintStream saidaCli) {
		this.futureWS = futureWS;
		this.futureBanco = futureBanco;
		this.saidaCli = saidaCli;
	}

	@Override
	public String call(){
		
		System.out.println("Aguardando resultado do future ws e banco");
		
		try {
			String numeroMagico = this.futureWS.get(20, TimeUnit.SECONDS);
			String numeroMagico2 = this.futureBanco.get(20, TimeUnit.SECONDS);
			
			this.saidaCli.println("Resultado comando C2: "+ numeroMagico +", "+numeroMagico2);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			System.out.println("Cancelando execucao do comando c2");
			this.saidaCli.println("Saiu fora do tempo de execuccao no comando c2");
			this.futureWS.cancel(true);
			this.futureBanco.cancel(true);
		}  
		
		System.out.println("Finalizou JuntaResultadoFuture");
		return null;
	}
}
