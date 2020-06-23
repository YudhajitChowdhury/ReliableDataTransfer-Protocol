import java.net.*;
import java.io.*;
import java.util.*;
 
public class FClient {
 
	public static void main(String[] args) {
	 
	    DatagramSocket cs = null;
		FileOutputStream fos = null;
		try {
			cs = new DatagramSocket();
			int count = 0,i,j,k,pos = 0;;
			String fname = "demoText.html";
			final int ch = 1;
			byte[] rd, sd;
			String GREETING = "REQUEST" + fname + "\r\n";
			String reply1 = "";
			DatagramPacket sp,rp;
			boolean end = false;
			System.out.println("Requesting " + fname + " from Server");
			fos = new FileOutputStream(fname.split("\\.")[0] + "1." + fname.split("\\.")[1]);
			int f = 1;
			while(!end)
			{   	  
				sd = new byte[512];
				String rec = "Received Consignment " + (count);
				String intlength = Integer.toString(count);
				String f_ack = "Forgot ACK " + Integer.toString(count+1) + "\nReceived Consignment " + count + " duplicate - Discarding\n" + "Sent ACK " + Integer.toString(count  + 1) + "\n";
				String ack= "ACK" + Integer.toString(count+1) +"\r\n";
				rd=new byte[517 + intlength.length()];
				rp=new DatagramPacket(rd,rd.length);
				if(count == 0)
				{
					sd=GREETING.getBytes();
					sp=new DatagramPacket(sd,sd.length, 
							InetAddress.getByName(args[0]),
							Integer.parseInt(args[1]));

					cs.send(sp);
				}
				sd = ack.getBytes();
				sp=new DatagramPacket(sd,sd.length, 
							InetAddress.getByName(args[0]),
							Integer.parseInt(args[1]));

				
					if(count == ch)
					{
						if(f == 1){
							cs.receive(rp);
							reply1 =new String(rp.getData());
							byte [] reply  = Arrays.copyOfRange(rp.getData(),intlength.length() + 3,rp.getData().length - 2);
							fos.write(reply);
							System.out.println(reply1);
							System.out.println(rec);
							}
							cs.receive(rp);
							if(reply1.equals(new String(rp.getData()))){
								System.out.println(f_ack);
								cs.send(sp); 
								count++;}
							else
							{
								f = 2;
							}

					}
					else
					{			
					cs.receive(rp);
					
					byte [] td = new byte[3];
					reply1 =new String(rp.getData());
					System.out.println(reply1);

					
					for(i = 1; i <= rp.getData().length; i++)
					{
						if(rp.getData()[i-1] == 0x0a && rp.getData()[i] == 0x0d )
						{
							for(j = i - 4,k=0; j <= i-2; j++,k++)
							{
								td[k] = rp.getData()[j];
							}
							if(new String(td).equals("END"))
							{
								pos = i - 5;
								
							}
							else
								pos = i - 2;
						}
						
					}

					byte [] reply  = Arrays.copyOfRange(rp.getData(),intlength.length() + 3,pos + 1);
					fos.write(reply);
					System.out.println(rec);
					
					if (!reply1.trim().substring(reply1.trim().length()-3,reply1.trim().length()).equals("END"))
					{ 
						sd = ack.getBytes();
						sp=new DatagramPacket(sd,sd.length, 
								InetAddress.getByName(args[0]),
								Integer.parseInt(args[1]));
						cs.send(sp);
						System.out.println("Sent ACK " + (count + 1) + "\n");
						
					}
					else
						end = true;
					
					
					count++;
				}
			}
			cs.close();

		
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		finally {
			try {
			if (fos != null) fos.close();
			if (cs != null) cs.close();
			} catch (IOException ex) { System.out.println(ex.getMessage());} } 
				}
 
}
