package JdkCore;

public class testContent {
	
	public static void main(String args[])
	{
		testContent sct = new testContent("wang", "Jiangsu", 2016);
		testContent sct2 = null;
		
		sct.getAllInfo();
		sct.testFunc();
		int x = 10;
		if(x > 5)
		{
			System.out.println("good");
			System.out.println("good");
		}
	}
	
	String name;
	String address;
	int number;
	
	testContent(String name, String address, int number)
	{
		this.name = name;
		this.address = address;
		this.number = number;
	}
	
	void getAllInfo()
	{
		System.out.println("name: " + name);
		System.out.println("address: " + address);
		System.out.println("number: " + number);
	}
	
	void testFunc()
	{
		for(int i = 0; i < 10; i ++)
		{
			System.out.println(i);
		}
		
		int x = 10;
		do{
			x--;
		}while(x > 0);
		
	}
}
