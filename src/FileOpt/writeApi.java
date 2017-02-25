package FileOpt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangQL on 2/17/17.
 */
public class writeApi {
    getAllFileName getFile;
    getApiSeqence getApil;
    String corpusName = "apicorpus.txt";
    String proj = System.getProperty("user.dir");
    String corpusPath = proj + "/" + corpusName;
    FileWriter writer;
    List<String> apisq;

    public static void main(String argc[]) throws Exception
    {
        writeApi wap = new writeApi();
        System.out.println(wap.corpusPath);
        //method2(wap.corpusPath, "fuuckkk!!\n");
    }

    writeApi() throws Exception
    {
        //String proj = System.getProperty("user.dir");
        writer = new FileWriter(corpusPath, true);
        apisq = new ArrayList<>();
    }

    //set api list prepare to write
    public void setApisq(List<String> aps)
    {
        //System.out.println("hello~~~");
        apisq.clear();
        apisq = aps;
        //System.out.println("hello!!!" + apisq.size());

//        for(int i = 0; i < apisq.size(); i ++)
//        {
//            System.out.println("XXX "+apisq.get(i));
//        }
    }

    //write api sequence to file
    //append

    public void writefile() throws Exception
    {
        //System.out.println("hewew");
        for(int i= 0; i < apisq.size(); i ++)
        {
            writer.write(apisq.get(i));
        }
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
