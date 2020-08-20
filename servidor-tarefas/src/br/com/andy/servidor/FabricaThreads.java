package br.com.andy.servidor;

import java.util.concurrent.ThreadFactory;

public class FabricaThreads implements ThreadFactory {

	private static int numero = 1;

	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(r, "ThreadServer "+numero);
		numero++;
		thread.setUncaughtExceptionHandler(new TratadorDeExcecao());
		return thread;
	}
}
