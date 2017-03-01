package JdkCore;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.eclipse.jdt.core.dom.*;

public class JdkCore {
	public static void main(String args[])
	{
		//System.out.println("good");
		//ASTParser astParser = ASTParser.newParser(/*API Level*/);  
		//astParser.setSource(/*Source Code*/); 
		//AST ast = new AST();
		CompilationUnit cu = getCompilationUnit("/Users/WangQL/Documents/git/"
				+ "Java/java-servlet-json/src/main/java/com/hmkcode/JSONServlet.java");
		//System.out.println(cu.toString());
		
		//get import infomation
		List<String> importList = cu.imports();
		for(Object obj : importList) {  
            ImportDeclaration importDec = (ImportDeclaration)obj;  
            System.out.println(importDec.getName());  
        }  
		
		//get class name
		List types = cu.types();    
	    TypeDeclaration typeDec = (TypeDeclaration) types.get(0);   
	    System.out.println("className:"+typeDec.getName()); 
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
  
        ASTParser parser = ASTParser.newParser(AST.JLS3); //initialize    
        parser.setKind(ASTParser.K_COMPILATION_UNIT);     //to parse compilation unit  
        parser.setSource(new String(input).toCharArray());      //content is a string which stores the <a href="http://lib.csdn.net/base/java" class='replace_word' title="Java 知识库" target='_blank' style='color:#df3434; font-weight:bold;'>Java </a>source  
        parser.setResolveBindings(true);  
        CompilationUnit result = (CompilationUnit) parser.createAST(null);   
        return result;  
    }  
}
