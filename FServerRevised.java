import java.net.*;
import java.io.*;
import java.util.*;
 
public class FServer {
	public static byte[] CRLF = new byte[] { 0x0a, 0x0d };
	public static byte[] RDT = new byte[] { 0x52, 0x44, 0x54 };
	public static byte[] END = new byte[] { 0x45, 0x4e, 0x44 };

	public static void main(String[] args) {


		DatagramSocket ss = null;
		FileInputStream fis = null;
		DatagramPacket rp, sp;
		byte[] rd, sd,td,id,mymsg;
		String intlength;
		InetAddress ip = null;
		int port = 0;
		final int frame = 3; 
    
    try {
			int consignment = 0;
			intlength = Integer.toString((consignment));
			String strConsignment;
			String strGreeting;
			int result = 0; // number of bytes read
	 		td = new byte[512];
			ss = new DatagramSocket(Integer.parseInt(args[0]));
			
			System.out.println("Server is up....");
			rd=new byte[512];
			rp = new DatagramPacket(rd,rd.length);
      
      while(true && result != -1)
			{
				rd=new byte[512];
				sd=new byte[512];
				
				rp = new DatagramPacket(rd,rd.length);
				try{
				ss.receive(rp);
				strConsignment = new String(rp.getData());
				ip = rp.getAddress(); 
			port =rp.getPort();
			System.out.println("Client IP Address = " + ip);
			System.out.println("Client port = " + port);
			System.out.println("Client says = " + strConsignment);
      
      if(new String(rp.getData()).contains("REQUEST"))
				{
			String a =  strConsignment.trim();
			a = a.substring(7,a.length());
			fis = new FileInputStream(a);
				consignment  = 0;
        sd = new byte[result];
        sd = Arrays.copyOfRange(td,0,result);
        sp=new DatagramPacket(mymsg,mymsg.length,ip,port);
        result = fis.read(sd);
				td = sd;
        ss.send(sp); 
        }
     }
  }
}

public static byte[] concatenateByteArrays(byte[] a, byte[] b, byte[] c, byte[] d) {
        byte[] result = new byte[a.length + b.length + c.length + d.length]; 
        System.arraycopy(a, 0, result, 0, a.length); 
        System.arraycopy(b, 0, result, a.length, b.length);
        System.arraycopy(c, 0, result, a.length+b.length, c.length);
        System.arraycopy(d, 0, result, a.length+b.length+c.length, d.length);
        return result;
    }

	public static byte[] concatenateByteArrays(byte[] a, byte[] b, byte[] c, byte[] d, byte[] e) {
        byte[] result = new byte[a.length + b.length + c.length + d.length + e.length]; 
        System.arraycopy(a, 0, result, 0, a.length); 
        System.arraycopy(b, 0, result, a.length, b.length);
        System.arraycopy(c, 0, result, a.length+b.length, c.length);
        System.arraycopy(d, 0, result, a.length+b.length+c.length, d.length);
        System.arraycopy(e, 0, result, a.length+b.length+c.length+d.length, e.length);
        return result;
    }
    
}
        
