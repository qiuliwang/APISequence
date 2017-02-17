package FileOpt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by WangQL on 2/17/17.
 */
public class writeApi {
    getAllFileName getFile;
    getApiSeqence getApil;
    String corpusName = "apicorpus";
    String proj = System.getProperty("user.dir");
    String corpusPath = proj + corpusName;
    FileWriter writer;
    List<String> apisq;

    writeApi() throws Exception
    {
        //String proj = System.getProperty("user.dir");
        writer = new FileWriter(corpusPath, true);
    }

    //set api list prepare to write
    public void setApisq(List<String> aps)
    {
        apisq = aps;
    }

    //write api sequence to file
    //append
    public void writeApi() throws Exception
    {
        for(int i = 0; i < apisq.size(); i ++)
        {
            writer.write(apisq.get(i));
        }
    }

//    public static void method2(String fileName, String content) {
//        try {
//            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
//            FileWriter writer = new FileWriter(fileName, true);
//            writer.write(content);
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
