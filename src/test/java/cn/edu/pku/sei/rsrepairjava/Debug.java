package cn.edu.pku.sei.rsrepairjava;

import java.io.File;
import java.io.IOException;

import com.github.antlrjavaparser.JavaParser;
import com.github.antlrjavaparser.ParseException;
import com.github.antlrjavaparser.api.CompilationUnit;
import com.github.antlrjavaparser.api.body.ConstructorDeclaration;
import com.github.antlrjavaparser.api.body.TypeDeclaration;
import com.github.antlrjavaparser.api.expr.BinaryExpr;
import com.github.antlrjavaparser.api.stmt.ExpressionStmt;
import junit.framework.TestCase;

public class Debug extends TestCase {
	
	public void testHello(){
		assertTrue(true);
		CompilationUnit cu;
		try {
			cu = JavaParser.parse(new File("D:\\workspace\\eclipse\\rsrepair-java\\resources\\test\\Debug.java"));
			System.out.println(cu.getBeginColumn());
			TypeDeclaration typeDec = cu.getTypes().get(0);
			ConstructorDeclaration body = (ConstructorDeclaration)typeDec.getMembers().get(0);
			BinaryExpr expr = (BinaryExpr) ((ExpressionStmt)body.getBlock().getStmts().get(0)).getExpression();
			System.out.println(expr.getBeginColumn());
			System.out.println(expr.getEndColumn());
			System.out.println(expr.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
