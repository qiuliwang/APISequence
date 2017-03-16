package JdkCore;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangQL on 2/17/17.
 */
public class WriteAPI {
    String corpusName = "apicorpus.txt";
    String proj = System.getProperty("user.dir");
    String corpusPath = proj + "\\" + corpusName;
    FileWriter writer;
    String contentToWrite;

    public static void main(String argc[]) throws Exception
    {
    	WriteAPI wap = new WriteAPI("test");
        //method2(wap.corpusPath, "fuuckkk!!\n");
        List<String> test = new ArrayList<>();
        test.add("aaa");
        test.add("bbb");
        test.add("ccc");
        wap.setApisq(test);
        wap.writeapi();
    }

    public WriteAPI(String str) throws Exception
    {
    	String osName = System.getProperty("os.name");
    	String path = "";
		if(osName.contains("Mac"))
		{
			path =  proj + "/" + str + ".txt";
		}
		else if(osName.contains("Windows"))
		{
			//path = "C:\\Users\\WangQL\\Desktop\\ArrayBackedTag.java";
			path= proj + "\\" + str + ".txt";;
		}
        //String proj = System.getProperty("user.dir");
    	
    	System.out.println(path);
        writer = new FileWriter(path, true);
    }

    //set api list prepare to write
    public Boolean setApisq(List<String> aps)
    {
        //System.out.println("hello~~~");
        List<String> apisq = new ArrayList<>();
        apisq = aps;
        contentToWrite = "";
        for(int i = 0; i < apisq.size(); i ++)
        {
        	if(apisq.get(i).contains("\n"))
        		break;
            contentToWrite += apisq.get(i) + " ";
        }
        if(contentToWrite.contains("\n"))
        	contentToWrite = contentToWrite.substring(0, contentToWrite.indexOf('\n'));
        contentToWrite += "\n";
        if(contentToWrite.contains("List") ||contentToWrite.contains("Byte") ||contentToWrite.contains("byte"))
        	return true;
        else 
        	return false;
    }

    //write api sequence to file
    //append

    public void writeapi() throws Exception
    {
        //System.out.println(contentToWrite);
        writer.write(contentToWrite);
        writer.flush();
    }
    
    public void writeString(String content) throws Exception
    {
    	writer.write(content);
    	writer.flush();
    }

    public static void method2(String fileName, String content) {
        try {
            // ´ò¿ªÒ»¸öÐ´ÎÄ¼þÆ÷£¬¹¹Ôìº¯ÊýÖÐµÄµÚ¶þ¸ö²ÎÊýtrue±íÊ¾ÒÔ×·¼ÓÐÎÊ½Ð´ÎÄ¼þ
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
