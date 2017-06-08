package boot;

import algorithm.Plannable;
import algorithm.Position;
import algorithm.Strips;
import forSokoban.PredicateGenerator;

public class Main {

	public static void main(String[] args) {
	/*	SimplePredicate<Integer> p1=new SimplePredicate<Integer>("BoxAt",1);
		SimplePredicate<Integer> p2=new SimplePredicate<Integer>("BoxAt",2);
		SimplePredicate<Integer> pa1=new SimplePredicate<Integer>("Box(in list)",54);
		SimplePredicate<Integer> pa2=new SimplePredicate<Integer>("Box(in list)",32);
		ArrayList<Predicate<Integer>> pa=new ArrayList<>();
		pa.add(pa1);
		AndPredicate<Integer> pArray=new AndPredicate<Integer>("Complex",pa);*/
		Plannable<Position> plannable=PredicateGenerator.readFile("LevelTest");
		System.out.println(plannable.getGoal());
		Strips<Position> strips=new Strips<>();
		strips.plan(plannable);
	}

}
