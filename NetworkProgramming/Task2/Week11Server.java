import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;

public class Week11Server {
	public static void main(String[] args){
		blockingServerSocketChannel();
	}
	
	public static void blockingServerSocketChannel(){
		try {
			//Inet address for connection
			String IPAddress = null;
			InetAddress ip;
			ip = InetAddress.getLocalHost();
			IPAddress = ip.getHostAddress();
			
			ServerSocketChannel serverChannel = ServerSocketChannel.open();
			serverChannel.socket().bind(new InetSocketAddress(IPAddress, 9999));
			SocketChannel socketChannel = serverChannel.accept();
			System.out.println(socketChannel.toString());
			ByteBuffer buf = ByteBuffer.allocate(1024);
//			int bytesRead = socketChannel.read(buf);
			
			boolean check;
			while(check = socketChannel.isConnected()){
				
				int bytesRead = socketChannel.read(buf);
				if(bytesRead == -1){
					socketChannel.close();
				}else{
					
					//receiving form channel
					ReceiveBuffer rb = new ReceiveBuffer(socketChannel, buf);
					rb.start();
					
					//send back to channel
					CapitalizeBuffer cb = new CapitalizeBuffer(socketChannel, buf, rb);
					cb.start();
//					buf.clear();
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class CapitalizeBuffer extends Thread{
	private static SocketChannel socketChannel;
	private static ByteBuffer buffer;
	private static ReceiveBuffer receiveThread;
	
	//receive buffer and print it out
	public CapitalizeBuffer(SocketChannel socketChannel, ByteBuffer buffer, ReceiveBuffer receiveThread){
		this.socketChannel = socketChannel;
		this.buffer = buffer;
		this.receiveThread = receiveThread;
	}
	

	//buffer draining, capitalization should be done here
	private static ByteBuffer capitalizeBuffer(ByteBuffer buffer){
		CharBuffer cb;
		ByteBuffer cBuf = ByteBuffer.allocate(1024);
		Charset charset = Charset.forName("ISO-8859-1");
		while(buffer.hasRemaining()){
			String capitalize = (charset.decode(buffer)).toString();
			
			cb = CharBuffer.wrap((capitalize.toUpperCase()).toCharArray());
			cBuf = charset.encode(cb);
		}
		return cBuf;
	}
	
	public void run(){
		synchronized(receiveThread){
			try {
				receiveThread.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Charset charset = Charset.forName("ISO-8859-1");
			buffer.flip();
			String print = "From Channel: " + charset.decode(buffer).toString();
			System.out.println(print);
			
			//capitalize
			buffer = capitalizeBuffer(buffer);
			
			//send back to channel
			try {
				socketChannel.write(buffer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			buffer.clear();
		}				
	}
}

class ReceiveBuffer extends Thread{
	private static SocketChannel socketChannel;
	private static ByteBuffer buffer;
	
	//write string to buffer and sent
	public ReceiveBuffer(SocketChannel socketChannel, ByteBuffer buffer){
		this.socketChannel = socketChannel;
		this.buffer = buffer;
	}
	
	public void run(){
		synchronized(this){			
			try {
				socketChannel.read(buffer);
				buffer.flip();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			notifyAll();
		}		
	}
}
