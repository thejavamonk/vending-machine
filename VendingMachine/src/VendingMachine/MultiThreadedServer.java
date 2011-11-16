package VendingMachine;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class MultiThreadedServer implements Runnable {
	
	private ServerSocket serverSocket = null;
	private Thread runningThread = null; 
	private int port;
	
	public MultiThreadedServer(int port) {
		this.port = port;
	}
	
	public void run() {
		synchronized(this) {
			this.runningThread = Thread.currentThread();
		}
		Socket clientSocket = null;
		try {
			this.startServer();
			clientSocket = this.serverSocket.accept();
			new Thread(new VendingMachine(clientSocket, "Multi-Threaded Server")).start();
		} catch (IOException e) {
			System.out.println("Error during server connection initialization.");
		}
	}
	
	public void startServer() {
		try {
			this.serverSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			System.out.println("Error during server socket instanciation.");
		}
	}
	
	public static void main(String[] args) {
		MultiThreadedServer server = new MultiThreadedServer(9000);
		new Thread(server).start();
	}
}
