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
		//System.out.println("good");
		//ASTParser astParser = ASTParser.newParser(/*API Level*/);  
		//astParser.setSource(/*Source Code*/); 
		//AST ast = new AST();
		String file1 = "/Users/WangQL/Documents/git/"
				+ "Java/java-servlet-json/src/main/java/com/hmkcode/JSONServlet.java";
		String file2 = "/Users/WangQL/Documents/git/hadoop/hadoop-common-project/"
				+ "hadoop-common/src/test/java/org/apache/hadoop/fs/contract/AbstractContractAppendTest.java";
		String file3 = "/Users/WangQL/Desktop/test.java";
		CompilationUnit cu = getCompilationUnit(file3);
		
		//System.out.println(cu.toString());
		
		//get import infomation
		List<?> importList = cu.imports();
		for(Object obj : importList) {  
            ImportDeclaration importDec = (ImportDeclaration)obj;  
            System.out.println(importDec.getName());  
        }  
		
		//get class name
		List<?> types = cu.types();    
	    TypeDeclaration typeDec = (TypeDeclaration) types.get(0);   
	    System.out.println("className:"+typeDec.getName()); 
	    
	    MethodDeclaration methodDec[] = typeDec.getMethods();    
        System.out.println("Method:");    
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
            
            //
            //AST ast = method.getAST();
            //System.out.println(ast.toString());
            Block body=method.getBody();  
            List<?> statements=body.statements();   //get the statements of the method body  
            Iterator<?> iter=statements.iterator();  
            while(iter.hasNext())  
            {  
                //get each statement  
                Statement stmt=(Statement)iter.next();  
                //System.out.println(stmt.toString());
                
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

//					InfixExpression wex=(InfixExpression) ifstmt.getExpression();
//					System.out.println("if-LHS:"+wex.getLeftOperand()+"; ");
//					System.out.println("if-op:"+wex.getOperator()+"; ");
//					System.out.println("if-RHS:"+wex.getRightOperand());
//					System.out.println();
				}
                
                //VariableDeclarationStatement
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
                
            }
        }
	    
	}
    public static CompilationUnit getCompilationUnit(String javaFilePath){  
        byte[] input = null;  
        try {  
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(javaFilePath));  
            input = new byte[bufferedInputStream.available()];  
                bufferedInputStream.read(input);  
                bufferedInputStream.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        
        char[] content = new String(input).toCharArray();
        ASTParser parser = ASTParser.newParser(AST.JLS3); //initialize    
        parser.setKind(ASTParser.K_COMPILATION_UNIT);     //to parse compilation unit  
        parser.setSource(content);      //content is a string which stores the <a href="http://lib.csdn.net/base/java" class='replace_word' title="Java 知识库" target='_blank' style='color:#df3434; font-weight:bold;'>Java </a>source  
        parser.setResolveBindings(true);  
        CompilationUnit result = (CompilationUnit) parser.createAST(null);   
        return result;  
    }  
}
