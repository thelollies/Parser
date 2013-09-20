package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;

import nodes.WhileNode;

/** The parser and interpreter.
    The top level parse function, a main method for testing, and several
    utility methods are provided.
    You need to implement parseProgram and all the rest of the parser.
 */

public class Parser {

	// TODO change to visitor pattern
	// https://en.wikipedia.org/wiki/Visitor_pattern#Java_example

	/**
	 * Top level parse method, called by the World
	 */
	static RobotProgramNode parseFile(File code){
		Scanner scan = null;
		try {
			scan = new Scanner(code);

			// the only time tokens can be next to each other is
			// when one of them is one of (){},;
			scan.useDelimiter("\\s+|(?=[{}(),;])|(?<=[{}(),;])");

			RobotProgramNode n = parseProgram(scan);  // You need to implement this!!!

			scan.close();
			return n;
		} catch (FileNotFoundException e) {
			System.out.println("Robot program source file not found");
		} catch (ParserFailureException e) {
			System.out.println("Parser error:");
			System.out.println(e.getMessage());
			scan.close();
		}
		return null;
	}

	/** For testing the parser without requiring the world */

	public static void main(String[] args){
		if (args.length>0){
			for (String arg : args){
				File f = new File(arg);
				if (f.exists()){
					System.out.println("Parsing '"+ f+"'");
					RobotProgramNode prog = parseFile(f);
					System.out.println("Parsing completed ");
					if (prog!=null){
						System.out.println("================\nProgram:");
						System.out.println(prog);}
					System.out.println("=================");
				}
				else {System.out.println("Can't find file '"+f+"'");}
			}
		} else {
			while (true){
				JFileChooser chooser = new JFileChooser(".");//System.getProperty("user.dir"));
				int res = chooser.showOpenDialog(null);
				if(res != JFileChooser.APPROVE_OPTION){ break;}
				RobotProgramNode prog = parseFile(chooser.getSelectedFile());
				System.out.println("Parsing completed");
				if (prog!=null){
					System.out.println("Program: \n"+prog);
				}
				System.out.println("=================");
			}
		}
		System.out.println("Done");
	}

	// Useful Patterns

	public static Pattern NUMPAT = Pattern.compile("-?\\d+");  //("-?(0|[1-9][0-9]*)");
	public static Pattern OPENPAREN = Pattern.compile("\\(");
	public static Pattern CLOSEPAREN = Pattern.compile("\\)");
	public static Pattern OPENBRACE = Pattern.compile("\\{");
	public static Pattern CLOSEBRACE = Pattern.compile("\\}");

	/**    PROG  ::= STMT+
	 */
	static RobotProgramNode parseProgram(Scanner s){



		while(s.hasNext()){
			// check for action/loop/if/while

			if(gobble("while", s)){
				new WhileNode().parse(s);
			}
			else if(gobble("if", s)){
				System.out.println("if");
			}
			else if(gobble("loop", s)){
				System.out.println("loop");
			}
			else if(gobble("move", s)){
				System.out.println("move");
			}
			else if(gobble("turnL", s)){
				System.out.println("turnL");
			}
			else if(gobble("turnR", s)){
				System.out.println("turnR");
			}
			else if(gobble("turnAround", s)){
				System.out.println("turnAround");
			}
			else if(gobble("shieldOn", s)){
				System.out.println("shieldOn");
			}
			else if(gobble("shieldOff", s)){
				System.out.println("shieldOff");
			}
			else if(gobble("takeFuel", s)){
				System.out.println("takeFuel");
			}
			else if(gobble("wait", s)){
				System.out.println("wait");
			}
			else{
				System.out.println("Didn't parse " + s.next());
			}
			// then check for actions

		}



		return null;     // just so it will compile!!
	}








	//utility methods for the parser
	/**
	 * Report a failure in the parser.
	 */
	static void fail(String message, Scanner s){
		String msg = message + "\n   @ ...";
		for (int i=0; i<5 && s.hasNext(); i++){
			msg += " " + s.next();
		}
		throw new ParserFailureException(msg+"...");
	}

	/**
       If the next token in the scanner matches the specified pattern,
       consume the token and return true. Otherwise return false without
       consuming anything.
       Useful for dealing with the syntactic elements of the language
       which do not have semantic content, and are there only to
       make the language parsable.
	 */
	public static boolean gobble(String p, Scanner s){
		if (s.hasNext(p)) { s.next(); return true;}
		else { return false; }
	}
	public static boolean gobble(Pattern p, Scanner s){
		if (s.hasNext(p)) { s.next(); return true;}
		else { return false; }
	}


}

// You could add the node classes here, as long as they are not declared public (or private)
