import java.net.*;
import java.io.*;
import java.util.*;
 
public class SecretServer {
 
	public static void main(String[] args) {
 
		String[] secret = {"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN"};


		DatagramSocket ss = null;
		DatagramPacket rp, sp;
		byte[] rd, sd;

		InetAddress ip;
		int port;
 
		try {
			ss = new DatagramSocket(Integer.parseInt(args[0]));
			System.out.println("Server is up....");
			System.out.println("");

			int consignment=0;
			String strGreeting;
			boolean end=true;
			int result = 0; // number of bytes read
	 
			while(end){
	 
				rd=new byte[100];
				sd=new byte[512];
				 
				rp = new DatagramPacket(rd,rd.length);
				 
				ss.receive(rp);
				 
				// get client's consignment request from DatagramPacket
				ip = rp.getAddress(); 
				port =rp.getPort();
				System.out.println("Client IP Address = " + ip);
				System.out.println("Client port = " + port);

				strGreeting = new String(rp.getData());
				System.out.println("Client says = " + strGreeting);

				// prepare data
				if (consignment == secret.length) { // last consignment
					sd = new String("END").getBytes();
					end = false;
				} else {
					sd = new String("RDT"+String.valueOf(consignment)+secret[consignment]).getBytes();
				}
				sp=new DatagramPacket(sd,sd.length,ip,port);
				 
				// send data
				ss.send(sp);
				System.out.println("");
				System.out.println("Sent Consignment #" + consignment);
				//System.out.println("RDT" + consignment + secret[consignment]);
	 
				rp=null;
				sp = null;
				 
				if (consignment == secret.length) { 
					consignment = 0; // reset consignment after last SECRET is delivered
					} 
				else {
					consignment++;
				     }
	 
			} // while true

		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		
	}
}
