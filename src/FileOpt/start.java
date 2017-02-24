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
        getAllFileName gafn = new getAllFileName("C:\\Users\\WangQL\\Desktop\\Java");
        allfile = gafn.getAllfiles();
        analysis ans = new analysis();

        for(int i = 0; i < allfile.size(); i ++)
        {
            String temp = allfile.get(i);
            //System.out.println(temp);
            ans.setPath(temp);
            ans.getSeq();
        }
        //analysis ans = new analysis("/Users/WangQL/Documents/git/Java/java-unzip/src/main/java/com/hmkcode/Unzip.java");
        //System.out.println(keywords.size());

    }


}
