/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import View.CashierInterface;

/**
 *
 * @author shreejal
 */

public class SocketServer implements Runnable {
	public ServerThread clients[];
	public ServerSocket server = null;
	public Thread thread = null;
	public int clientCount = 0, port = 13000;
	public CashierInterface ui;

	// public Database db;

	public SocketServer(CashierInterface frame) {
		clients = new ServerThread[50];
		ui = frame;
		try {
			server = new ServerSocket(port);
			port = server.getLocalPort();
			System.out.println("Server started IP : "
					+ this.server + ", Port : "
					+ server.getLocalPort());
			start();
		} catch (IOException ioe) {
			System.out.println("Can not bind to port : " + port + "\nRetrying");
			ui.RetryStart(0);
		}
	}

	public SocketServer(CashierInterface frame, int Port) {
		clients = new ServerThread[50];
		ui = frame;
		port = Port;
		try {
			server = new ServerSocket(port);
			port = server.getLocalPort();
			System.out.println("Server startet. IP : "
					+ server.getLocalSocketAddress() + ", Port : "
					+ server.getLocalPort());
			start();
		} catch (IOException ioe) {
			System.out.println("\nCan not bind to port " + port + ": "
					+ ioe.getMessage());
		}
	}

	public void run() {
		while (thread != null) {
			try {
				System.out.println("\nWaiting for a client ...");
				addThread(server.accept());
			} catch (Exception ioe) {
				System.out.println("\nServer error: \n");
				ui.RetryStart(0);
			}
		}
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	@SuppressWarnings("deprecation")
	public void stop() {
		if (thread != null) {
			thread.stop();
			thread = null;
		}
	}

	private void addThread(Socket socket) {
		System.out.println("\nClient accepted: " + socket);
		clients[clientCount] = new ServerThread(this, socket);
		try {
			clients[clientCount].open();
			clients[clientCount].start();
			clientCount++;
		} catch (IOException ioe) {
			System.out.println("\nError opening thread: " + ioe);
		}
	}

	public int find(String name){
		ServerThread thhread;
	
		for(int i =0; i<clientCount;i++){
			
			thhread = clients[i].getThis();
			String name1= thhread.ClientName;
			if(name1.equals(name.trim())){
				return i;
			}
		}
		return -1;
	}
}
