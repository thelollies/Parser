package nodes;

import main.Robot;

public interface Expression extends Evaluatable{
	public double evaluate(Robot r);
}
