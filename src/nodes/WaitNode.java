package nodes;

import java.util.Scanner;

import main.CodeParser;
import main.NodeProcessor;
import main.Robot;
import main.RobotProgramNode;

public class WaitNode implements RobotProgramNode, StatementNode{

	private Expression expression;
	
	@Override
	public void execute(Robot robot){
		int numWaits = expression != null ? (int)expression.evaluate(robot) : 1;
		
		for(int i = 0; i < numWaits; i++)
				robot.idleWait();
	}

	@Override
	public RobotProgramNode parse(Scanner s, CodeParser cp, NodeProcessor p) {
		p.process(s, cp, this);
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("wait");
		if(expression != null) sb.append("("+expression.toString()+")");
		sb.append(";");
		return sb.toString();
	}
	
	public void setExpression(Expression expression){
		this.expression = expression;
	}
	
}
