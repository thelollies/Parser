package main;

import java.util.Scanner;


/**
 * Interface for all nodes that can be executed,
 * including the top level program node
 */

public interface RobotProgramNode {
	public void execute(Robot robot);
	@Override
	public String toString();
	public RobotProgramNode parse(Scanner s);
}
