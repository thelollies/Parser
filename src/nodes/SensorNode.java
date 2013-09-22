package nodes;

import main.ParserFailureException;
import main.Robot;
import main.RobotInterruptedException;

public class SensorNode implements Expression, NumericalEvaluatable{
	public enum SensorType{FUELLEFT, OPPLR, OPPFB, NUMBARRELS, BARRELLR, BARRELFB, WALLDIST};

	private SensorType type;
	
	public SensorNode(SensorType type){
		this.type = type;
	}

	public double evaluate(Robot robot){
		switch(type){
		case BARRELFB: return robot.getBarrelFB(0);
		case BARRELLR: return robot.getBarrelLR(0);
		case FUELLEFT: return robot.getFuel();
		case NUMBARRELS: return robot.numBarrels();
		case OPPFB: return robot.getOpponentFB();
		case OPPLR: return robot.getOpponentLR();
		case WALLDIST: return robot.getDistanceToWall();
		default: throw new RobotInterruptedException(); 
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
