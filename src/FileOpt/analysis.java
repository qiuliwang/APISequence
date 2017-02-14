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
public class analysis {
    String path;
    static List<String> keywords;
    static List<String> fileContent;

    public static void main(String args[])
    {
        analysis ans = new analysis("/Users/WangQL/Documents/git/Java/java-unzip/src/main/java/com/hmkcode/Unzip.java");

    }

    public analysis(String path)
    {
        this.path = path;
        String proj = System.getProperty("user.dir");
        String keywordsPath = proj + "/keywords.txt";
        keywords = new ArrayList<String>();
        fileContent = new ArrayList<String>();
        getkeywords(keywordsPath);
        //System.out.println("size is "+keywords.size());
        readFile(path);
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
                            System.out.println(tempString);
                            fileContent.add(tempString);
                            line++;
                        }
                        else
                        {
                            if(!tempString.substring(0, 2).equals("//"))
                            {
                                System.out.println(tempString);
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

    private static void getkeywords(String fileName) {
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
                keywords.add(tempString);
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
