package nodes;

import java.util.ArrayList;
import java.util.Scanner;

import main.CodeParser;
import main.NodeProcessor;
import main.Robot;
import main.RobotProgramNode;

public class BlockNode implements RobotProgramNode{

	private ArrayList<StatementNode> statements;
	
	public BlockNode(){
		statements = new ArrayList<StatementNode>();
	}
	
	@Override
	public void execute(Robot r) {
		for(StatementNode s : statements)
			s.execute(r);
	}

	@Override
	public RobotProgramNode parse(Scanner s, CodeParser cp, NodeProcessor p) {
		p.process(s, cp, this);
		return this;
	}
	
	public void addStatement(StatementNode s){
		statements.add(s);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{\n");
		for(StatementNode s : statements){
			sb.append("\t");
			sb.append(s.toString());
			sb.append("\n");
		}
		String result = sb.toString();
		sb = new StringBuilder(result.replaceAll("(\\n)+", "\n   "));
		sb.append("}");
		return sb.toString();
	}

}
