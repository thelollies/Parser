package nodes;

import java.util.Scanner;

import main.CodeParser;
import main.NodeProcessor;
import main.Robot;
import main.RobotProgramNode;

public class WhileNode implements RobotProgramNode, StatementNode{

	private Conditional conditional;
	private BlockNode block;

	@Override
	public void execute(Robot robot) {
		while(conditional.evaluate(robot))
			block.execute(robot);
		
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("while (");
		sb.append(conditional.toString());
		sb.append(")");
		sb.append(block.toString());
		return sb.toString();
	}
	
	@Override
	public WhileNode parse(Scanner s, CodeParser cp, NodeProcessor p) {
		p.process(s, cp, this);
		return this;
	}
	
	public void setBlock(BlockNode block){
		this.block = block;
	}
	
	public void setConditional(Conditional conditional){
		this.conditional = conditional;
	}


}
