package main;

import java.util.Scanner;

import nodes.*;

public interface NodeProcessor {
	public void process(Scanner s, CodeParser cp, BlockNode n);
	public void process(Scanner s, CodeParser cp, ProgramNode n);
	public void process(Scanner s, CodeParser cp, WhileNode n);
	public void process(Scanner s, CodeParser cp, LoopNode n);
	public void process(Scanner s, CodeParser cp, MoveNode n);
	public void process(Scanner s, CodeParser cp, TurnLNode n);
	public void process(Scanner s, CodeParser cp, TurnRNode n);
	public void process(Scanner s, CodeParser cp, ShieldOnNode n);
	public void process(Scanner s, CodeParser cp, ShieldOffNode n);
	public void process(Scanner s, CodeParser cp, TakeFuelNode n);
	public void process(Scanner s, CodeParser cp, TurnAroundNode n);
	public void process(Scanner s, CodeParser cp, WaitNode n);
	public void process(Scanner s, CodeParser cp, Conditional n);
	public void process(Scanner s, CodeParser cp, IfNode n);
	public void process(Scanner s, CodeParser cp, OperationNode n);
}
