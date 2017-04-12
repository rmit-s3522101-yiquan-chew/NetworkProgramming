package main;

import java.io.IOException;
import java.net.*;

import week1to3.*;
import week4to5.*;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		week1to3.Task1 a1t1 = null;
		week1to3.Task2 a1t2 = null;
		week4to5.Task1 a2t1 = null;
		week4to5.Task2 a2t2 = null;
		
//		String uLetters = a1t1.task1a();
//		a1t1.task1aOutput(uLetters);
//		a1t1.task1b();
//		
//		a1t2.task2a();
//		a1t2.task2b();
		
<<<<<<< HEAD
		a2t1.task1();
=======
//		a2t1.task1();
		
		/**
		 * The following code is based on http://cs.lmu.edu/~ray/notes/javanetexamples/
		 * Its an application make the server runs in infinite loop on port 2017
		 */
		//server connection setup
		System.out.println("Running capitalization server");
		int cNumber = 0;
		ServerSocket listener = new ServerSocket(2017);
		try{
			while(true){
				a2t2.task2Server(listener.accept(), cNumber++);
			}
		}finally{
			listener.close();
		}
//		a2t2.task2Client(2017);
>>>>>>> branch 'master' of https://github.com/rmit-s3522101-yiquan-chew/NetworkProgramming.git
		
		
	}

}
