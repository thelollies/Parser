package nodes;

import java.util.Scanner;

import main.CodeParser;
import main.NodeProcessor;
import main.Robot;
import main.RobotProgramNode;

public class IfNode implements RobotProgramNode, StatementNode{

	private Conditional conditional;
	private BlockNode block;
	private BlockNode elseBlock;
	
	@Override
	public void execute(Robot robot) {
		if(conditional.evaluate(robot))
			block.execute(robot);
		else if(elseBlock != null)
			elseBlock.execute(robot);
	}

	@Override
	public RobotProgramNode parse(Scanner s, CodeParser cp, NodeProcessor p) {
		p.process(s, cp, this);
		return this;
	}

	public void setConditional(Conditional conditional){
		this.conditional = conditional;
	}
	
	public void setBlock(BlockNode block){
		this.block = block;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("if (");
		sb.append(conditional.toString());
		sb.append(")");
		sb.append(block.toString());
		if(elseBlock != null){
			sb.append("else");
			sb.append(elseBlock.toString());
		}
		
		return sb.toString();
	}
	
	public void setElse(BlockNode elseBlock){
		this.elseBlock = elseBlock;
	}
}
