package cn.edu.pku.sei.rsrepairjava.visitors;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.antlrjavaparser.api.stmt.BlockStmt;
import com.github.antlrjavaparser.api.stmt.Statement;
import com.github.antlrjavaparser.api.stmt.SwitchEntryStmt;

/**
 * Collect all statements of accepting node
 * @author Hansheng Zhang
 */
public class CollectStatementsVisitor extends StatementVisitor<Boolean> {
	
	private ArrayList<Statement> statements;
	
	public static String ignorePattern = ".*(super|this)[\\s\\t\\n]*\\(.*";
	
	public CollectStatementsVisitor() {
		statements = new ArrayList<Statement>();
	}
	
	public static boolean canIgnore(String s){
		Pattern p = Pattern.compile("\r|\n");
		Matcher m = p.matcher(s);
		s = m.replaceAll(" ");
		return s.matches(CollectStatementsVisitor.ignorePattern);
	}
	
	private void appendStatements(List<Statement> stmts){
		if (stmts == null)
			return;
		for (Statement s:stmts){
			String exprStr = s.toString();
			if (CollectStatementsVisitor.canIgnore(exprStr))
				continue;
			this.statements.add(s);
		}
	}
	
	@Override
	public void visitStatement(Statement s, Boolean arg) {
		// TODO Auto-generated method stub
		if (s instanceof BlockStmt){
			BlockStmt block = (BlockStmt) s;
			this.appendStatements(block.getStmts());
		}
		else if (s instanceof SwitchEntryStmt){
			SwitchEntryStmt switchEntry = (SwitchEntryStmt) s;
			this.appendStatements(switchEntry.getStmts());
		}
	}
	
	/**
	 * @return Collected statements
	 */
	public ArrayList<Statement> getStatements(){
		return this.statements;
	}
	
}
