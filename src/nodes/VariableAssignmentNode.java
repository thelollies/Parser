package nodes;

import java.util.Scanner;

import main.CodeParser;
import main.NodeProcessor;
import main.Robot;
import main.RobotProgramNode;

public class VariableAssignmentNode implements RobotProgramNode, StatementNode{
	
	private Expression expression;
	private ProgramNode program;
	private String name;
	
	public void setProgram(ProgramNode program){
		this.program = program;
	}
	
	public void setExpression(Expression expression){
		this.expression = expression;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	@Override
	public void execute(Robot robot) {
		program.setVariable(name, (int)expression.evaluate(robot));
	}

	@Override
	public RobotProgramNode parse(Scanner s, CodeParser cp, NodeProcessor p) {
		p.process(s, cp, this);
		return this;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("$");
		sb.append(name);
		sb.append(" = ");
		sb.append(expression.toString());
		sb.append(";");
		return sb.toString();
	}

}
