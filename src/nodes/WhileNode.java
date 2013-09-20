package nodes;

import java.util.Scanner;

import main.Parser;
import main.Robot;
import main.RobotProgramNode;

public class WhileNode implements RobotProgramNode, ExecutableNode{

	private ConditionNode condition;
	private BlockNode block;

	@Override
	public void execute(Robot robot) {
		if(condition.isTrue()){
			block.execute(robot);
		}
	}

	@Override
	public String toString() {
		StringBuilder whileString = new StringBuilder("while ( ");
		whileString.append(condition.toString());
		whileString.append(" ) ");
		whileString.append(block.toString());
		return super.toString();
	}

	@Override
	public WhileNode parse(Scanner s) {

		Parser.gobble(Parser.OPENPAREN, s);
		condition = new ConditionNode();
		condition.parse(s);
		Parser.gobble(Parser.CLOSEPAREN, s);
		block = new BlockNode();
		block.parse(s);

		return this;
	}

}
