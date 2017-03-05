package JdkCore;

public class start {
	public static void main(String args[])
	{
		String path = "/Users/WangQL/Documents/git/APISequence/Eclipse/src/JdkCore/testContent.java";
		//git test
		ReadFile rf = new ReadFile();
		rf.setPath(path);
		String content = rf.getContent();
		//System.out.println(content.toString());
		ASTAna asa = new ASTAna(content);
	}
}
