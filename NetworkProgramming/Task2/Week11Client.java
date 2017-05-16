import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Week11Client {
	public static void main(String[] args){
		try {
			ByteBuffer buf = ByteBuffer.allocate(1024);
			SocketChannel socketChannel = SocketChannel.open();
			
			//Inet address for connection
			String IPAddress = null;
			InetAddress ip;
			ip = InetAddress.getLocalHost();
			IPAddress = ip.getHostAddress();
			
			//non-blocking
			socketChannel.configureBlocking(false);
			socketChannel.connect(new InetSocketAddress(IPAddress, 9999));
			
			//if the server is blocking server, wait until finish connect
			boolean connect = socketChannel.finishConnect();
			while(!connect){
				System.out.println("Connecting...");
				connect = socketChannel.finishConnect();
			}
			
			if(connect){
				System.out.println("Successfully connect to " + IPAddress.toString() + ", port: " + 9999);
			}
			
			//scan for input and sent to serverSocketChannel
			Scanner sc = new Scanner(System.in);
			System.out.println("Start capitalizing");
			String read;
			while(connect){
				read = sc.nextLine();
				
				//write to server
				writeBuffer writeBufferThread = new writeBuffer(socketChannel, read);
				writeBufferThread.start();
				
				//reading from server
				int bytesRead = socketChannel.read(buf);
				
				readBuffer readBufferThread = new readBuffer(socketChannel, buf);
				readBufferThread.start();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class readBuffer extends Thread{
	private static SocketChannel socketChannel;
	private static ByteBuffer buffer;
	private static writeBuffer writeThread;
	
	//receive buffer and print it out
	public readBuffer(SocketChannel socketChannel, ByteBuffer buffer){
		this.socketChannel = socketChannel;
		this.buffer = buffer;
		start();
	}
	
	public void run(){
		synchronized(writeThread){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Charset charset = Charset.forName("ISO-8859-1");
			buffer.flip();
			String print = charset.decode(buffer).toString();
			
			if(print.equalsIgnoreCase("x")){
				System.out.println("Ending...");
				System.exit(0);
			}
			
			System.out.println(print);
		}				
	}
}

class writeBuffer extends Thread{
	private static SocketChannel socketChannel;
	private static String string;
	//write string to buffer and sent
	public writeBuffer(SocketChannel socketChannel, String string){
		this.socketChannel = socketChannel;
		this.string = string;
	}
	
	public void run(){
		synchronized(this){
			ByteBuffer buf = ByteBuffer.allocate(1024);
			Charset charset = Charset.forName("ISO-8859-1");
			CharBuffer cb = CharBuffer.wrap(string.toCharArray());
			buf = charset.encode(cb);
			
			try {
				socketChannel.write(buf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			notify();
		}		
	}
}
