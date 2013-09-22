package nodes;

import java.util.Scanner;

import main.CodeParser;
import main.NodeProcessor;
import main.Robot;
import main.RobotProgramNode;

public class LoopNode implements RobotProgramNode, StatementNode{

	private BlockNode block;
	
	@Override
	public void execute(Robot robot) {
		while(true)
			block.execute(robot);
	}

	@Override
	public RobotProgramNode parse(Scanner s, CodeParser cp, NodeProcessor p) {
		p.process(s, cp, this);
		return this;
	}
	
	public void setBlock(BlockNode block){
		this.block = block;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("loop ");
		sb.append(block.toString());
		return sb.toString();
	}

}
