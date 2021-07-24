package com.newrelic.codingchallenge;


public class Main {

	public static void main(String[] args) {
    	final int PORT = 4000;
        System.out.println("Starting up server ....");
        Server server = new Server();
        server.start(PORT);
    }
}