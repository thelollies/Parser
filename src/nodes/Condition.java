package nodes;

import main.ParserFailureException;
import main.Robot;

public class Condition implements BooleanEvaluatable{
	public enum ConditionType{LT, GT, EQ, AND, OR, NOT};
	
	private ConditionType type;
	private Evaluatable argOne;
	private Evaluatable argTwo;
	
	@Override
	public boolean evaluate(Robot robot) {
		
		switch(type){
		case EQ: return ((NumericalEvaluatable)argOne).evaluate(robot) 
				== ((NumericalEvaluatable)argTwo).evaluate(robot);
		case GT: return ((NumericalEvaluatable)argOne).evaluate(robot) 
				> ((NumericalEvaluatable)argTwo).evaluate(robot);
		case LT: return ((NumericalEvaluatable)argOne).evaluate(robot) 
				< ((NumericalEvaluatable)argTwo).evaluate(robot);
		case AND: 
			return ((BooleanEvaluatable)argOne).evaluate(robot) 
					&& ((BooleanEvaluatable)argTwo).evaluate(robot);
		case OR:
			return ((BooleanEvaluatable)argOne).evaluate(robot) 
					|| ((BooleanEvaluatable)argTwo).evaluate(robot);
		case NOT:
			return !((BooleanEvaluatable)argOne).evaluate(robot);
		}
		
		throw new ParserFailureException("Could not determine condition type");
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		switch(type){
		case LT: sb.append("lt");break;
		case GT: sb.append("gt");break;
		case EQ: sb.append("eq");break;
		case AND: sb.append("and");break;
		case OR: sb.append("or");break;
		case NOT: sb.append("not");break;
		}
		
		sb.append("(");
		sb.append(argOne.toString());
		sb.append(", ");
		sb.append(argTwo.toString());
		sb.append(")");
		
		return sb.toString();
	}
	
	public void setArgOne(Evaluatable argOne){
		this.argOne = argOne;
	}
	
	public void setArgTwo(Evaluatable argTwo){
		this.argTwo = argTwo;
	}
	
	public void setType(ConditionType type){
		this.type = type;
	}
}