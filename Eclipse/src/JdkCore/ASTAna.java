package JdkCore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class ASTAna {
	public static void main(String args[])
	{
		
	}
	
	char[] fileContent;
	
	ASTAna(String content)
	{
		fileContent = content.toCharArray();
		CompilationUnit cu = getCompilationUnit(fileContent);
		TypeDeclaration typeDec = getClass(cu);
		MethodDeclaration methodDec[] = getFunctions(typeDec);
		analysisFunctions(methodDec);
	}
	
	//class name
	TypeDeclaration getClass(CompilationUnit cu)
	{
		List<?> types = cu.types();    
	    TypeDeclaration typeDec = (TypeDeclaration) types.get(0);   
	    System.out.println("className:"+typeDec.getName()); 
	    return typeDec;
	}
	
	//functions
    MethodDeclaration [] getFunctions(TypeDeclaration typeDec)
    {
    	MethodDeclaration methodDec[] = typeDec.getMethods(); 
    	return methodDec;
    }
    
    //get all functions
    //analysis
    List<String> analysisFunctions(MethodDeclaration [] methodDec)
    {
    	//System.out.println("dddd");
    	List<String> lis = new ArrayList<String>();
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
             List<String> temp = analysisNode(statements);
         }   	
    	return lis;
    }
    
    List<String> analysisNode(List<?> statements)
    {
    	List<String> lis = new ArrayList<String>();
    	
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
				if(exp instanceof MethodInvocation)
				{
					MethodInvocation mi=(MethodInvocation) exp;
					System.out.println("invocation name:"+mi.getName());
					System.out.println("invocation exp:"+mi.getExpression());
					System.out.println("invocation arg:"+mi.arguments());
				}
				System.out.println(stat.toString());
				
				
				
//				InfixExpression wex=(InfixExpression) ifstmt.getExpression();
//				System.out.println("if-LHS:"+wex.getLeftOperand()+"; ");
//				System.out.println("if-op:"+wex.getOperator()+"; ");
//				System.out.println("if-RHS:"+wex.getRightOperand());
//				System.out.println();
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
    	
    	return lis;
    }

	CompilationUnit getCompilationUnit(char [] fileContent)
	{
		ASTParser parser = ASTParser.newParser(AST.JLS3); //initialize    
        parser.setKind(ASTParser.K_COMPILATION_UNIT);     //to parse compilation unit  
        parser.setSource(fileContent);      //content is a string which stores the <a href="http://lib.csdn.net/base/java" class='replace_word' title="Java 知识库" target='_blank' style='color:#df3434; font-weight:bold;'>Java </a>source  
        parser.setResolveBindings(true);  
        CompilationUnit result = (CompilationUnit) parser.createAST(null);   
        return result;
	}
	
	void setContent(String content)
	{
		fileContent = content.toCharArray();
	}
}
