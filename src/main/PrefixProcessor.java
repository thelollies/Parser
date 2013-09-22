package main;

import java.util.Scanner;
import java.util.regex.Pattern;

import nodes.*;
import nodes.Condition.ConditionType;
import nodes.OperationNode.OperationType;
import nodes.SensorNode.SensorType;

public class PrefixProcessor implements NodeProcessor{

	private ProgramNode program;

	private static Pattern lt = Pattern.compile("lt");
	private static Pattern gt = Pattern.compile("gt");
	private static Pattern eq = Pattern.compile("eq");
	private static Pattern and = Pattern.compile("and");
	private static Pattern or = Pattern.compile("or");
	private static Pattern not = Pattern.compile("not");
	private static Pattern comma = Pattern.compile("( )*,( )*");
	private static Pattern elsePattern = Pattern.compile("else");
	private static Pattern elseIfPattern = Pattern.compile("elif");
	private static Pattern add = Pattern.compile("add");
	private static Pattern subtract = Pattern.compile("sub");
	private static Pattern multiply = Pattern.compile("mul");
	private static Pattern divide = Pattern.compile("div");
	private static Pattern variable = Pattern.compile("\\$[A-Za-z][A-Za-z0-9]*");
	private static Pattern equals = Pattern.compile("( )*=( )*");
	private static Pattern comment = Pattern.compile("//[A-Za-z0-9]*");

	public PrefixProcessor(ProgramNode program){
		this.program = program;
	}

	@Override
	public void process(Scanner s, CodeParser cp, BlockNode n) {
		Parser.gobble(Parser.OPENBRACE, s);
		while(!Parser.gobble(Parser.CLOSEBRACE, s)){

			if(Parser.gobble(comment, s)){
				s.nextLine();
				continue;
			}
			RobotProgramNode node = cp.parse(s, this);
			if(node instanceof StatementNode){
				StatementNode statement = (StatementNode) node;
				n.addStatement(statement);
			}
			else{
				Parser.fail("Non statement in block " + node, s);
			}

		}
	}

	@Override
	public void process(Scanner s, CodeParser cp, ProgramNode n) {}

	@Override
	public void process(Scanner s, CodeParser cp, WhileNode n) {
		Parser.gobble(Parser.OPENPAREN, s);
		n.setConditional(new Conditional().parse(s, cp, this));

		Parser.gobble(Parser.CLOSEPAREN, s);
		n.setBlock((BlockNode)new BlockNode().parse(s, cp, this));
	}

	@Override
	public void process(Scanner s, CodeParser cp, LoopNode n) {
		BlockNode block = (BlockNode)new BlockNode().parse(s, cp, this);
		n.setBlock(block);
	}

	@Override
	public void process(Scanner s, CodeParser cp, MoveNode n) {
		if(Parser.gobble(Parser.OPENPAREN, s)){
			n.setExpression(parseExpression(program, s, cp, this));

			if(!Parser.gobble(Parser.CLOSEPAREN, s))
				Parser.fail("Missing closing ) in move with args", s);
		}

		if(!Parser.gobble(";", s))
			Parser.fail("Move missing ;", s);
	}

	@Override
	public void process(Scanner s, CodeParser cp, TurnLNode n) {
		if(!Parser.gobble(";", s))
			Parser.fail("turnL missing ;" , s);
	}

	@Override
	public void process(Scanner s, CodeParser cp, TurnRNode n) {
		if(!Parser.gobble(";", s))
			Parser.fail("turnR missing ;" , s);
	}

	@Override
	public void process(Scanner s, CodeParser cp, ShieldOnNode n) {
		if(!Parser.gobble(";", s))
			Parser.fail("shieldOn missing ;" , s);
	}

	@Override
	public void process(Scanner s, CodeParser cp, ShieldOffNode n) {
		if(!Parser.gobble(";", s))
			Parser.fail("shieldOff missing ;" , s);
	}

	@Override
	public void process(Scanner s, CodeParser cp, TakeFuelNode n) {
		if(!Parser.gobble(";", s))
			System.out.println("TakeFuel missing ;");
	}

	@Override
	public void process(Scanner s, CodeParser cp, TurnAroundNode n) {
		if(!Parser.gobble(";", s))
			Parser.fail("turnAround missing ;" , s);
	}

	@Override
	public void process(Scanner s, CodeParser cp, WaitNode n) {

		if(Parser.gobble(Parser.OPENPAREN, s)){
			n.setExpression(parseExpression(program, s, cp, this));

			if(!Parser.gobble(Parser.CLOSEPAREN, s))
				Parser.fail("Missing closing ) in wait with args", s);
		}

		if(!Parser.gobble(";", s))
			Parser.fail("Wait missing ;", s);
	}

