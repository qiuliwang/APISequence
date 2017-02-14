package FileOpt;

import java.util.List;

/**
 * Created by WangQL on 2/14/17.
 */
public class start {
    public static void main(String args[])
    {
        getAllFileName gafn = new getAllFileName("/Users/WangQL/Documents/git/Java");
        List<String> allfile = gafn.getAllfiles();
//        for(int i = 0; i < allfile.size(); i ++)
//        {
//            System.out.println(allfile.get(i));
//        }
    }
}
