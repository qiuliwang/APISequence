package JdkCore;

import java.util.List;

public class start {
	public static void main(String args[]) throws Exception
	{
		String osName = System.getProperty("os.name");
		String path = "";
		if(osName.contains("Mac"))
		{
			path = "/Users/WangQL/Documents/git/APISequence/Eclipse/src/JdkCore/testContent.java";
		}
		else if(osName.contains("Windows"))
		{
			path = "C:\\Users\\WangQL\\Documents\\Code\\Java";
		}
		WriteAPI writer = new WriteAPI("Java");
		
		getAllFileName gaf = new getAllFileName(path);
		List<String> filenames = gaf.getAllfiles();

		for(int i = 0; i < filenames.size(); i ++)
		{
			try{
			JdkCore jcore = new JdkCore(filenames.get(i));
			List<String> temp = jcore.getAPI();
			writer.setApisq(temp);
			writer.writefile();
			}
			catch(Exception e)
			{
				
			}
		}
		
		//ASTAna asa = new ASTAna(content);
	}
}
