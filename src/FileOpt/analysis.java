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
    static List<String> opt;
    static String contentString;

    static String CLASS_NAME;

    String testCo = "public static void unzip(String zipFile,String outputPath){ if(outputPath == null) outputPath = #; else outputPath+=File.separator; File outputDirectory = new File(outputPath); String str1 = #; String str2 = #; if(outputDirectory.exists()) outputDirectory.delete(); outputDirectory.mkdir(); try { ZipInputStream zip = new ZipInputStream(new FileInputStream(zipFile)); ZipEntry entry = null; int len; byte[] buffer = new byte[1024]; while((entry = zip.getNextEntry()) != null){ if(!entry.isDirectory()){ System.out.println(#+entry.getName()); File file = new File(outputPath +entry.getName()); if(!new File(file.getParent()).exists()) new File(file.getParent()).mkdirs(); FileOutputStream fos = new FileOutputStream(file); while ((len = zip.read(buffer)) > 0) { fos.write(buffer, 0, len); } fos.close(); } } }catch (FileNotFoundException e) { e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); } } ";

    public static void main(String args[])
    {
        analysis ans = new analysis("/Users/WangQL/Documents/git" +
                "/Java/java-unzip/src/main/java/com/hmkcode/Unzip.java");
    }

    public analysis(String path)
    {
        this.path = path;
        String proj = System.getProperty("user.dir");
        String keywordsPath = proj + "/keywords.txt";
        String operatorPath = proj + "/opt.txt";
        keywords = new ArrayList<String>();
        fileContent = new ArrayList<String>();
        //contentWithoutKeywords = new ArrayList<String>();
        opt = new ArrayList<String>();
        contentString = "";

        getkeywords(keywordsPath, keywords);
        getkeywords(operatorPath, opt);

        readFile(path);
        contentString = rmSpace(fileContent);
        String stringWithoutMarks = removeString(contentString);
        //System.out.println(stringWithoutMarks);
        test(stringWithoutMarks);
        //System.out.println(stringWithoutMarks);
    }

    private static void test(String str)
    {
        int countLeft = 0;
        int countRight = 0;
        for(int i = 0; i < str.length(); i ++)
        {
            if(str.charAt(i) == '{')
            {
                countLeft ++;
            }
            else if(str.charAt(i) == '}') {
                countRight++;
            }
        }
        if(countLeft == countRight) {
            System.out.println("no error");
            //System.out.println(str.indexOf("class"));
            int indexOfClass = str.indexOf("class");
            int endIndexOfClass = indexOfClass + 5;
            int beginOfClassName = endIndexOfClass + 1;
            int endOfClassName = str.indexOf('{');
            //System.out.println(str.substring(indexOfClass, endIndexOfClass+1));
            //System.out.println(str.substring(beginOfClassName, endOfClassName));
            CLASS_NAME = str.substring(beginOfClassName, endOfClassName);
            str = str.substring(str.indexOf('{') + 2, str.lastIndexOf('}'));
            System.out.println(str);

            //List<Integer> leftScale = new ArrayList<Integer>();
            //List<Integer> rightScale = new ArrayList<Integer>();

            //now we start to analysis source code

            //stack, use for { ... }
            Stack stk = new Stack();
            Integer temp = str.indexOf('{');
            stk.push('{');

            //List<String> use for
            List<String> littStr = new ArrayList<String>();
            //System.out.println(str.charAt(temp));
            int i = 0;

//            String []splitedString = str.split("\\}|;");
//            for(int x = 0; x < splitedString.length; x ++)
//            {
//                System.out.println(splitedString[x]);
//            }
        }
    }

    private static String removeString(String content) {
        String innerString = content;
        while (innerString.contains("\""))
        {
            int length = innerString.length();
            //System.out.println(innerString);
            int ind = innerString.indexOf("\"");
            //System.out.println(innerString.charAt(ind));
            for(int i = ind + 1; i < length; i ++)
            {
                 if(innerString.charAt(i) == '\"')
                 {
                     if(innerString.charAt(i - 1) != '\\')
                     {
                         String temp1 = innerString.substring(0, innerString.indexOf("\""));
                         String temp2 = "#" + innerString.substring(i + 1, length);
                         innerString = temp1 + temp2;
                         break;
                     }
                 }
            }
        }
        //System.out.println(innerString);
        return innerString;
    }

    /*
    like
        public class Uzip {
        pub......{
        ....}
        ...}
     */

    //remove keywords from content, but leave "class"
    private static String rmSpace(List<String> fileContent)
    {
        List<String> contentWithoutKeywords = new ArrayList<String>();
        String innerStr = "";
        for(int i = 0; i < fileContent.size(); i ++)
        {
            String tempString = fileContent.get(i);
            //System.out.println(tempString);
            String words[] = tempString.split("\\s+");

            for(String ss : words){
                    contentWithoutKeywords.add(ss);
            }
        }
        for(int i = 0; i < contentWithoutKeywords.size(); i ++)
        {
            innerStr += contentWithoutKeywords.get(i) + " ";
        }
        return innerStr;
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
