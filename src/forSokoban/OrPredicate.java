package forSokoban;

import java.util.Collection;

import algorithm.ComplexPredicate;
import algorithm.Predicate;

public class OrPredicate<T> extends ComplexPredicate<T>{

	public OrPredicate(String name, Collection<Predicate<T>> components) {
		super(name, components);
	}

	@Override
	public boolean contradicts(Predicate<T> other) {
		boolean contradicts=true;
		for (Predicate<T> predicate : this.components) {
			if(!predicate.contradicts(other))
			{
				contradicts=false;
			}
		}
		return contradicts;
	}
	
	@Override
	public boolean satisfies(Predicate<T> other) {
		boolean contradicts=false;
		for (Predicate<T> predicate : this.components) {
			if(predicate.satisfies(other))
			{
				contradicts=true;
			}
		}
		return contradicts;
	}
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		for (Predicate<T> predicate : components) {
			sb.append("\n"+predicate.toString()+"\n");
		}
		return "** 'Or' PREDICATE, Name: "+this.name+", preds:"+sb.toString();
	}
}
