import java.net.*;
import java.io.*;

//class runnableExample implements Runnable {
//	
//	private Socket client;
//	
//	public runnableExample(Socket cSocket){
//		this.client = cSocket;
//	}
//
//	public void run() {
//		try{
//			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
//			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//			
//			String inputLine;
//			while ((inputLine = in.readLine()) != null){
//				System.out.println("Server: "+ inputLine);
//				out.println(inputLine);
//				
//				if (inputLine.equals("x")){
//					System.out.println("Server close");
//				}
//			}
//			
//			out.close();
//			in.close();
//			return;
//		}
//		catch (IOException e){
//			System.out.println("Server Error: " + e);
//		}
//		
//	}
//}