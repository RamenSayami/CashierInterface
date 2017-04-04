package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import View.CashierInterface;

import com.example.ramen.menu.Model.IdentifySelf;
import com.example.ramen.menu.Model.Order;
import com.example.ramen.menu.Model.OrderStatus;

public class ServerThread extends Thread {

	public String ClientName;
	public SocketServer server = null;
	public Socket socket = null;
	public int ID = -1;
	public String username = "";
	public ObjectInputStream streamIn;
	public ObjectOutputStream streamOut;
	public CashierInterface ui;
	public Object object;

	public ServerThread(SocketServer _server, Socket _socket) {
		super();
		server = _server;
		socket = _socket;
		ID = socket.getPort();
		ui = _server.ui;
	}

	public void send(Order msg) {
		try {
			streamOut.writeObject(msg);
			streamOut.flush();
			
			System.out.println("sent "+msg.getDishName()+" to kitchen");
		} catch (IOException ex) {
			System.out.println("Exception [SocketClient : send(...)]");
		}
	}

	public int getID() {
		return ID;
	}

	@SuppressWarnings("deprecation")
	public void run() {
		System.out.println("\nServer Thread " + ID + " running.");
		while (socket.isConnected()) {
			try {
				Object obj = streamIn.readObject();
				this.object = obj;
				Order order = (Order) this.object;
				if (order.getStatusString().equals(
						OrderStatus.ORDER_IN.toString())) {
					System.out.println("Sending "+ order.getDishName()+" to kitchen");
					ui.sendToKitchen(order);
					continue;
				}

				if (ui.billingList.containsKey(order.getTableNo())) {
					List<Order> tempList = ui.billingList.get(order
							.getTableNo());
					tempList.add(order);
					ui.billingList.put(order.getTableNo(), tempList);
					System.out.println("Added in billing map");
				} else {
					List<Order> tempList = new ArrayList<Order>();
					tempList.add(order);
					ui.billingList.put(order.getTableNo(), tempList);
					System.out.println("Added in billing map");
				}

			} catch (Exception ioe) {
				// try to read IdentifySelf object
				System.out.println("Couldnot parse Order Object");
				try {
					IdentifySelf id = (IdentifySelf) this.object;
					// TODO future feature, cant have same named clients,
					// because kitchen can be only one.
					this.ClientName = id.getName();
					System.out.println("Client Name: " + ClientName);
				} catch (Exception e) {
					System.out.println(ID + " ERROR reading Object(s): "
							+ e.getMessage());
					ioe.printStackTrace();
					stop();
				}
			}

		}
	}

	public void open() throws IOException {
		streamOut = new ObjectOutputStream(socket.getOutputStream());
		streamOut.flush();
		streamIn = new ObjectInputStream(socket.getInputStream());
	}

	public void close() throws IOException {
		if (socket != null)
			socket.close();
		if (streamIn != null)
			streamIn.close();
		if (streamOut != null)
			streamOut.close();
	}

	public ServerThread getThis() {
		return this;
	}

}
