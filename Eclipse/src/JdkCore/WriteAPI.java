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
        wap.writefile();
    }

    WriteAPI(String str) throws Exception
    {
        //String proj = System.getProperty("user.dir");
    	String path = proj + "\\" + str + ".txt";
    	System.out.println(path);
        writer = new FileWriter(path, true);
    }

    //set api list prepare to write
    public void setApisq(List<String> aps)
    {
        //System.out.println("hello~~~");
        List<String> apisq = new ArrayList<>();
        apisq = aps;
        System.out.println(apisq.size());
        contentToWrite = "";
        for(int i = 0; i < apisq.size(); i ++)
        {
            contentToWrite += apisq.get(i) + " ";
        }
        if(contentToWrite.length() != 0)
            contentToWrite += "\n";
    }

    //write api sequence to file
    //append

    public void writefile() throws Exception
    {
        System.out.println(contentToWrite);
        writer.write(contentToWrite);
        writer.flush();

    }

    public static void method2(String fileName, String content) {
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
