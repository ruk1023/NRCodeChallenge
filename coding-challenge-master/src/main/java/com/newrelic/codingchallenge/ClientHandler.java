package com.newrelic.codingchallenge;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Pattern;

public class ClientHandler implements Runnable {
	private Socket clientSocket;
	BlockingQueue<Integer> blockingQueue;

	public ClientHandler(Socket socket, BlockingQueue<Integer> blockingQueue) {
		this.clientSocket = socket;
		this.blockingQueue = blockingQueue;
	}

	@Override
	public void run() {

		//try-with-resource
		try (BufferedReader in = new BufferedReader(
				new InputStreamReader(clientSocket.getInputStream()))) {
			String inputLine = "";
			System.out.println("Client connection started");
			while (true) {
				inputLine = in.readLine();
				if (inputLine == null || inputLine.equals("terminate")) {
					throw new Exception();
				}
				int num = processNumber(inputLine);
				if (num == -1) {
					throw new Exception();
				}
				blockingQueue.add(num);

				Logger logger = new Logger(blockingQueue);
				logger.run();
			}
		}
		catch (Exception e) { // catch closes all resources
			e.printStackTrace();
		}

	}

	private int processNumber(String inputLine) {
		
		Pattern numberPattern = Pattern.compile("\\d{9}");
		if (!numberPattern.matcher(inputLine).matches() || inputLine.length() != 9) {
			return -1; //Invalid format, terminate client
		}

		try {
			return Integer.parseInt(inputLine) > 0 ? Integer.parseInt(inputLine) : -1; // Check negative values
		} catch(NumberFormatException e) {  //Invalid format, terminate client
			return -1;
		}
	}
}
