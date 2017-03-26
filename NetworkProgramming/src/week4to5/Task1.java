package week4to5;

import java.io.*;
import java.net.*;
import java.util.*;

public class Task1 {
	public static void task1() throws SocketException, IOException{
		Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = InetAddress.getLocalHost();
		NetworkInterface network = NetworkInterface.getByInetAddress(ip);
		byte[] mac = network.getHardwareAddress();
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
		}
		System.out.println(sb.toString());
		
		String hName = ip.getHostName();
		String hAddress = ip.getHostAddress();
		
		System.out.println(hName);
		System.out.println(hAddress);
		System.out.println("This is a new line ===================>");
		System.out.println();
		
		for (NetworkInterface netIf : Collections.list(nets)) {
            System.out.printf("Display name: %s\n", netIf.getDisplayName());
            System.out.printf("Name: %s\n", netIf.getName());
        }
		
	}

	private static void displaySubInterfaces(NetworkInterface netIf) {
		// TODO Auto-generated method stub
		Enumeration<NetworkInterface> subIfs = netIf.getSubInterfaces();
        
        for (NetworkInterface subIf : Collections.list(subIfs)) {
            System.out.printf("\tSub Interface Display name: %s\n", subIf.getDisplayName());
            System.out.printf("\tSub Interface Name: %s\n", subIf.getName());
        }
	}
	
	public static void task2Server() throws Exception{
		/*
		 * According to notes:
		 * - Hosts have ports, 0->65535, server listen on a port
		 * - Multiple clients can be communicating with a server on a given port,
		 * each client is assigned a separate socket
		 * - Client get port n a socket on client machine when they successfully connect with a server
		 */
		
		System.out.println("Server running");
		int clientNumber = 0;
		ServerSocket listener = new ServerSocket(2017);
		Socket clientSocket = listener.accept();
		
		//reading from client
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
		
		System.out.println("Connection setup with client #" + clientNumber + "at " + clientSocket);
		out.println("Please enter a line you would like to capitalized. Enter \"X\" to end the program.");
		
		while (true){
			String input = in.readLine();
			//end cndition
			if (input == null || input.equals("X")){
				break;
			}
			
			//capitalized the line
			out.println(input.toUpperCase());
			
			//write to file
			
			
			//close the socket
			clientSocket.close();
		}
	}
	
	public static void task2Client() {
		BufferedReader in;
		PrintWriter out;
	}
}
