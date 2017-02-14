package FileOpt;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by WangQL on 2/14/17.
 */
public class getAllFileName {
    String path;
    List<String> allfiles;
    public getAllFileName(String path)
    {
        this.path = path;
        allfiles = traverseFolder1(path);
    }

    List<String> getAllfiles()
    {
        return allfiles;
    }

    private List<String> traverseFolder1(String path) {
        List<String> fileNames = new ArrayList<String>();
        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    //System.out.println("folders:" + file2.getAbsolutePath());
                    list.add(file2);
                    folderNum++;
                } else {
                    String filename = file2.getAbsolutePath();
                    if(filename.contains(".java")) {
                        System.out.println("files:" + filename);
                        fileNum++;
                        fileNames.add(filename);
                        //Instances temp =
                    }
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        //System.out.println("folders:" + file2.getAbsolutePath());
                        list.add(file2);
                        folderNum++;
                    } else {
                        //System.out.println("files:" + file2.getAbsolutePath());
                        String filename = file2.getAbsolutePath();
                        if(filename.contains(".java"))
                        {
                            fileNames.add(filename);
                            fileNum++;
                        }
                    }
                }
            }
        } else {
            System.out.println("files not exist!");
        }
        System.out.println("folders:" + folderNum + ",files:" + fileNum);
        //System.out.println(fileNames.size());
        return fileNames;
    }
}
