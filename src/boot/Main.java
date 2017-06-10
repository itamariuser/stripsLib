package boot;

import algorithm.Plan;
import algorithm.Plannable;
import algorithm.Position;
import algorithm.Strips;
import forSokoban.PredicateGenerator;

public class Main {

	public static void main(String[] args) {
		Plannable<Position> plannable=PredicateGenerator.readFile("LevelTest");
		System.out.println(plannable.getGoal());
		Strips<Position> strips=new Strips<>();
		Plan<Position > p=strips.plan(plannable);
		System.out.println(p);
	}

}
