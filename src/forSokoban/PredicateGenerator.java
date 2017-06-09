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
	
	static public Plannable<Position> readFile(String fileName) {
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
						List<Action<Position>> possibleActions=getSatisfyingActions(top);
						System.out.println(possibleActions.get(0));
						int fitCount=0;
						int maxFit=-1;
						Action<Position> mostFitAction=null;
						for (Action<Position> act : possibleActions) {
							fitCount=0;
							for (Predicate<Position> precondition : act.getPreconditions().getComponents()) {
								fitCount = kb.satisfies(precondition)? fitCount+1:fitCount;
							}
							if(fitCount>maxFit)
							{
								maxFit=fitCount;
								mostFitAction=act;
							}
						}				
					return mostFitAction;
				}
				
				@Override
				public AndPredicate<Position> getKnowledgebase() {
					return kb;
				}
				
				@Override
				public Predicate<Position> getGoal() {
					AndPredicate<Position> goal=new AndPredicate<>("Goal_Predicates",new ArrayList<Predicate<Position>>() );
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
						ArrayList<Action<Position>> possibleActions= new ArrayList<>();//this list will be returned
						ArrayList<Predicate<Position>> toGenerate=new ArrayList<>();//all items in this list will be generated for each action
						ArrayList<Predicate<Position>> toRemove=new ArrayList<>();//items in this list will be removed afer each generation of action
						Action<Position> act=new Action<Position>("Move_Crate_To_Position");
						act.setEffects(new AndPredicate<>(new SimplePredicate<>("Crate #?",new Position(x,y)),new NotPredicate<Position>(new SimplePredicate<Position>("Non Solid", new Position(x,y)))));//set effects to be "Crate at position "(x,y)", "No non solid at position (x,y)" (which means crate is in pos)
						char objInNextPos=level.get(y)[x];
						if(!(objInNextPos==' ' || objInNextPos=='o'))//if there's a solid at position, then add targetSpaceIsFree predicate
						{
							toGenerate.add(new NotPredicate<>(new SimplePredicate<Position>("Crate",new Position(x,y))));//add a predicate: no crate in next point
							
						}
						SimplePredicate<Position> player1IsAtPosition=new SimplePredicate<Position>("Player1",null);//add player in position to push the crate
						SimplePredicate<Position> CrateIsAtPosition=new SimplePredicate<Position>("Crate #?",null);//crate is at position to be pushed
						
						//TODO: if no wall at next position, then add (still need to check if there's a crate in next pos)
						//{
						CrateIsAtPosition.setData(new Position(x-1,y));
						player1IsAtPosition.setData(new Position(x-2,y));
						
						updateLists(toGenerate,toRemove,CrateIsAtPosition,player1IsAtPosition);
						
						act.setPreconditions(new AndPredicate<Position>(toGenerate));//push crate to up
						possibleActions.add(act);
						//}
						
						//TODO: if no wall at next position, then add (still need to check if there's a crate in next pos)
						//{
						CrateIsAtPosition.setData(new Position(x,y-1));
						player1IsAtPosition.setData(new Position(x,y-2));
						
						updateLists(toGenerate,toRemove,CrateIsAtPosition,player1IsAtPosition);
						
						act.setPreconditions(new AndPredicate<Position>(toGenerate));//push crate to up
						possibleActions.add(act);
						//}
						
						//TODO: if no wall at next position, then add (still need to check if there's a crate in next pos)	
						//{
						CrateIsAtPosition.setData(new Position(x+1,y));
						player1IsAtPosition.setData(new Position(x+2,y));
						
						updateLists(toGenerate,toRemove,CrateIsAtPosition,player1IsAtPosition);
						
						act.setPreconditions(new AndPredicate<Position>(toGenerate));//push crate to up
						possibleActions.add(act);
						//}
						
						//TODO: if no wall at next position, then add (still need to check if there's a crate in next pos)		
						//{
						CrateIsAtPosition.setData(new Position(x,y+1));
						player1IsAtPosition.setData(new Position(x,y+2));
						
						updateLists(toGenerate,toRemove,CrateIsAtPosition,player1IsAtPosition);
						
						act.setPreconditions(new AndPredicate<Position>(toGenerate));//push crate to up
						possibleActions.add(act);
						//}
						
						return possibleActions;
					}
					if(top.getName().startsWith("No Crate"))// if location needs to be free
					{
						//if there's already no crate in the location then generate a new action with no preconditions
						
						//else if there's a crate in the location, generate all the ways to remove it, AKA crate to the top, bottom, right, left of this position
						
					}
					
					//TODO: if there's already a player in the position, activate Searcher (from searchLib)
					if(top.getName().startsWith("Player1")){
						
					}
					return null;
				}
			};	
			return plannable;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	@SafeVarargs
	private static void updateLists(List<Predicate<Position>> toGenerate,List<Predicate<Position>> toRemove,Predicate<Position>...predicates )
	{
		toGenerate.removeAll(toRemove);
		toRemove=new ArrayList<>();
		for (Predicate<Position> predicate : predicates) {
			toGenerate.add(predicate);
			toRemove.add(predicate);
		}
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
					kb.add(new SimplePredicate<Position>("Non_Solid", new Position(i,j)));
					break;
				case ('A'):
					kb.add(new SimplePredicate<Position>("Player1", new Position(i,j)));
					break;
				case ('@'):
					kb.add(new SimplePredicate<Position>("Crate #" + (crateCount++), new Position(i,j)));
					break;
				case ('o'):
					kb.add(new SimplePredicate<Position>("Goal #" + (goalCount++), new Position(i,j)));
					kb.add(new SimplePredicate<Position>("Non_Solid", new Position(i,j)));
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
