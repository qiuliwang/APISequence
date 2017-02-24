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
    static List<String> CLASS_NAME;  //class get from source code
    static List<String> importClass;  //class from import
//    public static void main(String args[])
//    {
//        analysis ans = new analysis();
////        ans.setPath("C:\\Users\\WangQL\\Desktop\\Java\\java-excel-poi" +
////                "\\src\\main\\java\\com\\hmkcode\\poi\\AppRead.java");
//
//        ans.setPath("C:\\Users\\WangQL\\Desktop\\Java\\java-combinations\\Combination.java");
//        ans.getSeq();
//    }

    public analysis()
    {
        String proj = System.getProperty("user.dir");
        String keywordsPath = proj + "/keywords.txt";
        String operatorPath = proj + "/opt.txt";
        keywords = new ArrayList<String>();
        fileContent = new ArrayList<String>();
        opt = new ArrayList<String>();
        CLASS_NAME = new ArrayList<>();
        getkeywords(keywordsPath, keywords);
        getkeywords(operatorPath, opt);
        importClass = new ArrayList<>();
    }

    public void getSeq()
    {
        readFile(path);
        contentString = rmSpace(fileContent);
        String stringWithoutMarks = removeString(contentString);
        //System.out.println(stringWithoutMarks);
        try {
            test1(stringWithoutMarks);
        }
        catch (Exception e)
        {

        }
    }

    public void setPath(String str)
    {
        path = str;
        //System.out.println(path);
    }

    private static List<String> divideFuntions(String str)
    {
        //System.out.println(str);
        String content = str;
        //List<Integer> LeftScale = new ArrayList<Integer>();
        List<Integer> indexOfFunction = new ArrayList<Integer>();

        Stack stk = new Stack();

        int left = str.indexOf('{');
        str = str.substring(left + 1, str.length());
        int right = str.lastIndexOf('}');
        str = str.substring(0, right);

        for(int i = 0; i < str.length(); i ++)
        {
            if(str.charAt(i) == '{')
            {
                //LeftScale.add(i);
                stk.push('{');
            }
            else if(str.charAt(i) == '}')
            {
                //RightScale.add(i);
                stk.pop();
                if(stk.size() == 0)
                {
                    indexOfFunction.add(i);
                }
            }
        }

        System.out.println("number of funcitons:"+indexOfFunction.size());
        List<String> functions = new ArrayList<String>();
        for(int i = 0; i < indexOfFunction.size(); i ++)
        {
            //functions.add(str.substring())
            if(i == 0)
            {
                String temp = str.substring(0, indexOfFunction.get(i) + 1);
                while(temp.charAt(0) == ' ')
                {
                    temp = temp.substring(1, temp.length());
                }
                functions.add(temp);

            }
            else
            {
                String temp = str.substring(indexOfFunction.get(i - 1) + 1, indexOfFunction.get(i) + 1);
                while(temp.charAt(0) == ' ')
                {
                    temp = temp.substring(1, temp.length());
                }
                functions.add(temp);
            }
        }
        return functions;
    }

    private static void test1(String str)
    {
        //System.out.println(str);
        List<String> api = new ArrayList<>();
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
            System.out.println("no error\n");
            List<String> functions = divideFuntions(str);
            for(int x = 0; x < functions.size(); x ++)
            {
                List<String> temp = analysisSingleFunction(functions.get(x));
                for(int i = 0; i < temp.size(); i ++)
                {
                    System.out.println(temp.get(i));
                }
            }
        }
        else
        {
            System.out.println("error");
        }
    }

    //analysis function one by one
    private static List<String> analysisSingleFunction(String content)
    {
        String [] temp = content.split("new ");
        System.out.println(content);
        List<String> varaName = new ArrayList<String>();
        List<String> className = new ArrayList<String>();
        for(int i = 0; i < temp.length - 1; i ++)
        {
            String x = temp[i];  //before new
            String y = temp[i + 1]; //after new
            String paraName = "";
            //add vars name into list
            int indexBeforeEqu = x.lastIndexOf('=');
            if(indexBeforeEqu >= 0)
            {
                if(x.charAt(indexBeforeEqu - 1) == ' ')
                {
                    //System.out.println("has space ");
                    indexBeforeEqu = indexBeforeEqu - 2;
                }
                else
                {
                    indexBeforeEqu --;
                }
                for(int j = indexBeforeEqu; j > 0; j --)
                {
                    if(x.charAt(j) == ' ')
                    {
                        paraName = x.substring(j + 1, indexBeforeEqu + 1);
                        break;
                    }
                }
                varaName.add(paraName);
                //add class names into list
                int indexOffirstSpace = y.indexOf('(');
                //System.out.println(y + indexOffirstSpace);
                indexOffirstSpace = 0;
                while(y.charAt(indexOffirstSpace) != '(' && y.charAt(indexOffirstSpace) != '[')
                {
                    indexOffirstSpace ++;
                }
                String classname = y.substring(0, indexOffirstSpace);
                className.add(classname);
                System.out.println(classname);
            }
        }
        if(varaName.size() != className.size())
        {
            System.out.println("error, funcions");
        }
        else
        {
            for(int i = 0; i < varaName.size(); i ++)
            {
                content = content.replaceAll(varaName.get(i), className.get(i));
                if(!CLASS_NAME.contains(className.get(i)))
                {
                    CLASS_NAME.add(className.get(i));
                }
            }
        }
        //NOW we start to get api sequences
       // System.out.println(content);
        System.out.println("content to process: "+content);

        List<String> actApi = new ArrayList<String>();
        for(int z = 0; z < content.length(); z ++)
        {
            if(content.charAt(z) == 'n' && content.charAt(z + 1) == 'e' && content.charAt(z + 2) == 'w'
                    && (content.charAt(z - 1) != '(' || content.charAt(z - 2) != ' '))
            {
                String inner = content.substring(z + 3, content.length());
                int d = 0;
                for(d = 0; d < inner.length(); d ++)
                {
                    if(inner.charAt(d) == '(' || inner.charAt(d) == '[')
                        break;
                }
                inner = inner.substring(1, d);
                actApi.add(inner + ".new");
                //System.out.println(inner + ".new");
            }
            if(content.charAt(z) == '.')
            {
                int leftDot = z;
                int rightDot = z;
                while(content.charAt(leftDot) != ';'
                       && content.charAt(leftDot) != '{' && content.charAt(leftDot) != '('
                        && content.charAt(leftDot) != '=' && leftDot > 0 && content.charAt(leftDot) != '+')
                {
                    leftDot --;
                }
                while(content.charAt(rightDot) != ';'
                        && content.charAt(rightDot) != '}' && content.charAt(rightDot) != '(' )
                {
                    rightDot ++;
                }
                String inner = content.substring(leftDot, rightDot);
                inner = getapiForanalysisSingleFunction((inner));
                z = rightDot + 1;
                if(inner.charAt(0) == '.')
                {
                    String te = actApi.get(actApi.size() - 1);
                    te = te + inner;
                    actApi.set(actApi.size() - 1, te);
                }
                else {
                    actApi.add(inner);
                }
            }
        }

        //System.out.println("========\nreconstruct api sequences\n========");

        List<String> reconsApi = new ArrayList<>();
        for(int i = 0; i < actApi.size(); i ++)
        {
            String cla = actApi.get(i).substring(0, actApi.get(i).indexOf('.'));
            //System.out.println(cla);
            if(CLASS_NAME.contains(cla))
            {
                reconsApi.add(actApi.get(i));
            }
        }
        return reconsApi;
    }

    public static boolean check(char x)
    {
        char c = x;
        if(((c>='a'&&c<='z') || (c>='A'&&c<='Z')))
        {
            return   true;
        }else{
            return   false;
        }
    }
    private static String getapiForanalysisSingleFunction(String inner)
    {
        while(!check(inner.charAt(0))&&inner.charAt(0) != '.')
            inner = inner.substring(1, inner.length());
        while(inner.charAt(inner.length() - 1) == ' ' || inner.charAt(inner.length() - 1) == ';')
        {
            inner = inner.substring(0, inner.length() - 1);
        }
        //System.out.println(inner);
        return inner;
    }

    private static String removeString(String content) {
        String innerString = content;
        while (innerString.contains("\""))
        {
            int length = innerString.length();
            int ind = innerString.indexOf("\"");
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
        return innerString;
    }


    //remove keywords from content, but leave "class"
    private static String rmSpace(List<String> fileContent)
    {
        List<String> contentWithoutKeywords = new ArrayList<String>();
        String innerStr = "";
        for(int i = 0; i < fileContent.size(); i ++)
        {
            String tempString = fileContent.get(i);
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
        //System.out.println("here");
        fileContent.clear();
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            //System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            //int line = 1;
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
                            fileContent.add(tempString);
                        }
                        else
                        {
                            if(!tempString.substring(0, 2).equals("//"))
                            {
                                //System.out.println(tempString);
                                fileContent.add(tempString);
                                //line++;
                            }
                        }

                    }
                }
                else if(tempString.contains("import"))
                {
                    String temp = tempString.substring(tempString.lastIndexOf('.') + 1,
                            tempString.lastIndexOf(';'));
                    //System.out.println(temp);
                    CLASS_NAME.add(temp);
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
            //int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                //System.out.println("line " + line + ": " + tempString);
                key.add(tempString);
                //line++;
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
