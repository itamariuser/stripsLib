package forSokoban;

import java.util.Collection;

import algorithm.ComplexPredicate;
import algorithm.Predicate;

public class AndPredicate<T> extends ComplexPredicate<T> {

	public AndPredicate(String name, Collection<Predicate<T>> components) {
		super(name, components);
	}
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		for (Predicate<T> predicate : components) {
			sb.append("\n"+predicate.toString()+"\n");
		}
		return "** 'And' PREDICATE, Name: "+this.name+", preds:"+sb.toString();
	}

}
