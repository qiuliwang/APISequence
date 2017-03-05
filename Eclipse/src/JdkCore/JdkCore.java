package JdkCore;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.*;

public class JdkCore {
	public static void main(String args[])
	{
		String osName = System.getProperty("os.name");
		String path = "";
		if(osName.contains("Mac"))
		{
			path = "/Users/WangQL/Documents/git/APISequence/Eclipse/src/JdkCore/testContent.java";
		}
		else if(osName.contains("Windows"))
		{
			path = "C:\\Users\\WangQL\\Documents\\GitHub\\APISequence\\Eclipse\\src\\JdkCore\\testContent.java";
		}
		JdkCore test = new JdkCore(path);
		
	}
	
	String FILE_PATH;
	
	JdkCore(String path)
	{
		FILE_PATH = path;
		char [] content = getFileContent(path, 0);
		//System.out.println(content.length);
	}
	
	/***
	 * if index == 0
	 * read file and process
	 * else
	 * process the string directly
	 * @param path
	 * @param index
	 * @return
	 */
	char [] getFileContent(String path, int index)
	{
		if(index == 0)
		{
			ReadFile rf = new ReadFile();
			rf.setPath(path);
			char [] content = rf.getContent();
			return content;
		}
		else
		{
			String statContent = path;
			statContent = statContent.substring(statContent.indexOf('{') + 1, 
		       		statContent.lastIndexOf('}'));
		    while(statContent.charAt(0) == ' ' || statContent.charAt(0) == '\t' ||
		       		statContent.charAt(0) == '\n')
		    {
		      	statContent = statContent.substring(1, statContent.length());
		    }    
		    while(statContent.charAt(statContent.length() - 1) == ' ' || statContent.charAt(statContent.length() - 1) == '\t' ||
		       		statContent.charAt(statContent.length() - 1) == '\n')
		    {
		       	statContent = statContent.substring(0, statContent.length() - 1);
		    }
		    
		    return statContent.toCharArray();
		}
	}
}
