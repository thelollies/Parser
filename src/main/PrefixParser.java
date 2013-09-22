package main;

import java.util.Scanner;

import nodes.*;

public class PrefixParser implements CodeParser{

	@Override
	public RobotProgramNode parse(Scanner s, NodeProcessor p) {
		
		if(Parser.gobble("while", s)){
			return new WhileNode().parse(s, this, p);
		}
		else if(Parser.gobble("if", s)){
			return new IfNode().parse(s, this, p);
		}
		else if(Parser.gobble("loop", s)){
			return new LoopNode().parse(s, this, p);
		}
		else if(Parser.gobble("move", s)){
			return new MoveNode().parse(s, this, p);
		}
		else if(Parser.gobble("turnL", s)){
			return new TurnLNode().parse(s, this, p);
		}
		else if(Parser.gobble("turnR", s)){
			return new TurnRNode().parse(s, this, p);
		}
		else if(Parser.gobble("turnAround", s)){
			return new TurnAroundNode().parse(s, this, p);
		}
		else if(Parser.gobble("shieldOn", s)){
			return new ShieldOnNode().parse(s, this, p);
		}
		else if(Parser.gobble("shieldOff", s)){
			return new ShieldOffNode().parse(s, this, p);
		}
		else if(Parser.gobble("takeFuel", s)){
			return new TakeFuelNode().parse(s, this, p);
		}
		else if(Parser.gobble("wait", s)){
			return new WaitNode().parse(s, this, p);
		}
		
		System.out.println("Didn't parse " + s.next());
		
		return null;
	}

}
