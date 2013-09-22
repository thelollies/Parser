package nodes;

import java.util.Scanner;

import main.CodeParser;
import main.NodeProcessor;
import main.Robot;

public class Conditional implements BooleanEvaluatable{

	private Condition condition;

	public boolean evaluate(Robot robot) {
		return condition.evaluate(robot);
	}
	
	public Conditional parse(Scanner s, CodeParser cp, NodeProcessor p){
		p.process(s, cp, this);
		return this;
	}

	@Override
	public String toString() {
		return condition.toString();
	}

	public void setCondition(Condition condition){
		this.condition = condition;
	}

}
