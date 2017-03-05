package JdkCore;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadFile {
	public static void main(String args[])
	{
		
	}
	
	String path;
	
	ReadFile()
	{
		
	}
	
	void setPath(String path)
	{
		this.path = path;
	}
	
	char [] getContent()
	{
		byte[] input = null;
		try {  
			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(path));  
	        input = new byte[bufferedInputStream.available()];  
	                bufferedInputStream.read(input);  
	                bufferedInputStream.close();  
	    } catch (FileNotFoundException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }          
	    String content = new String(input);//.toCharArray();
	    char [] res = content.toCharArray();
		return res;
	}
}
