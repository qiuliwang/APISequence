package JdkCore;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jdt.core.dom.*;

public class JdkCore {
	 List<String> commentlist=new ArrayList<String>();
	 List<String> methodlist=new ArrayList<String>();
	 WriteAPI writeApi;
	 WriteAPI writeCom;
	public static void main(String args[]) throws Exception
	{
		String osName = System.getProperty("os.name");
		String path = "";
		if(osName.contains("Mac"))
		{
			path = "/Users/WangQL/Downloads/ArrayBackedTag.java";
		}
		else if(osName.contains("Windows"))
		{
			//path = "C:\\Users\\WangQL\\Desktop\\ArrayBackedTag.java";
			path="C:\\Users\\WangQL\\Desktop\\ArrayBackedTag.java";
		}
		
		JdkCore test = new JdkCore(path, "test");
//		WriteAPI writeapi1=new WriteAPI("l1");
//		
//		writeapi1.setApisq(test.commentlist);
//		writeapi1.writefile();
//		WriteAPI writeapi2=new WriteAPI("l2");
//		writeapi2.setApisq(test.methodlist);
//		writeapi2.writefile();
	}
	
	String FILE_PATH;
	HashMap<String , String> map;
	static List<String> keyType = new ArrayList<String>(){{
        add("boolean");
        add("byte");
        add("short");
        add("int");
        add("long");
        add("double");
        add("char");
        add("float");
        add("Integer");
        add("Double");
        add("Float");
        add("String");
    }};
    
	List<String> api;
	//corpu's name
	String crupx = "";
	
	JdkCore(String path, String crup) throws Exception
	{
		FILE_PATH = path;
		map = new HashMap<String , String>();  
		api = new ArrayList<String>();
		char [] content = getFileContent(path, 0);
		//
		crupx = crup;
	    writeApi = new WriteAPI(crupx+"api");
	    writeCom = new WriteAPI(crupx+"com");
		processJavaFile(content);
	}
	
	void setPath(String path) throws Exception
	{
		FILE_PATH = path;
		char [] content = getFileContent(path, 0);
		//
		processJavaFile(content);
	}
	
