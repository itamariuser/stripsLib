package forSokoban;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import algorithm.Action;
import algorithm.AndPredicate;
import algorithm.Plannable;
import algorithm.Position;
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
	
	static public Plannable<Position> readFile(String fileName) {//CHANGE TO Plannable<POSITION> (x,y)
		try {
			
			ArrayList<char[]> level = new ArrayList<char[]>();//level.get(y)[x]
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = br.readLine()) != null) {
				level.add(line.toCharArray());
			}
			br.close();
			AndPredicate<Position> kb=getKB(level);
			Plannable<Position> plannable=new Plannable<Position>() {
				
				@Override
				public Action<Position> getSatisfyingAction(Predicate<Position> top) {//data: (x ,y)
					//new action(name,data)=>new action(top.getname(), );
					int x=top.getData().getX();
					int y=top.getData().getY();
					if(top.getName().startsWith("Crate"))// change to fit class CratePredicate<Position>
					{
						List<Action<Position>> possibleActions=getSatisfyingActions(top);
						
						Action<Position> actionToPerform=new Action<>("Move");//Move to ____
						//pick an action that has preconditions who fit the most to the knowledge base
						
					}
					
					return null;
				}
				
				@Override
				public AndPredicate<Position> getKnowledgebase() {
					return kb;
				}
				
				@Override
				public Predicate<Position> getGoal() {
					AndPredicate<Position> goal=new AndPredicate<>("Goal predicates",new ArrayList<Predicate<Position>>() );
					int goalCount=0;
					int index=-1;
					int goalIndex=0;
					for (Predicate<Position> p: kb.getComponents()) {
						index++;
						if(p.getName().startsWith("Goal "))//find goals
						{
							goalCount++;
							goalIndex=index;
							goal.add(new SimplePredicate<Position>("Crate #?",p.getData()));
						}
					}
					if(goalCount<=1)
					{
						SimplePredicate<Position> temp=new SimplePredicate<Position>("Crate #?",kb.getComponents().get(goalIndex).getData());//p.getData()
						return temp;
					}
					return goal;
				}

				@Override
				public List<Action<Position>> getSatisfyingActions(Predicate<Position> top) {
					int x=top.getData().getX();
					int y=top.getData().getY();
					if(top.getName().startsWith("Crate"))// change to fit class CratePredicate<Position>
					{
						ArrayList<Action<Position>> possibleActions= new ArrayList<>();
						Action<Position> act=new Action<Position>("Crate #?");
						act.setEffects(new AndPredicate<>(new SimplePredicate<>("Crate #?",new Position(x,y)),new NotPredicate<Position>(new SimplePredicate<Position>("Non Solid", new Position(x,y)))));//set effects to be "Crate at position "(x,y)", "No non solid at position (x,y)" (which means crate is in pos)
						SimplePredicate<Position> nextSpaceIsFree=new SimplePredicate<Position>("Non Solid",new Position(x,y));//no wall or crate in next point
						SimplePredicate<Position> player1IsAtPosition=new SimplePredicate<Position>("Player1",null);//add player in position to push the crate
						SimplePredicate<Position> CrateIsAtPosition=new SimplePredicate<Position>("Crate #?",null);//crate is at position to be pushed
						
						CrateIsAtPosition.setData(new Position(x-1,y));
						player1IsAtPosition.setData(new Position(x-2,y));
						act.setPreconditions(new AndPredicate<Position>(CrateIsAtPosition,player1IsAtPosition,nextSpaceIsFree));//push crate to right
						possibleActions.add(act);
						
						CrateIsAtPosition.setData(new Position(x,y-1));
						player1IsAtPosition.setData(new Position(x,y-2));
						act.setPreconditions(new AndPredicate<Position>(CrateIsAtPosition,player1IsAtPosition,nextSpaceIsFree));//push crate to down
						possibleActions.add(act);
						
						CrateIsAtPosition.setData(new Position(x+1,y));
						player1IsAtPosition.setData(new Position(x+2,y));
						act.setPreconditions(new AndPredicate<Position>(CrateIsAtPosition,player1IsAtPosition,nextSpaceIsFree));//push crate to left
						possibleActions.add(act);
						
						CrateIsAtPosition.setData(new Position(x,y+1));
						player1IsAtPosition.setData(new Position(x,y+2));
						act.setPreconditions(new AndPredicate<Position>(CrateIsAtPosition,player1IsAtPosition,nextSpaceIsFree));//push crate to up
						possibleActions.add(act);
						
						return possibleActions;
//						generate a list of legal directions to move crate (policy)
						
						//(you have to check if there's a box or a wall in the direction [like policy checks])
						//pick an action that has preconditions who fit the most to the knowledge base
					}
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

	
	static public AndPredicate<Position> getKB(ArrayList<char[]> level) {
		
		AndPredicate<Position> kb = new AndPredicate<>("Knowledge base", null);
		int crateCount = 0;
		int goalCount = 0;
		for (int i = 0; i < level.size(); i++) {
			for (int j = 0; j < level.get(i).length; j++) {
				switch (level.get(i)[j]) {
				case ('#'):
					kb.add(new SimplePredicate<Position>("Wall", new Position(i,j)));
					break;
				case (' '):
					kb.add(new SimplePredicate<Position>("BlankSpace", new Position(i,j)));
					kb.add(new SimplePredicate<Position>("Non Solid", new Position(i,j)));
					break;
				case ('A'):
					kb.add(new SimplePredicate<Position>("Player1", new Position(i,j)));
					break;
				case ('@'):
					kb.add(new SimplePredicate<Position>("Crate #" + (crateCount++), new Position(i,j)));
					break;
				case ('o'):
					kb.add(new SimplePredicate<Position>("Goal #" + (goalCount++), new Position(i,j)));
					kb.add(new SimplePredicate<Position>("Non Solid", new Position(i,j)));
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
