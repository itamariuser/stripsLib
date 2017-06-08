package forSokoban;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import algorithm.Action;
import algorithm.AndPredicate;
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
	
	static public Plannable<String> readFile(String fileName) {//CHANGE TO Plannable<POSITION> (x,y)
		try {
			
			ArrayList<char[]> level = new ArrayList<char[]>();//level.get(y)[x]
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = br.readLine()) != null) {
				level.add(line.toCharArray());
			}
			br.close();
			AndPredicate<String> kb=getKB(level);
			Plannable<String> plannable=new Plannable<String>() {
				
				@Override
				public Action<String> getSatisfyingAction(Predicate<String> top) {//data: (x ,y)
					//new action(name,data)=>new action(top.getname(), );
					int[] position=StringTo2Ints(top.getData());
					int x=position[0];
					int y=position[1];
					if(top.getName().startsWith("Crate"))// change to fit class CratePredicate<Position>
					{
						Action<String> act=new Action<String>("Crate #?");
						act.setEffects(new AndPredicate<>(top));//set effects to be "Crate", at position "(x,y)"
						
						act.setPreconditions(new AndPredicate<String>(new SimplePredicate<String>("Crate #?",new Position(x+1,y) );
						act.setPreconditions(new AndPredicate<String>(new SimplePredicate<String>("Crate #?",new Position(x,y+1) );
						act.setPreconditions(new AndPredicate<String>(new SimplePredicate<String>("Crate #?",new Position(x-1,y) );
						act.setPreconditions(new AndPredicate<String>(new SimplePredicate<String>("Crate #?",new Position(x,y-1) );
//						generate a list of legal directions to move crate (policy)
						
						//(you have to check if there's a box or a wall in the direction [like policy checks])
						//pick an action that has preconditions who fit the most to the knowledge base
					}
					
					return null;
				}
				
				@Override
				public AndPredicate<String> getKnowledgebase() {
					return kb;
				}
				
				@Override
				public Predicate<String> getGoal() {
					AndPredicate<String> goal=new AndPredicate<>("Goal predicates",new ArrayList<Predicate<String>>() );
					int goalCount=0;
					int index=-1;
					int goalIndex=0;
					for (Predicate<String> p: kb.getComponents()) {
						index++;
						if(p.getName().startsWith("Goal "))//find goals
						{
							goalCount++;
							goalIndex=index;
							goal.add(new SimplePredicate<String>("Crate #?",p.getData()));
						}
					}
					if(goalCount<=1)
					{
						SimplePredicate<String> temp=new SimplePredicate<String>("Crate #?",kb.getComponents().get(goalIndex).getData());//p.getData()
						return temp;
					}
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

	
	static public AndPredicate<String> getKB(ArrayList<char[]> level) {
		
		AndPredicate<String> kb = new AndPredicate<>("Knowledge base", null);
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
	
	/** (x,y)*/
//	static private int[] StringTo2Ints(String position)
//	{
//		int[] pos=new int[2];
//		pos[0]=Character.getNumericValue(position.charAt(0));
//		pos[1]=Character.getNumericValue(position.charAt(1));
//		return pos;
//	}
}