	List<String> getAPI()
	{
		return api;
	}
	/**
	 * process the java file
	 * @param content
	 * @throws Exception 
	 */
	void processJavaFile(char [] content) throws Exception
	{
		api.clear();
		map.clear();
		ASTParser parser = ASTParser.newParser(AST.JLS3); //initialize    
        parser.setKind(ASTParser.K_COMPILATION_UNIT);     //to parse compilation unit  
        parser.setSource(content);      //content is a string which stores the <a href="http://lib.csdn.net/base/java" class='replace_word' title="Java 閻儴鐦戞惔锟�" target='_blank' style='color:#df3434; font-weight:bold;'>Java </a>source  
        parser.setResolveBindings(true);  
        CompilationUnit result = (CompilationUnit) parser.createAST(null);
        List<?> types = result.types(); 
        
        List<Comment> comments = result.getCommentList();
        List<Integer> commentsPos = new ArrayList<Integer>();
        for(Comment com : comments)
        {
        	commentsPos.add(com.getStartPosition());
        }
               
        TypeDeclaration typeDec = (TypeDeclaration) types.get(0);   	    
	    MethodDeclaration methodDec[] = typeDec.getMethods(); 
	    
   	 	for (MethodDeclaration method : methodDec)    
   	 	{
   	 		api.clear();
   	 		//position for the function
   	 		Integer pos = method.getStartPosition();
   	 		
   	 		if(commentsPos.contains(pos))
   	 			;
   	 		else
   	 			continue;
   	 		
         	Block body=method.getBody();  
         	List<?> statements=body.statements();   //get the statements of the method body  
         	Iterator<?> iter=statements.iterator();  
            while(iter.hasNext())  
            {  
                //get each statement  
                Statement stmt=(Statement)iter.next();  
                //ExpressionStatement
                if(stmt instanceof ExpressionStatement)
    			{
    				ExpressionStatement expressStmt=(ExpressionStatement) stmt;
    				Expression express=expressStmt.getExpression();
    				if(express instanceof Assignment)
    				{
    					Assignment assign=(Assignment)express;    				
    					String RHS = assign.getRightHandSide().toString();
    					if(RHS.contains("."))
    					{
    						RHS+=";";    						
    						char []tpRHS = RHS.toCharArray();
    						processBlock(tpRHS);
    					}
    				}
    				else if(express instanceof MethodInvocation)
    				{
    					MethodInvocation mi=(MethodInvocation) express;    
    					String exp = "";
    					if(mi.getExpression() != null)
    					{
    						exp = mi.getExpression().toString();
    					}
    					String name = mi.getName().toString();
    					
    					if(exp.contains("."))
    					{
    						String temp1 = exp.substring(0, exp.indexOf('.'));
    						String temp2 = exp.substring(exp.indexOf('.'), exp.length());
    						if(map.keySet().contains(temp1))
    						{
    							temp1 = map.get(temp1);
    						}
    						exp = temp1 + temp2;
    					}
    					else
    					{
    						if(map.keySet().contains(exp))
    							exp = map.get(exp);
    					}
    					
    					String temp = exp + "." + name;
    					if(temp.charAt(0) == '.')
    					{
    						temp = temp.substring(1, temp.length());
    					}
    					if(!temp.contains("out.print") && temp != "log")
    					{	
    						api.add(temp);
    					}
    				}
    			}
                
                //IfStatement

                else if(stmt instanceof IfStatement)
    			{
    				IfStatement ifstmt=(IfStatement) stmt;
    				Expression exp = ifstmt.getExpression();
    				Statement stat = ifstmt.getThenStatement();
    				String statContent = stat.toString();
    				if(statContent.contains("{"))
    				{
    		        statContent = statContent.substring(statContent.indexOf('{') + 1, 
    		        		statContent.lastIndexOf('}'));
    				}
    		        while(statContent.charAt(0) == ' ' || statContent.charAt(0) == '\t' ||
    		        		statContent.charAt(0) == '\n')
    		        {
    		        	statContent = statContent.substring(1, statContent.length());
    		        }
    		        
    		        while(statContent.charAt(statContent.length() - 1) == ' ' || statContent.charAt(statContent.length() - 1) == '\t' ||
    		        		statContent.charAt(statContent.length() - 1) == '\n')
    		        {
    		        	statContent = statContent.substring(0, statContent.length() - 1);
    		        }
    				char [] temp = statContent.toCharArray();
    				processBlock(temp);
    			}
                else if(stmt instanceof VariableDeclarationStatement)
    			{
                	//here, we add Class.New
    				VariableDeclarationStatement var=(VariableDeclarationStatement) stmt;			
    				String type = var.getType().toString();
    				String frag = var.fragments().toString();
    				String fragAll = frag;
    				String variableName = "";
    				if(frag.contains("="))
    				{
    					variableName = frag.substring(frag.indexOf('[') + 1, frag.indexOf('='));
    				}
    				else
    				{
    					variableName = frag.substring(frag.indexOf('[') + 1, frag.indexOf(']'));
    				}
    				
    				map.put(variableName, type);
    				fragAll = fragAll.substring(fragAll.indexOf('=') + 1, fragAll.indexOf(']'));
    				fragAll += ";";
    				if((type != "float" && type != "Float" && type != "Double" && type != "double" ) 
    						&& fragAll.contains("."))
    				{
    					char [] temp = fragAll.toCharArray();
    					processBlock(temp);
    				}
    				if(!keyType.contains(type))
    				{
    					api.add(type + ".New");
    				}
    			}
                //ReturnStatement
                else if(stmt instanceof ReturnStatement)
    			{
    			}
                
                else if(stmt instanceof ForStatement)
                {
                	ForStatement fst = (ForStatement)stmt;
                	Expression exp = fst.getExpression();
    				Statement stat = fst.getBody();
    				String statContent = stat.toString();
    				if(statContent.contains("{"))
    				{
    		        statContent = statContent.substring(statContent.indexOf('{') + 1, 
    		        		statContent.lastIndexOf('}'));
    				}
    		        while(statContent.charAt(0) == ' ' || statContent.charAt(0) == '\t' ||
    		        		statContent.charAt(0) == '\n')
    		        {
    		        	statContent = statContent.substring(1, statContent.length());
    		        }
    		        
    		        while(statContent.charAt(statContent.length() - 1) == ' ' || statContent.charAt(statContent.length() - 1) == '\t' ||
    		        		statContent.charAt(statContent.length() - 1) == '\n')
    		        {
    		        	statContent = statContent.substring(0, statContent.length() - 1);
    		        }
    				char [] temp = statContent.toCharArray();
    				processBlock(temp);

                }
                
                else if(stmt instanceof WhileStatement)
                {
                	WhileStatement fst = (WhileStatement)stmt;
                	Expression exp = fst.getExpression();
    				Statement stat = fst.getBody();
    				String statContent = stat.toString();
    		        statContent = statContent.substring(statContent.indexOf('{') + 1, 
    		        		statContent.lastIndexOf('}'));
    		        while(statContent.charAt(0) == ' ' || statContent.charAt(0) == '\t' ||
    		        		statContent.charAt(0) == '\n')
    		        {
    		        	statContent = statContent.substring(1, statContent.length());
    		        }
    		        
    		        while(statContent.charAt(statContent.length() - 1) == ' ' || statContent.charAt(statContent.length() - 1) == '\t' ||
    		        		statContent.charAt(statContent.length() - 1) == '\n')
    		        {
    		        	statContent = statContent.substring(0, statContent.length() - 1);
    		        }
    				char [] temp = statContent.toCharArray();
    				processBlock(temp);

                }
            }
            if(api.size() != 0)
            {
            	
            	if(writeApi.setApisq(api))
            	{
           	 		String comment = "";
           	 		for(Comment com : comments)
           	 		{
           	 			if(com.getStartPosition() == pos)
           	 			{
           	 				comment = com.toString();
           	 				comment = cleanComments(comment);
           	 			}
           	 		}
           	 		comment += "\n";
           	 		if(comment.charAt(0) != '@' && comment.charAt(0) != '<')
           	 		{
           	 			writeApi.writeapi();
           	 			writeCom.writeString(comment);
           	 		}
            	}
            }
   	 	}    	 	
	}
	
