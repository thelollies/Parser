package main;

import java.util.Scanner;

public interface CodeParser {
	RobotProgramNode parse(Scanner s, NodeProcessor p);
}