	@Override
	public void process(Scanner s, CodeParser cp, Conditional n) {
		boolean oneArg = false;
		ConditionType type = null;
		Condition condition = new Condition();

		if(Parser.gobble(lt, s)){
			type = ConditionType.LT;
		}
		else if(Parser.gobble(gt, s)){
			type = ConditionType.GT;
		}
		else if(Parser.gobble(eq, s)){
			type = ConditionType.EQ;
		}
		else if(Parser.gobble(and, s)){
			type = ConditionType.AND;
		}
		else if(Parser.gobble(or, s)){
			type = ConditionType.OR;
		}
		else if(Parser.gobble(not, s)){
			type = ConditionType.NOT;
			oneArg = true;
		}
		else{
			Parser.fail("Could not determine condition type " + s.next(), s);
		}

		condition.setType(type);

		if(!Parser.gobble(Parser.OPENPAREN, s))
			Parser.fail("No opening parenthesis in conditional condition", s);


		// if it's and/or/not it will be conditions
		// otherwise it will be expressions
		if(type == ConditionType.AND || 
				type == ConditionType.OR || 
				type == ConditionType.NOT){

			condition.setArgOne(new Conditional().parse(s, cp, this));

			// Only process second arg if one exists
			if(!oneArg){
				if(!Parser.gobble(comma, s))
					Parser.fail("No comma in conditional", s);

				condition.setArgTwo(new Conditional().parse(s, cp, this));
			}
		}
		else{
			condition.setArgOne(parseExpression(program, s, cp, this));

			if(!Parser.gobble(comma, s))
				Parser.fail("No comma in conditional", s);

			condition.setArgTwo(parseExpression(program, s, cp, this));
		}

		n.setCondition(condition);
		// Clean up the closing bracket
		if(!Parser.gobble(Parser.CLOSEPAREN, s))
			Parser.fail("No closing ) in conditional", s);
	}

	@Override
	public void process(Scanner s, CodeParser cp, IfNode n) {
		if(!Parser.gobble(Parser.OPENPAREN, s))
			Parser.fail("No opening ( in if", s);

		n.setConditional(new Conditional().parse(s, cp, this));

		if(!Parser.gobble(Parser.CLOSEPAREN, s))
			Parser.fail("No closing parenthesis in if statement", s);

		n.setBlock((BlockNode)new BlockNode().parse(s, cp, this));

		while(Parser.gobble(elseIfPattern, s)){
			if(!Parser.gobble(Parser.OPENPAREN, s))
				Parser.fail("No opening parenthesis on elif", s);
			
			IfNode elif = new IfNode();
			elif.setConditional(new Conditional().parse(s, cp, this));
			
			if(!Parser.gobble(Parser.CLOSEPAREN, s))
				Parser.fail("No closing parenthesis in elif statement", s);
			
			elif.setBlock((BlockNode)new BlockNode().parse(s, cp, this));
			
			n.addElif(elif);
		}
		
		if(Parser.gobble(elsePattern, s)){
			n.setElse((BlockNode)new BlockNode().parse(s, cp, this));
		}
	}

	@Override
	public void process(Scanner s, CodeParser cp, OperationNode n) {
		if(Parser.gobble(add, s))
			n.setType(OperationType.ADD);
		else if(Parser.gobble(subtract, s))
			n.setType(OperationType.SUBTRACT);
		else if(Parser.gobble(multiply, s))
			n.setType(OperationType.MULTIPLY);
		else if(Parser.gobble(divide, s))
			n.setType(OperationType.DIVIDE);
		else
			Parser.fail("Invalid operation type", s);

		if(!Parser.gobble(Parser.OPENPAREN, s))
			Parser.fail("No opening parenthesis in operation", s);

		n.setArgOne(parseExpression(program, s, cp, this));

		if(!Parser.gobble(comma, s))
			Parser.fail("No comma in operation", s);

		n.setArgTwo(parseExpression(program, s, cp, this));

		// Clean up the closing bracket
		if(!Parser.gobble(Parser.CLOSEPAREN, s))
			Parser.fail("No closing ) in operation", s);
	}

	private static Expression parseExpression(ProgramNode program, Scanner s, CodeParser cp, PrefixProcessor p){
		if(Parser.gobble("fuelLeft", s))
			return new SensorNode(SensorType.FUELLEFT);
		else if(Parser.gobble("oppLR", s))
			return new SensorNode(SensorType.OPPLR);
		else if(Parser.gobble("oppFB", s))
			return new SensorNode(SensorType.OPPFB);
		else if(Parser.gobble("numBarrels", s))
			return new SensorNode(SensorType.NUMBARRELS);
		else if(Parser.gobble("barrelLR", s)){
			SensorNode sensor = new SensorNode(SensorType.BARRELLR);
			if(Parser.gobble(Parser.OPENPAREN, s)){
				sensor.setExpression(parseExpression(program, s, cp, p));
				if(!Parser.gobble(Parser.CLOSEPAREN, s))
					Parser.fail("Missing closing parenthesis on barrelLR with args", s);
			}
			return sensor;
		}
		else if(Parser.gobble("barrelFB", s)){
			SensorNode sensor = new SensorNode(SensorType.BARRELFB);
			if(Parser.gobble(Parser.OPENPAREN, s)){
				sensor.setExpression(parseExpression(program, s, cp, p));
				if(!Parser.gobble(Parser.CLOSEPAREN, s))
					Parser.fail("Missing closing parenthesis on barrelFB with args", s);
			}
			return sensor;
		}
		else if(Parser.gobble("wallDist", s))
			return new SensorNode(SensorType.WALLDIST);
		else if(s.hasNext(variable))
			return new VariableNode(program, s.next(variable));
		else if(s.hasNext(Parser.NUMPAT))
			return new NumberNode(Double.parseDouble(s.next(Parser.NUMPAT)));
		else
			return new OperationNode().parse(s, cp, p);
	}

	@Override
	public void process(Scanner s, CodeParser cp, VariableAssignmentNode n) {
		n.setProgram(program);

		if(!s.hasNext(variable))
			Parser.fail("Invalid variable name", s);

		n.setName(s.next(variable).replace("$",""));

		if(!Parser.gobble(equals, s))
			Parser.fail("No = in variable assignment", s);

		n.setExpression(parseExpression(program, s, cp, this));
		
		if(!Parser.gobble(";", s))
			Parser.fail("Missing ; in variable declaration", s);
	}

}
