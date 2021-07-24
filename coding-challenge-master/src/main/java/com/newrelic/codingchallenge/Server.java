package com.newrelic.codingchallenge;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
	private static final int MAX_CLIENTS = 5;
	private ServerSocket serverSocket;

	public void start(int port) {
		ExecutorService executorService = Executors.newFixedThreadPool(MAX_CLIENTS);
		BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>();
		try {	
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				ClientHandler clientHandler = new ClientHandler(serverSocket.accept(), blockingQueue);
				executorService.submit(clientHandler);
				executorService.shutdown();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}