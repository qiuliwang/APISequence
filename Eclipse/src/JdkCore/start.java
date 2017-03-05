package JdkCore;

public class start {
	public static void main(String args[])
	{
		String osName = System.getProperty("os.name");
		String path = "";
		if(osName.contains("Mac"))
		{
			path = "/Users/WangQL/Documents/git/APISequence/Eclipse/src/JdkCore/testContent.java";
		}
		else if(osName.contains("Windows"))
		{
			path = "C:\\Users\\WangQL\\Documents\\GitHub\\APISequence\\Eclipse\\src\\JdkCore\\testContent.java";
		}
		ReadFile rf = new ReadFile();
		rf.setPath(path);
		char [] content = rf.getContent();
		//ASTAna asa = new ASTAna(content);
	}
}
