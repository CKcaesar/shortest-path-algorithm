import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Date;


public class TestLog {
	
	public static void main(String[] args){
		
		
		try {
			FileWriter fw;
			fw = new FileWriter(".\\log.txt",true);
			Date date=new Date();
		String logMsg=date.toString()+"\n";
		fw.write(logMsg,0,logMsg.length());  
		fw.flush();
		fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*BufferedWriter out = new BufferedWriter(fw);
		out.append(logMsg);
		out.flush();
		out.close();*/
		
		/*PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("log.txt")),true);  
		pw.println(logMsg); 
		pw.close();*/
		//System.out.println(logMsg);

	}
	
}
