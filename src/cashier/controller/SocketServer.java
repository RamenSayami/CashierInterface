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


class ServerThread extends Thread { 
	
    public SocketServer server = null;
    public Socket socket = null;
    public int ID = -1;
    public String username = "";
    public ObjectInputStream streamIn  =  null;
    public ObjectOutputStream streamOut = null;
    public CashierInterface ui;

    public ServerThread(SocketServer _server, Socket _socket){  
    	super();
        server = _server;
        socket = _socket;
        ID     = socket.getPort();
        ui = _server.ui;
    }
    
    public void send(Order msg){
        try {
            streamOut.writeObject(msg);
            streamOut.flush();
        } 
        catch (IOException ex) {
            System.out.println("Exception [SocketClient : send(...)]");
        }
    }
    
    public int getID(){  
	    return ID;
    }
   
    @SuppressWarnings("deprecation")
	public void run(){  
//    	System.out.println("\nServer Thread " + ID + " running.");
        while (true){  
    	    try{  
                Order msg = (Order) streamIn.readObject();
    	    	server.handle( msg);
            }
            catch(Exception ioe){  
            	System.out.println(ID + " ERROR reading: " + ioe.getMessage());
//                server.remove(ID);
                stop();
            }
        }
    }
    
    public void open() throws IOException {  
        streamOut = new ObjectOutputStream(socket.getOutputStream());
        streamOut.flush();
        streamIn = new ObjectInputStream(socket.getInputStream());
    }
    
    public void close() throws IOException {  
    	if (socket != null)    socket.close();
        if (streamIn != null)  streamIn.close();
        if (streamOut != null) streamOut.close();
    }
}




public class SocketServer implements Runnable{

     
    public ServerThread clients[];
    public ServerSocket server = null;
    public Thread thread = null;
    public int clientCount = 0, port = 13000;
    public CashierInterface ui;
//    public Database db;
    
    public SocketServer(){
        try{  
	    server = new ServerSocket(port);
            port = server.getLocalPort();
            System.out.println("cashier.controller.SocketServer.<init>()");
	    System.out.println("Server startet. IP : " + InetAddress.getLocalHost() + ", Port : " + server.getLocalPort());
	    start(); 
        }
	catch(IOException ioe){  
            System.out.println("Can not bind to port : " + port + "\nRetrying"); 
            ui.RetryStart(port+1);
	}
    }

     public SocketServer(CashierInterface frame, int Port){
       
        clients = new ServerThread[50];
        ui = frame;
        port = Port;
//        db = new Database(ui.filePath);
        
	try{  
	    server = new ServerSocket(port);
            port = server.getLocalPort();
	    System.out.println("Server startet. IP : " + InetAddress.getLocalHost() + ", Port : " + server.getLocalPort());
	    start(); 
        }
	catch(IOException ioe){  
            System.out.println("\nCan not bind to port " + port + ": " + ioe.getMessage()); 
	}
    }
     
     public void start(){  
    	if (thread == null){  
            thread = new Thread(this); 
	    thread.start();
	}
    }
   @SuppressWarnings("deprecation")
    public void stop(){  
        if (thread != null){  
            thread.stop(); 
	    thread = null;
	}
    }

    @Override
    public void run() {
       while (thread != null){  
            try{  
		System.out.println("\nWaiting for a client ..."); 
	        addThread(server.accept()); 
	    }
	    catch(Exception ioe){ 
                System.out.println("\nServer accept error: \n");
                ui.RetryStart(0);
	    }
        }
    }
    
    private void addThread(Socket socket){  
	if (clientCount < clients.length){  
            System.out.println("\nClient accepted: " + socket);
	    clients[clientCount] = new ServerThread(this, socket);
	    try{  
	      	clients[clientCount].open(); 
	        clients[clientCount].start();  
	        clientCount++; 
	    }
	    catch(IOException ioe){  
	      	System.out.println("\nError opening thread: " + ioe); 
	    } 
	}
	else{
            System.out.println("\nClient refused: maximum " + clients.length + " reached.");
	}
    }
    
     public synchronized void handle(Order msg){  
         ui.addToOrderList(msg);
     }
     
    
    
}
