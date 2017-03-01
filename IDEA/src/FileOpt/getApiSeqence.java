package FileOpt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * @author lyh
 */
public class getApiSeqence {
    static StringBuffer stringBuffer=new StringBuffer();
    static List<String> apiList;
    public static void main(String[] args) throws Exception {
        getApiSeqence test = new getApiSeqence("/Users/WangQL/Documents/git/Java/" +
                "java-combinations/Combination.java");
        List<String> temp = test.getApiList();
        for(int i = 0; i < temp.size(); i ++)
        {
            System.out.println(temp.get(i));
        }
    }

    public List<String> getApiList()
    {
        return  apiList;
    }

    getApiSeqence(String path) throws Exception {

        String filePath = path;
        readFile(filePath);
        List<String> list = getImportApi();
        if (list != null) {
            apiList = new ArrayList<String>();

            Pattern p = Pattern.compile("\\w+\\.\\w+");
            Matcher m = p.matcher(stringBuffer.toString().substring(stringBuffer.toString().indexOf("class")));
            List<String> list1 = new ArrayList<String>();

            while (m.find()) {
                list1.add(m.group());
            }

            for (int i = 0; i < list1.size(); i++) {

                String[] string = list1.get(i).split("\\.");
                if (string[0].equals("System")) {
                    apiList.add(list1.get(i).replace(".", " "));
                } else {
                    String metodname = string[1];

                    for (int j = 0; j < list.size(); j++) {
                        String apiname = list.get(j);
                        Class<?> clazz = Class.forName(apiname);
                        Method method[] = clazz.getMethods();
                        for (int k = 0; k < method.length; k++) {
                            if (method[k].getName().equals(metodname)) {

                                String temp = apiname.replace(".", " ") + " " + metodname;
                                apiList.add(temp);
                            }
                        }

                    }
                }
            }


        }else{
            apiList = new ArrayList<String>();

            Pattern p = Pattern.compile("\\w+\\.\\w+");
            Matcher m = p.matcher(stringBuffer.toString().substring(stringBuffer.toString().indexOf("class")));
            List<String> list1 = new ArrayList<String>();

            while (m.find()) {
                list1.add(m.group());
            }
            for (int i = 0; i < list1.size(); i++) {

                String[] string = list1.get(i).split("\\.");
                if (string[0].equals("System")) {
                    apiList.add(list1.get(i).replace(".", " "));
                }
            }
        }
    }
    //获取import中的java包
    public static List getImportApi(){
        int startimport=stringBuffer.indexOf("import");
        int endimport=stringBuffer.indexOf("public");
        if(startimport > endimport)
        {
            int temp = startimport;
            startimport = endimport;
            endimport = temp;
        }
        if (startimport!=-1){
            String str=stringBuffer.substring(startimport, endimport);
            List<String[]> list=new ArrayList<String[]>();
            List<String>  list1=new ArrayList<String>();
            String[]str1=str.split(";");
            String[] str2=null;
            for (int i = 0; i < str1.length; i++) {
                str2=str1[i].split(" ");
                list.add(str2);
            }
            for (int i = 0; i < list.size(); i++) {
                list1.add(list.get(i)[list.get(i).length-1]);
            }

            return list1;
        }else{
            return  null;
        }

    }



    //读取文件，放入StringBuffer中
    public static void readFile(String filePath){

        try {
            String encoding="GBK";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    // System.out.println(lineTxt);
                    stringBuffer.append(lineTxt);
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

    }
}
