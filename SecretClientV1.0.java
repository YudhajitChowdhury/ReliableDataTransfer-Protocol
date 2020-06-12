import java.net.*;
import java.io.*;
import java.util.*;
 
public class SecretClient {
 
	public static void main(String[] args) {
	 
	    DatagramSocket cs = null;
	    int i=1; 

		try {
			cs = new DatagramSocket();

			byte[] rd, sd;
			String GREETING = "REQUESTHELLO";
			String reply;
			DatagramPacket sp,rp;
			boolean end = false;
		
		//initializing the communication
		sd = GREETING.getBytes();
		sp = new DatagramPacket(sd,sd.length,InetAddress.getByName(args[0]),Integer.parseInt(args[1]));
		cs.send(sp);
		
		

			while(!end)
			{   	  
			
				// get next consignment
				rd=new byte[512];
				rp=new DatagramPacket(rd,rd.length); 
			   	 cs.receive(rp);	

				// print SECRET
				reply=new String(rp.getData());	 
				System.out.println(reply);
				String r = new String(reply.substring(3,4));
				int b=Integer.valueOf(r); b++;
				String a="ACK"+String.valueOf(b);    
			
			    // send request     
			    sd=a.getBytes();	 
			    sp=new DatagramPacket(sd,sd.length,InetAddress.getByName(args[0]),Integer.parseInt(args[1]));	
 
				cs.send(sp);	
				System.out.println("sent "+a);

				


				if (reply.trim().equals("END")) // last consignment
					end = true;
				
				}

			}
		 
			

		 catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		catch (NumberFormatException ex) {
			System.out.println();
		}
		cs.close();
	}
 
}
