package boot;

import java.util.ArrayList;

import algorithm.ComplexPredicate;
import algorithm.Predicate;
import algorithm.SimplePredicate;
import forSokoban.AndPredicate;

public class Main {

	public static void main(String[] args) {
		SimplePredicate<Integer> p1=new SimplePredicate<Integer>("BoxAt",1);
		SimplePredicate<Integer> p2=new SimplePredicate<Integer>("BoxAt",2);
		
		ArrayList<Predicate<Integer>> preds=new ArrayList<>();
		preds.add(p1);
		preds.add(p2);
		ComplexPredicate<Integer> c1=new AndPredicate<Integer>("AllBoxesAtPositions", preds);
		System.out.println(c1);

	}

}
