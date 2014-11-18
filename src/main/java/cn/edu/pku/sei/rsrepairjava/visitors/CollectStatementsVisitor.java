package cn.edu.pku.sei.rsrepairjava.visitors;

import java.util.ArrayList;

import com.github.antlrjavaparser.api.stmt.BlockStmt;
import com.github.antlrjavaparser.api.stmt.Statement;
import com.github.antlrjavaparser.api.stmt.SwitchEntryStmt;

/**
 * Collect all statements of accepting node
 * @author Hansheng Zhang
 */
public class CollectStatementsVisitor extends StatementVisitor<Boolean> {
	
	private ArrayList<Statement> statements;
	
	public CollectStatementsVisitor() {
		statements = new ArrayList<Statement>();
	}
	
	@Override
	public void visitStatement(Statement s, Boolean arg) {
		// TODO Auto-generated method stub
		if (s instanceof BlockStmt){
			BlockStmt block = (BlockStmt) s;
			statements.addAll(block.getStmts());
		}
		else if (s instanceof SwitchEntryStmt){
			SwitchEntryStmt switchEntry = (SwitchEntryStmt) s;
			statements.addAll(switchEntry.getStmts());
		}
	}
	
	/**
	 * @return Collected statements
	 */
	public ArrayList<Statement> getStatements(){
		return this.statements;
	}
	
}
