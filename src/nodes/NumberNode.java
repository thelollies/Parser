package nodes;

import main.Robot;

public class NumberNode implements Expression, NumericalEvaluatable{

	private double value;
	
	public NumberNode(double value){
		this.value = value;
	}
	
	@Override
	public double evaluate(Robot r) {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
