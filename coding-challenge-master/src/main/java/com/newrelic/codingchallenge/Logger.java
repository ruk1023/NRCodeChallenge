package com.newrelic.codingchallenge;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class Logger  {

	private BlockingQueue<Integer> blockingQueue;
	private BufferedWriter bufferedWriter;
	public Logger(BlockingQueue<Integer> blockingQueue) {
		super();
		this.blockingQueue = blockingQueue;
	}

	public void run() {

		try(FileWriter fileWriter = new FileWriter("numbers.log", false)) {
			bufferedWriter = new BufferedWriter(fileWriter);;
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (true) {
			while (!blockingQueue.isEmpty()) {
				int num = blockingQueue.poll();
				try {
					bufferedWriter.write(num);
					bufferedWriter.newLine();
					bufferedWriter.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

}
