import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Task2Client {
//	private static BufferedReader in;
//	private static PrintWriter out;
//	
//	public static void task2cResponse(){
//		System.out.println("im alive");
//		Scanner sc = new Scanner(System.in);
//		String response = sc.nextLine();	
//		out.println(response);
////        String response;
//	    try {
//	         response = in.readLine();
//	         if (response == null || response.equals("X")) {
//	               System.exit(0);
//	           }
//
////	 		out.println(sc.nextLine());
//	     } catch (Exception ex) {
//	            response = "Error: " + ex;
//	     }
//	     System.out.println(response);
//	}
//	
//	public static void connectToServer() throws UnknownHostException, IOException{
//		//getting server IP address
//		String IPAddress = null;
//		InetAddress ip;
//		ip = InetAddress.getLocalHost();
//		IPAddress = ip.getHostAddress();
//		
//		//initialize Streams
//		Socket socket = new Socket(IPAddress, 9898);
//		in = new BufferedReader(
//                new InputStreamReader(socket.getInputStream()));
//        out = new PrintWriter(socket.getOutputStream(), true);        
//	}
	
	public static void main(String[] args) throws Exception{
		Socket cSocket = null;
		DataOutputStream dOut = null;
		DataInputStream dIn = null;
		
		try{
			//getting server IP address
			String IPAddress = null;
			InetAddress ip;
			ip = InetAddress.getLocalHost();
			IPAddress = ip.getHostName();
			System.out.println(IPAddress);
			
			cSocket = new Socket(IPAddress, 9999);
			System.out.println(cSocket);
			dOut = new DataOutputStream(cSocket.getOutputStream());
			dIn = new DataInputStream(cSocket.getInputStream());
		}catch(Exception e){
			System.out.println(e);
		}
		
		if(cSocket != null && dOut != null && dIn != null){
			try{
				dOut.writeBytes("testing");
				
				String response = dIn.readLine();
				do{
					
					System.out.println("Server: " + response);
					if (response.indexOf("Ok") != -1) {
	                      break;
	                    }
				}while(!response.equals(null));
				dOut.close();
				dIn.close();
				cSocket.close();
			}catch(Exception e){
				System.out.println(e);
			}
		}
	}
}
