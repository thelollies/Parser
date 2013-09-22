package nodes;

import main.ParserFailureException;
import main.Robot;
import main.RobotInterruptedException;

public class SensorNode implements Expression, NumericalEvaluatable{
	public enum SensorType{FUELLEFT, OPPLR, OPPFB, NUMBARRELS, BARRELLR, BARRELFB, WALLDIST};

	private SensorType type;
	private Expression expression;
	
	public SensorNode(SensorType type){
		this.type = type;
	}
	
	public void setExpression(Expression expression){
		this.expression = expression;
	}

	public double evaluate(Robot robot){
		switch(type){
		case BARRELFB: 
			int valueFB = expression != null ? (int)expression.evaluate(robot) : 0;
			return robot.getBarrelFB(valueFB);
		case BARRELLR: 
			int valueLR = expression != null ? (int)expression.evaluate(robot) : 0;
			return robot.getBarrelLR(valueLR);
		case FUELLEFT: return robot.getFuel();
		case NUMBARRELS: return robot.numBarrels();
		case OPPFB: return robot.getOpponentFB();
		case OPPLR: return robot.getOpponentLR();
		case WALLDIST: return robot.getDistanceToWall();
		default: throw new ParserFailureException("Could not determine sensor type"); 
		}
	}
	
	@Override
	public String toString() {
		switch(type){
		case BARRELFB: return "barrelFB";
		case BARRELLR: return "barrelLR";
		case FUELLEFT: return "fuelLeft";
		case NUMBARRELS: return "numBarrels";
		case OPPFB: return "oppFB";
		case OPPLR: return "oppLR";
		case WALLDIST: return "wallDist";
		}
		
		throw new ParserFailureException("Unkown sensor type: " + type.toString());
	}
}
