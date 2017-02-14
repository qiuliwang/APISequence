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
    public static void main(String args[])
    {
        //getAllFileName gafn = new getAllFileName("/Users/WangQL/Documents/git/Java");
        //allfile = gafn.getAllfiles();

        analysis ans = new analysis("/Users/WangQL/Documents/git/Java/java-unzip/src/main/java/com/hmkcode/Unzip.java");
        //System.out.println(keywords.size());

    }


}
