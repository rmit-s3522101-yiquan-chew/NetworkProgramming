import java.io.*;
import java.net.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

class AssignmentTask1Server {
	//logging server
	final static Logger LOGGER = Logger.getLogger(AssignmentTask1Client.class.getName());
	
	//server generates random number
    private static int serverGNumber = 0;
    private static int combineNumber = 0;
    
    private static int serverGenerate(){
    	int min = 0;
    	int max = 2;
    	
    	int randomNum = ThreadLocalRandom.current().nextInt(min, max+1);
    	//refer to http://stackoverflow.com/questions/363681/how-to-generate-random-integers-within-a-specific-range-in-java
    	//quote: nextInt is normally exclusive of the top value, add 1 to make it inclusive.
    	return randomNum;
    }
    
    //check differences of clientGnumber and combineNumber
    public static boolean check(int clientNumber){
    	if (Math.abs(clientNumber - combineNumber) < 2){
    		return true;
    	}    	
    	return false;
    }
    
    public static void connectingClient(){
    	//initializing
    	LOGGER.info("INITIALIZING THE SERVER");
    	ServerSocket sSocket = null;
    	int serverPort = 9999;
    	
    	String inputLine;
    	BufferedReader in;
    	PrintWriter out;
    	Socket cSocket = null;
    			
    	//creating ServerSocket
    	try{
    		sSocket = new ServerSocket(serverPort);
    	}catch(IOException ioe){
    		System.out.println(ioe);
    	}
    	
    	try{
    		//client Socket
    		cSocket = sSocket.accept();
    		in = new BufferedReader(new InputStreamReader (cSocket.getInputStream()));
    		out = new PrintWriter(cSocket.getOutputStream(), true);
    		
    		//get and combine number from client
    		LOGGER.info("CONNECT WITH CLIENT");
    		
    		LOGGER.info("GETTING CLIENT NUMBER");
    		int clientGNumber = Integer.parseInt(in.readLine());
    		combineNumber = serverGNumber + clientGNumber;
    		
    		LOGGER.info("serverGNumber = " + serverGNumber);
    		LOGGER.info("clientGNumber = " + clientGNumber);
    		LOGGER.info("combineNumber = " + combineNumber);
    		    				
    		while((inputLine = in.readLine()) != null){
    			int inputInt = Integer.parseInt(inputLine);
    			LOGGER.info("CLIENT NUMBER = " + inputInt);
    			//check
    			LOGGER.info("CHECKING CLIENT NUMBER AND COMBINED NUMBER");
    			boolean check = check(inputInt);
    			
    			if(check == true){
    				LOGGER.info("CLIENT SUCCESSFULLY BET THE NUMBER");
    				out.println("You got it!");
    				break;
    			}
    			else{
    				LOGGER.info("CLIENT UNSUCCESSFULLY BET THE NUMBER");
    				out.println("Please try again. Do remember to guess between 0-4!");
    			}
    		}
    		
    		LOGGER.info("CLOSING THE SERVER");
    		out.close();
    		in.close();
    		cSocket.close();
    		sSocket.close();    				
    	}catch(Exception e){
    		System.out.println(e);
    	}
    }
	
    //handling log
    public static void logHandle(){
    	Handler fileHandler = null;
    	Formatter simpleFormatter = null;
    	try {
    		//save log to file
    		fileHandler = new FileHandler("./Task1Server.log");
    		//format log in readable format
    		simpleFormatter = new SimpleFormatter();
    		
			LOGGER.addHandler(fileHandler);
			
			//setting formatter to handler
			fileHandler.setFormatter(simpleFormatter);
			fileHandler.setLevel(Level.ALL);
			
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	public static void main(String[] args){
		logHandle();
		serverGNumber = serverGenerate();
		connectingClient();		
	}
}
