package nodes;

import main.Robot;

public class VariableNode implements Expression, NumericalEvaluatable{

	private ProgramNode program;
	private String name;
	
	public VariableNode(ProgramNode program, String name) {
		this.program = program;
		this.name = name.replace("$", "");
	}
	
	@Override
	public double evaluate(Robot r) {
		return program.getVariable(name);
	}
	
	@Override
	public String toString() {
		return "$" + name;
	}
}
