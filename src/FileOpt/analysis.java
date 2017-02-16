package FileOpt;

import com.sun.xml.internal.ws.api.ha.StickyFeature;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by WangQL on 2/14/17.
 */
public class analysis {
    String path;
    static List<String> keywords;
    static List<String> fileContent;
    static List<String> contentWithoutKeywords;
    static List<String> opt;
    static String contentString;
    static List<String> splitString; //split by scale

    public static void main(String args[])
    {
        analysis ans = new analysis("/Users/WangQL/Documents/git/Java/java-unzip/src/main/java/com/hmkcode/Unzip.java");

    }

    public analysis(String path)
    {
        this.path = path;
        String proj = System.getProperty("user.dir");
        String keywordsPath = proj + "/keywords.txt";
        String operatorPath = proj + "/opt.txt";
        keywords = new ArrayList<String>();
        fileContent = new ArrayList<String>();
        contentWithoutKeywords = new ArrayList<String>();
        opt = new ArrayList<String>();
        contentString = "";


        getkeywords(keywordsPath, keywords);
        getkeywords(operatorPath, opt);

        readFile(path);
        rmKeywords();

        //System.out.println(contentString);
        splitStringByScale(contentString);
    }

    /*
    like
        public class Uzip {
        pub......{
        ....}
        ...}
     */
    private static void splitStringByScale(String content)
    {
        String innerStr = content;
        List<String> innerContent = new ArrayList<String>();
        Stack stk = new Stack();
        int length = innerStr.length();
        innerContent.add(innerStr.substring(0, innerStr.indexOf('{')));
        innerStr = innerStr.substring(innerStr.indexOf('{'), innerStr.length());
        stk.push('{');

        while(innerStr.contains("{"))
        {
            int index = innerStr.indexOf('{');
            String sp = innerStr.substring(0, index);
            if(!sp.contains("\""))
            {
                innerContent.add(sp);
            }
            else
            {

            }
        }
    }

    //remove keywords from content, but leave "class"
    private static void rmKeywords()
    {
        for(int i = 0; i < fileContent.size(); i ++)
        {
            String tempString = fileContent.get(i);
            //System.out.println(tempString);
            String words[] = tempString.split("\\s+");

            for(String ss : words){
                //System.out.print(ss + " ");
                if(!keywords.contains(ss)) {
                    contentWithoutKeywords.add(ss);
                }
            }
            //System.out.print("\n");
        }
        for(int i = 0; i < contentWithoutKeywords.size(); i ++)
        {
            contentString += contentWithoutKeywords.get(i) + " ";
        }
    }

    private static void readFile(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            //System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                if(!tempString.contains("import") && !tempString.contains("package")
                     && tempString.length() != 0) {
                    while(tempString.indexOf('\t') == 0 || tempString.indexOf(' ') == 0)
                    {
                        tempString = tempString.substring(1, tempString.length());
                    }
                    if(tempString.length() != 0) {
                        if(tempString.length()<2)
                        {
                            //System.out.println(tempString);
                            fileContent.add(tempString);
                            line++;
                        }
                        else
                        {
                            if(!tempString.substring(0, 2).equals("//"))
                            {
                                //System.out.println(tempString);
                                fileContent.add(tempString);
                                line++;
                            }
                        }

                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    private static void getkeywords(String fileName, List<String> key) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            //System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                //System.out.println("line " + line + ": " + tempString);
                key.add(tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }
}