	/**
	 * clean useless comments' infomation and return clean string
	 * @param comment
	 * @return
	 */
	private String cleanComments(String comment)
	{
		String res = comment;
		while(res.charAt(0) == '/' || res.charAt(0) == '*' ||
				res.charAt(0) == '\n' || res.charAt(0) == '\t' || res.charAt(0) == ' ')
		{
			res = res.substring(1, res.length());
		}
		res = res.substring(0, res.indexOf('\n'));
		return res;
	}
	
	/**
	 * process strings from if for and while and so on
	 * @param content
	 */
	void processBlock(char [] content)
	{
		ASTParser parser = ASTParser.newParser(AST.JLS3); //initialize    
        parser.setKind(ASTParser.K_STATEMENTS);     //to parse block  
        parser.setSource(content);
        Block bck = (Block)parser.createAST(null);
        List<?> statements = bck.statements();
        Iterator<?> iter=statements.iterator();  
        while(iter.hasNext())  
        {  
            //get each statement  
            Statement stmt=(Statement)iter.next();  
            //ExpressionStatement
            if(stmt instanceof ExpressionStatement)
			{
				ExpressionStatement expressStmt=(ExpressionStatement) stmt;
				Expression express=expressStmt.getExpression();
				if(express instanceof Assignment)
				{
					Assignment assign=(Assignment)express;
				}
				else if(express instanceof MethodInvocation)
				{
					MethodInvocation mi=(MethodInvocation) express;
					String exp = "";
					if(mi.getExpression() != null)
					{
						exp = mi.getExpression().toString();
					}
					String name = mi.getName().toString();
					
					if(exp.contains("."))
					{
						String temp1 = exp.substring(0, exp.indexOf('.'));
						String temp2 = exp.substring(exp.indexOf('.'), exp.length());
						if(map.keySet().contains(temp1))
						{
							temp1 = map.get(temp1);
						}
						exp = temp1 + temp2;
					}
					else
					{
						if(map.keySet().contains(exp))
							exp = map.get(exp);
					}
					
					String temp = exp + "." + name;
					if(temp.charAt(0) == '.')
					{
						temp = temp.substring(1, temp.length());
					}					
					if(!temp.contains("out.print") && temp != "log")
					{	
						api.add(temp);
					}
				}
            
				//IfStatement
				
				else if(stmt instanceof IfStatement)
				{
					IfStatement ifstmt=(IfStatement) stmt;
					Expression exp = ifstmt.getExpression();
					Statement stat = ifstmt.getThenStatement();
				}
				else if(stmt instanceof VariableDeclarationStatement)
				{
					//here, we add Class.New
					VariableDeclarationStatement var=(VariableDeclarationStatement) stmt;			
					String type = var.getType().toString();
					String frag = var.fragments().toString();
					String variableName = frag.substring(frag.indexOf('[') + 1, frag.indexOf('='));
					map.put(variableName, type);
					if(!keyType.contains(type))
    				{
						api.add(type + ".New");
    				}
				}
				//ReturnStatement
				else if(stmt instanceof ReturnStatement)
				{
				}
            
	            else if(stmt instanceof ForStatement)
	            {
	            	ForStatement fst = (ForStatement)stmt;
	            	Expression exp = fst.getExpression();
					Statement stat = fst.getBody();
	            }
	            
	            else if(stmt instanceof WhileStatement)
	            {
	            	ForStatement fst = (ForStatement)stmt;
	            	Expression exp = fst.getExpression();
					Statement stat = fst.getBody();
	            }
			}
        }
	}
	
