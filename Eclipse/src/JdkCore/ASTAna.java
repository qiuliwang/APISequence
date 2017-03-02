package JdkCore;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class ASTAna {
	public static void main(String args[])
	{
		
	}
	
	char[] fileContent;
	
	ASTAna(String content)
	{
		fileContent = content.toCharArray();
		CompilationUnit result = getCompilationUnit(fileContent);
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
