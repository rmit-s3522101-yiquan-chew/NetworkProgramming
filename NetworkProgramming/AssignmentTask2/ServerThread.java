import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

//thread for handling multiple client
public class ServerThread extends Thread{
	//logging server
	final static Logger LOGGER = Logger.getLogger(AssignmentTask2Server.class.getName());
	
	String line = null;
	static BufferedReader in = null;
	static PrintWriter pw = null;
	Socket socket = null;
	static List<Socket> waitingList = new ArrayList<Socket>();
	private static int serverGNumber;
	private static int combineNumber;
	
	public ServerThread(Socket socket, int serverGNumber){
		this.socket = socket;
		this.serverGNumber = serverGNumber;
	}
		    
    //check differences of clientGnumber and combineNumber
    public static boolean check(int clientNumber){
    	if (Math.abs(clientNumber - combineNumber) <= 1){
    		return true;
    	}    	
    	return false;
    }
    
    //select and combine number from client list
    public static List<Socket> selected(List<Socket> waitingList) throws IOException{
    	List<Socket> selectedList = new ArrayList<Socket>();
    	
    	if(waitingList.size() > 3){
			//select only 3 client
			for(int i=0; i<3; i++){
    			Socket tempSocket = waitingList.get(i);
    			selectedList.add(tempSocket);
    			pw = new PrintWriter(tempSocket.getOutputStream(), true);
    			pw.println("You are the choosen one!!!");			    			
    		}
		}
		//else, select all
		else{
			for(int i=0; i<waitingList.size(); i++){
    			Socket tempSocket = waitingList.get(i);
    			selectedList.add(tempSocket);
    			pw = new PrintWriter(tempSocket.getOutputStream(), true);
    			pw.println("You are the choosen one!!!");			    			
    		}
		}
    	return selectedList;
    }
    
    //get and combine number from selected socket
    public static int getConbineNumber(List<Socket> selectedList) throws IOException{
    	for(int i=0; i<selectedList.size(); i++){
    		Socket tempSocket = selectedList.get(i);
    		in = new BufferedReader(new InputStreamReader(tempSocket.getInputStream()));
    		int clientGNumber = Integer.parseInt((in.readLine()));
    		combineNumber += clientGNumber;
    	}    	
    	return combineNumber;
    }
        
	//betting function
    public void readFromClient(Socket cSocket){
    	String inputLine;
    	
    	try{
    		//client Socket
    		in = new BufferedReader(new InputStreamReader (cSocket.getInputStream()));
    		pw = new PrintWriter(cSocket.getOutputStream(), true);
    		
    		LOGGER.info("CONNECT WITH CLIENT");
    			    		
    		LOGGER.info("GETTING CLIENT");

    		while((inputLine = in.readLine()) != null){
    			/* registering client
	    		 * register and put client on waiting list 
	    		 * client enter "r" to register
	    		 */
    			System.out.println("Waiting client to register");
	    		if(inputLine.equals("r")){
	    			waitingList.add(cSocket);
	    			System.out.println("Successfully registered");
	    			pw.println("Successfully registered");
//	    			//test print arraylist
//	    			for(int j=0; j<waitingList.size(); j++){
//	    				System.out.println(waitingList.get(j));
//	    			}
	    		}
    		}
    		
    		LOGGER.info("CLOSING THE SERVER");
    		pw.close();
    		in.close(); 				
    	}catch(Exception e){
    		System.out.println(e);
    	}
    }
	
	public void run(){
		try{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(socket.getOutputStream());
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		
		try{
			readFromClient(socket);
		}
		catch(NullPointerException npe){
			//when client is closed
			npe.printStackTrace();
		}
		
		try{
			in.close();
			pw.close();
			socket.close();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
}

