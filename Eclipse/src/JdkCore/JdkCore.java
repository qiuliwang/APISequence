package JdkCore;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.*;

public class JdkCore {
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
		JdkCore test = new JdkCore(path);
		
	}
	
	String FILE_PATH;
	
	JdkCore(String path)
	{
		FILE_PATH = path;
		char [] content = getFileContent(path, 0);
		//System.out.println(content.length);
		processJavaFile(content);
	}
	
	
	/**
	 * process the java file
	 * @param content
	 */
	void processJavaFile(char [] content)
	{
		ASTParser parser = ASTParser.newParser(AST.JLS3); //initialize    
        parser.setKind(ASTParser.K_COMPILATION_UNIT);     //to parse compilation unit  
        parser.setSource(content);      //content is a string which stores the <a href="http://lib.csdn.net/base/java" class='replace_word' title="Java 鐭ヨ瘑搴�" target='_blank' style='color:#df3434; font-weight:bold;'>Java </a>source  
        parser.setResolveBindings(true);  
        CompilationUnit result = (CompilationUnit) parser.createAST(null);
        List<?> types = result.types(); 
	    TypeDeclaration typeDec = (TypeDeclaration) types.get(0);   
	    System.out.println("className:"+typeDec.getName()); 
	    MethodDeclaration methodDec[] = typeDec.getMethods(); 
   	 	for (MethodDeclaration method : methodDec)    
   	 	{
   	 		System.out.println("=================="); 
   	 		//get method name  
   	 		SimpleName methodName=method.getName();  
   	 		System.out.println("method name:"+methodName);  
   	 		//get method parameters  
   	 		List<?> param=method.parameters();  
         	System.out.println("method parameters:"+param);  
         	//get method return type  
         	Type returnType=method.getReturnType2();  
         	System.out.println("method return type:"+returnType); 
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
                   System.out.println(stmt.toString());
    				ExpressionStatement expressStmt=(ExpressionStatement) stmt;
    				Expression express=expressStmt.getExpression();
    				if(express instanceof Assignment)
    				{
    					Assignment assign=(Assignment)express;
    					System.out.println("LHS:"+assign.getLeftHandSide()+"; ");
    					System.out.println("Op:"+assign.getOperator()+"; ");
    					System.out.println("RHS:"+assign.getRightHandSide());
    				}
    				else if(express instanceof MethodInvocation)
    				{
    					MethodInvocation mi=(MethodInvocation) express;
    					System.out.println("invocation name:"+mi.getName());
    					System.out.println("invocation exp:"+mi.getExpression());
    					System.out.println("invocation arg:"+mi.arguments());
    				}
    				System.out.println();
    			}
                
                //IfStatement

                else if(stmt instanceof IfStatement)
    			{
                    System.out.println(stmt.toString());
    				IfStatement ifstmt=(IfStatement) stmt;
    				Expression exp = ifstmt.getExpression();
    				Statement stat = ifstmt.getThenStatement();
    				System.out.println(exp.toString());
    				System.out.println(stat.toString());
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
                else if(stmt instanceof VariableDeclarationStatement)
    			{
                    System.out.println(stmt.toString());
    				VariableDeclarationStatement var=(VariableDeclarationStatement) stmt;
    				System.out.println("Type of variable:"+var.getType());
    				System.out.println("Name of variable:"+var.fragments());
    				System.out.println();
    			}
                //ReturnStatement
                else if(stmt instanceof ReturnStatement)
    			{
                    System.out.println(stmt.toString());
    				ReturnStatement rtstmt=(ReturnStatement) stmt;
    				System.out.println("return:"+rtstmt.getExpression());
    				System.out.println();
    			}
                
                else if(stmt instanceof ForStatement)
                {
                	ForStatement fst = (ForStatement)stmt;
                	Expression exp = fst.getExpression();
    				Statement stat = fst.getBody();
    				System.out.println(exp.toString());
    				System.out.println(stat.toString());
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
                
                else if(stmt instanceof WhileStatement)
                {
                	ForStatement fst = (ForStatement)stmt;
                	Expression exp = fst.getExpression();
    				Statement stat = fst.getBody();
    				System.out.println(exp.toString());
    				System.out.println(stat.toString());
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
        	
     } 
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
               System.out.println(stmt.toString());
				ExpressionStatement expressStmt=(ExpressionStatement) stmt;
				Expression express=expressStmt.getExpression();
				if(express instanceof Assignment)
				{
					Assignment assign=(Assignment)express;
					System.out.println("LHS:"+assign.getLeftHandSide()+"; ");
					System.out.println("Op:"+assign.getOperator()+"; ");
					System.out.println("RHS:"+assign.getRightHandSide());
				}
				else if(express instanceof MethodInvocation)
				{
					MethodInvocation mi=(MethodInvocation) express;
					System.out.println("invocation name:"+mi.getName());
					System.out.println("invocation exp:"+mi.getExpression());
					System.out.println("invocation arg:"+mi.arguments());
				}
				System.out.println();
			}
            
            //IfStatement

            else if(stmt instanceof IfStatement)
			{
                System.out.println(stmt.toString());
				IfStatement ifstmt=(IfStatement) stmt;
				Expression exp = ifstmt.getExpression();
				Statement stat = ifstmt.getThenStatement();
				System.out.println(exp.toString());
//				if(exp instanceof MethodInvocation)
//				{
//					MethodInvocation mi=(MethodInvocation) exp;
//					System.out.println("invocation name:"+mi.getName());
//					System.out.println("invocation exp:"+mi.getExpression());
//					System.out.println("invocation arg:"+mi.arguments());
//				}
				System.out.println(stat.toString());
			}
            else if(stmt instanceof VariableDeclarationStatement)
			{
                System.out.println(stmt.toString());
				VariableDeclarationStatement var=(VariableDeclarationStatement) stmt;
				System.out.println("Type of variable:"+var.getType());
				System.out.println("Name of variable:"+var.fragments());
				System.out.println();
			}
            //ReturnStatement
            else if(stmt instanceof ReturnStatement)
			{
                System.out.println(stmt.toString());
				ReturnStatement rtstmt=(ReturnStatement) stmt;
				System.out.println("return:"+rtstmt.getExpression());
				System.out.println();
			}
            
            else if(stmt instanceof ForStatement)
            {
            	ForStatement fst = (ForStatement)stmt;
            	Expression exp = fst.getExpression();
				Statement stat = fst.getBody();
				System.out.println(exp.toString());
				System.out.println(stat.toString());
            }
            
            else if(stmt instanceof WhileStatement)
            {
            	ForStatement fst = (ForStatement)stmt;
            	Expression exp = fst.getExpression();
				Statement stat = fst.getBody();
				System.out.println(exp.toString());
				System.out.println(stat.toString());
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
}
