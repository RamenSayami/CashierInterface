package cashier.controller;

import cashier.View.CashierInterface;
import cashier.model.Order;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ramen
 */

public class ServerClass implements Runnable {

	public SThread clients[];
	public ServerSocket server = null;
	public Thread thread = null;
	public int clientCount = 0, port = 13000;
	public CashierInterface ui;

	// public Database db;

	public ServerClass() {
		try {
			server = new ServerSocket(port);
			port = server.getLocalPort();
			System.out.println("cashier.controller.SocketServer.<init>()");
			System.out.println("Server startet. IP : "
					+ InetAddress.getLocalHost() + ", Port : "
					+ server.getLocalPort());
			start();
		} catch (Exception e) {
			System.out.println("Can not bind to port : " + port + "\nRetrying");
			// ui.RetryStart(port+1);
		}
	}

	public ServerClass(CashierInterface frame, int Port) {

		clients = new SThread[50];
		ui = frame;
		port = Port;
		// db = new Database(ui.filePath);

		try {
			server = new ServerSocket(port);
			port = server.getLocalPort();
			System.out.println("Server startet. IP : "
					+ InetAddress.getLocalHost() + ", Port : "
					+ server.getLocalPort());
			start();
		} catch (IOException ioe) {
			System.out.println("\nCan not bind to port " + port + ": "
					+ ioe.getMessage());
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

	@Override
	public void run() {
		while (thread != null) {
			try {
				System.out.println("\nWaiting for a client ...");
				addThread(server.accept());
			} catch (Exception ioe) {
				System.out.println("\nServer accept error: \n");
				ui.RetryStart(0);
			}
		}
	}

	private void addThread(Socket socket) {
		if (clientCount < clients.length) {
			System.out.println("\nClient accepted: " + socket);
			clients[clientCount] = new SThread(this, socket);
			try {
				clients[clientCount].open();
				clients[clientCount].start();
				clientCount++;
			} catch (IOException ioe) {
				System.out.println("\nError opening thread: " + ioe);
			}
		} else {
			System.out.println("\nClient refused: maximum " + clients.length
					+ " reached.");
		}
	}

	public synchronized void handle(Order order) {
		ui.addToOrderList(order);
	}

}
