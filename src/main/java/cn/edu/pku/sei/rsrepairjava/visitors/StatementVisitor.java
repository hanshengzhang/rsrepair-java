package cn.edu.pku.sei.rsrepairjava.visitors;

import com.github.antlrjavaparser.api.Comment;
import com.github.antlrjavaparser.api.body.CatchParameter;
import com.github.antlrjavaparser.api.body.Resource;
import com.github.antlrjavaparser.api.expr.LambdaExpr;
import com.github.antlrjavaparser.api.expr.MethodReferenceExpr;
import com.github.antlrjavaparser.api.stmt.*;
import com.github.antlrjavaparser.api.type.Type;
import com.github.antlrjavaparser.api.visitor.VoidVisitorAdapter;

public class StatementVisitor<A> extends VoidVisitorAdapter<A> {

	public void visitStatement(Statement s, A arg){
	}
	
	@Override
	public void visit(AssertStmt s, A arg){
		super.visit(s, arg);
		visitStatement(s, arg);
	}
	@Override
	public void visit(BlockStmt s, A arg){
		super.visit(s, arg);
		visitStatement(s, arg);
	}
	@Override
	public void visit(BreakStmt s, A arg){
		super.visit(s, arg);
		visitStatement(s, arg);
	}
	
	//ignore CatchClause
	
	@Override
	public void visit(ContinueStmt s, A arg){
		super.visit(s, arg);
		visitStatement(s, arg);
	}
	@Override
	public void visit(DoStmt s, A arg){
		super.visit(s, arg);
		visitStatement(s, arg);
	}
	@Override
	public void visit(EmptyStmt s, A arg){
		super.visit(s, arg);
		visitStatement(s, arg);
	}
	@Override
	public void visit(ExplicitConstructorInvocationStmt s, A arg){
		super.visit(s, arg);
		visitStatement(s, arg);
	}
	@Override
	public void visit(ExpressionStmt s, A arg){
		super.visit(s, arg);
		visitStatement(s, arg);
	}
	@Override
	public void visit(ForeachStmt s, A arg){
		super.visit(s, arg);
		visitStatement(s, arg);
	}
	@Override
	public void visit(ForStmt s, A arg){
		super.visit(s, arg);
		visitStatement(s, arg);
	}
	@Override
	public void visit(IfStmt s, A arg){
		super.visit(s, arg);
		visitStatement(s, arg);
	}
	@Override
	public void visit(LabeledStmt s, A arg){
		super.visit(s, arg);
		visitStatement(s, arg);
	}
	@Override
	public void visit(ReturnStmt s, A arg){
		super.visit(s, arg);
		visitStatement(s, arg);
	}
	//ignore Statement
	@Override
	public void visit(SwitchEntryStmt s, A arg){
		super.visit(s, arg);
		visitStatement(s, arg);
	}
	@Override
	public void visit(SwitchStmt s, A arg){
		super.visit(s, arg);
		visitStatement(s, arg);
	}
	@Override
	public void visit(SynchronizedStmt s, A arg){
		super.visit(s, arg);
		visitStatement(s, arg);
	}
	@Override
	public void visit(ThrowStmt s, A arg){
		super.visit(s, arg);
		visitStatement(s, arg);
	}
	@Override
	public void visit(TryStmt s, A arg){
		super.visit(s, arg);
		visitStatement(s, arg);
	}
	@Override
	public void visit(TypeDeclarationStmt s, A arg){
		super.visit(s, arg);
		visitStatement(s, arg);
	}
	@Override
	public void visit(WhileStmt s, A arg){
		super.visit(s, arg);
		visitStatement(s, arg);
	}
	
	
	
	/*************Seperator**********************/
	@Override
	public void visit(Comment n, A arg) {
		// no longer used
	}
	@Override
	public void visit(CatchParameter n, A arg) {
		for (Type t: n.getTypeList()){
			t.accept(this, arg);
		}
        n.getId().accept(this, arg);
	}
	@Override
	public void visit(Resource n, A arg) {
		n.getType().accept(this, arg);
        n.getId().accept(this, arg);
        n.getExpression().accept(this, arg);
	}
	
	@Override
	public void visit(LambdaExpr n, A arg) {
		//nothing
	}

	@Override
	public void visit(MethodReferenceExpr n, A arg) {
		//nothing
	}
	
}
