package nodes;

import java.util.Scanner;

import main.CodeParser;
import main.NodeProcessor;
import main.Robot;
import main.RobotProgramNode;

public class TurnLNode implements RobotProgramNode, StatementNode{

	@Override
	public void execute(Robot robot) {
		robot.turnLeft();
	}

	@Override
	public RobotProgramNode parse(Scanner s, CodeParser cp, NodeProcessor p) {
		p.process(s, cp, this);
		return this;
	}
	
	@Override
	public String toString() {
		return "turnL;";
	}

}
