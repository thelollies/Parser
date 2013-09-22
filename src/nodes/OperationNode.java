package nodes;

import java.util.Scanner;

import main.CodeParser;
import main.NodeProcessor;
import main.ParserFailureException;
import main.Robot;

public class OperationNode implements Expression, NumericalEvaluatable{

	public enum OperationType{ADD, SUBTRACT, MULTIPLY, DIVIDE};
	private OperationType type;
	private Expression argOne;
	private Expression argTwo;
	
	@Override
	public double evaluate(Robot r) {
		switch (type){
		case ADD: return argOne.evaluate(r) + argTwo.evaluate(r);
		case SUBTRACT: return argOne.evaluate(r) - argTwo.evaluate(r);
		case MULTIPLY: return argOne.evaluate(r) * argTwo.evaluate(r);
		case DIVIDE: return argOne.evaluate(r) / argTwo.evaluate(r);
		}
		
		throw new ParserFailureException("Unkown operation type");
	}
	
	public void setType(OperationType type){
		this.type = type;
	}
	
	public void setArgOne(Expression argOne){
		this.argOne = argOne;
	}
	
	public void setArgTwo(Expression argTwo){
		this.argTwo = argTwo;
	}
	
	public OperationNode parse(Scanner s, CodeParser cp, NodeProcessor p) {
		p.process(s, cp, this);
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		switch (type){
		case ADD: sb.append("add");break;
		case SUBTRACT: sb.append("sub");break;
		case MULTIPLY: sb.append("mul");break;
		case DIVIDE: sb.append("div");break;
		}
		
		sb.append("(");
		sb.append(argOne.toString());
		sb.append(", ");
		sb.append(argTwo.toString());
		sb.append(")");
		return sb.toString();
	}
	
}
