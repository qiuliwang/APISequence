package FileOpt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangQL on 2/14/17.
 */
public class start {
    static List<String> keywords;
    static List<String> allfile;
    public static void main(String args[]) throws Exception
    {
        getAllFileName gafn = new getAllFileName("/Users/WangQL/Documents/git/Java");
        allfile = gafn.getAllfiles();

        writeApi wa = new writeApi();

        for(int i = 0; i < 10; i ++)
        {
            String temp = allfile.get(i);
            getApiSeqence gap = new getApiSeqence(temp);
            List<String> inst = gap.getApiList();
            wa.setApisq(inst);
            wa.writeApi();
        }
        //analysis ans = new analysis("/Users/WangQL/Documents/git/Java/java-unzip/src/main/java/com/hmkcode/Unzip.java");
        //System.out.println(keywords.size());

    }


}
