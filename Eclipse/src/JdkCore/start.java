package JdkCore;

import java.util.List;

public class start {
	public static void main(String args[]) throws Exception
	{
		String osName = System.getProperty("os.name");
		String path = "";
		if(osName.contains("Mac"))
		{
			path = "/Users/WangQL/Documents/git/hbase";
		}
		else if(osName.contains("Windows"))
		{
			path = "C:\\Users\\WangQL\\Desktop\\test\\buildinggame\\build\\apk";
		}		
		getAllFileName gaf = new getAllFileName(path);
		List<String> filenames = gaf.getAllfiles();
		JdkCore jcore = new JdkCore(filenames.get(0), path.substring(path.lastIndexOf("\\"), path.length()));
		//JdkCore jcore = new JdkCore(filenames.get(0), path.substring(path.lastIndexOf("/"), path.length()));

		for(int i = 1; i < filenames.size(); i ++)
		{
			
			try{
				jcore.setPath(filenames.get(i));
				System.out.println(i + "/" + filenames.size());
			}
			catch(Exception e)
			{
				//System.out.println(e.getMessage());
			}
		}
		System.out.println("Done!");
		//ASTAna asa = new ASTAna(content);
	}
}
