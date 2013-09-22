package nodes;

import java.util.ArrayList;
import java.util.Scanner;

import main.CodeParser;
import main.NodeProcessor;
import main.Robot;
import main.RobotProgramNode;

public class ProgramNode implements RobotProgramNode{

	private ArrayList<RobotProgramNode> nodes;
	
	public ProgramNode(){
		nodes = new ArrayList<RobotProgramNode>();
	}
	
	public void addNode(RobotProgramNode node){
		nodes.add(node);
	}
	
	@Override
	public void execute(Robot robot) {
		for(RobotProgramNode n : nodes)
			n.execute(robot);
	}

	@Override
	public RobotProgramNode parse(Scanner s, CodeParser cp, NodeProcessor p) {
		p.process(s, cp, this);
		return this;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(RobotProgramNode node : nodes)
			s.append(node.toString() + "\n");
		return s.toString();
	}

}
