import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AssignmentTask2Server {
	//logging server
	final static Logger LOGGER = Logger.getLogger(AssignmentTask2Server.class.getName());
	
	//server generates random number
    private static int serverGNumber = 0;
    private static int serverGenerate(){
    	int min = 0;
    	int max = 2;
    	
    	int randomNum = ThreadLocalRandom.current().nextInt(min, max+1);
    	//refer to http://stackoverflow.com/questions/363681/how-to-generate-random-integers-within-a-specific-range-in-java
    	//quote: nextInt is normally exclusive of the top value, add 1 to make it inclusive.
    	return randomNum;
    }
    
    public static void connectingClient(){
    	//initializing
    	LOGGER.info("INITIALIZING THE SERVER");
    	ServerSocket sSocket = null;
    	int serverPort = 9999;
    	
    	//creating ServerSocket
    	try{
    		sSocket = new ServerSocket(serverPort, 2);
    	}catch(IOException ioe){
    		System.out.println(ioe);
    	}
    	
    	//preparing 5 socket for 5 client
    	Socket cSocket[] = new Socket[2];
    	
    	//to continually accepting client
    	while(true && cSocket[cSocket.length-1] == null){
    		//bet function
    		try{
    			for(int i=0; i<cSocket.length; i++){
    				if(cSocket[i] != null){
    					LOGGER.info("MAXIMUM CONNECTED CLIENT REACHED.");
    				}
    				cSocket[i] = sSocket.accept();
    				System.out.println("cSocket[" + i + "] = " + cSocket[i].toString());
        			ServerThread sThread = new ServerThread(cSocket[i], serverGNumber);
        			sThread.start();
    			}
    		}catch(IOException ioe){
    			ioe.printStackTrace();
    		}
    	}
    }
	
    //handling log
    public static void logHandle(){
    	Handler fileHandler = null;
    	Formatter simpleFormatter = null;
    	try {
    		//save log to file
    		fileHandler = new FileHandler("./Task2Server.log");
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
		System.out.println(serverGNumber);
		connectingClient();		
	}
	
	
	
	
}
