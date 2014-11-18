package cn.edu.pku.sei.rsrepairjava;

import java.io.File;
import com.github.antlrjavaparser.JavaParser;
import com.github.antlrjavaparser.api.CompilationUnit;
import com.github.antlrjavaparser.api.stmt.Statement;

import cn.edu.pku.sei.rsrepairjava.visitors.StatementVisitor;
import junit.framework.TestCase;

public class StatementVisitorTest extends TestCase {
	
	public static int cnt = 0;
	public void testPrintStatement(){
		String path = "resources/test/sample-project-backup-restore/sub1/Hello.java";
		try {
			CompilationUnit cu = JavaParser.parse(new File(path));
			cu.accept(new PrintStatementVisitor(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class PrintStatementVisitor extends StatementVisitor<Object>{
		@Override
		public void visitStatement(Statement s, Object arg){
			System.out.println(""+StatementVisitorTest.cnt++ +"-"+s.getClass().getName()+":"+s.toString());
		}
	}
}