	/***
	 * if index == 0
	 * read file and process
	 * else
	 * process the string directly
	 * @param path
	 * @param index
	 * @return
	 */
	char [] getFileContent(String path, int index)
	{
		if(index == 0)
		{
			ReadFile rf = new ReadFile();
			rf.setPath(path);
			char [] content = rf.getContent();
			return content;
		}
		else
		{
			String statContent = path;
			statContent = statContent.substring(statContent.indexOf('{') + 1, 
		       		statContent.lastIndexOf('}'));
		    while(statContent.charAt(0) == ' ' || statContent.charAt(0) == '\t' ||
		       		statContent.charAt(0) == '\n')
		    {
		      	statContent = statContent.substring(1, statContent.length());
		    }    
		    while(statContent.charAt(statContent.length() - 1) == ' ' || statContent.charAt(statContent.length() - 1) == '\t' ||
		       		statContent.charAt(statContent.length() - 1) == '\n')
		    {
		       	statContent = statContent.substring(0, statContent.length() - 1);
		    }
		    
		    return statContent.toCharArray();
		}
	}
	//对注释进行提取的内容的方法
		public static String util(String string){
			if(string.contains("@")){
				String str1=string.replaceAll("\\*", " ").replaceAll("/", " ");
				int index =str1.indexOf("@");
				String str2 = str1.substring(0, index);	
				StringBuffer sb=new StringBuffer();
				char[] str=str2.toCharArray();
				for (int i = 0; i < str.length; i++) {
						sb.append(str[i]);
				}
				return sb.toString();
				
			}else{
				String str1=string.replaceAll("\\*", " ").replaceAll("/", " ");
				
				char[] str=str1.toCharArray();
				StringBuffer sb=new StringBuffer();
				for (int i = 0; i < str.length; i++) {
					sb.append(str[i]);	
				}
				return sb.toString();
			}
		}
}
