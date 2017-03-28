package week4to5;

import java.io.*;
import java.net.*;
import java.util.*;

public class Task1 {
	public static void task1() throws SocketException, IOException{
		Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
		
		for (NetworkInterface netIf : Collections.list(nets)) {
            byte[] mac = netIf.getHardwareAddress();
            if(mac != null){
            	StringBuilder sb = new StringBuilder();
        		for (int i = 0; i < mac.length; i++) {
        			sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        		}
        		System.out.printf("Display name: %s\n", netIf.getDisplayName());
                System.out.printf("Name: %s\n", netIf.getName());
        		System.out.printf("Hardware Address: %s\n\n", sb.toString());
            }
        }		
	}

}
