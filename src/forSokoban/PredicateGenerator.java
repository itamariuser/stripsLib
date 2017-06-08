package forSokoban;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Set;

import algorithm.Action;
import algorithm.ComplexPredicate;
import algorithm.Plannable;
import algorithm.Predicate;
import algorithm.SimplePredicate;

public class PredicateGenerator  {// Convert level
																// to
																// predicates,
																// TODO: make
																// object
																// adapter from
																// sokoban policy
																// to Plannable

	static public ComplexPredicate<String> getGoal(ComplexPredicate<String> kb)
	{
		ComplexPredicate<String> goal=new ComplexPredicate<>("Goal predicates",null );
		
		for (Predicate<String> p: kb.getComponents()) {
			if(p.getName().startsWith("Goal "))//find goals
			{
				goal.add(new SimplePredicate<String>("Crate #?",p.getData()));
			}
		}
		
		return goal;
	}
	
	static public Plannable<String> readFile(String fileName) {
		try {
			
			ArrayList<char[]> level = new ArrayList<char[]>();
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = br.readLine()) != null) {
				level.add(line.toCharArray());
			}
			br.close();
			ComplexPredicate<String> kb=getKB(level);
			ComplexPredicate<String> goal=getGoal(kb);
			Plannable<String> plannable=new Plannable<String>() {
				
				@Override
				public boolean isSatisifed(Predicate<String> p) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public Action<String> getSatisfyingAction(Predicate<String> top) {
					// TODO: from all possible actions, return the action with most satisfied preconditions
					return null;
				}
				
				@Override
				public ComplexPredicate<String> getKnowledgebase() {
					return kb;
				}
				
				@Override
				public ComplexPredicate<String> getGoal() {
					return goal;
				}

				@Override
				public Set<Action<String>> getSatisfyingActions(Predicate<String> top) {
					//TODO: get position of "top" from top.getData
					//   	search for the position in the level
					//		return all legal actions (up down left right) it can take
					//		optional: in strips, use strips.clone() to run all possible outcomes in
					//		a seperate thread
					//		
					return null;
				}
			};	
			return plannable;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static ComplexPredicate<String> getGoal() {

		return null;
	}

	
	static public ComplexPredicate<String> getKB(ArrayList<char[]> level) {
		
		ComplexPredicate<String> kb = new ComplexPredicate<>("Knowledge base", null);
		int crateCount = 0;
		int goalCount = 0;
		for (int i = 0; i < level.size(); i++) {
			for (int j = 0; j < level.get(i).length; j++) {
				switch (level.get(i)[j]) {
				case ('#'):
					kb.add(new SimplePredicate<String>("Wall", "(" + i + "," + j + ")"));
					break;
				case (' '):
					kb.add(new SimplePredicate<String>("BlankSpace", "(" + i + "," + j + ")"));
					break;
				case ('A'):
					kb.add(new SimplePredicate<String>("Player1", "(" + i + "," + j + ")"));
					break;
				case ('@'):
					kb.add(new SimplePredicate<String>("Crate #" + (crateCount++), "(" + i + "," + j + ")"));
					break;
				case ('o'):
					kb.add(new SimplePredicate<String>("Goal #" + (goalCount++), "(" + i + "," + j + ")"));
					break;
				default:
					break;
				}
			}
		}
		return kb;
	}
}
