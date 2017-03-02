package JdkCore;

public class start {
	public static void main(String args[])
	{
		String path = "/Users/WangQL/Desktop/test.java";
		ReadFile rf = new ReadFile();
		rf.setPath(path);
		String content = rf.getContent();
		System.out.println(content.toString());
	}
}
