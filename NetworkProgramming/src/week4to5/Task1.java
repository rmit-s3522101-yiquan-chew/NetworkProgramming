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
            netIf.
//            displaySubInterfaces(netIf);
            System.out.printf("\n");
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
}
