package algorithm;

import java.util.Collection;

public abstract class ComplexPredicate<T> extends Predicate<T> {
	protected Collection<Predicate<T>> components;
	
	public ComplexPredicate(String name, Collection<Predicate<T>> components) {
		super(name);
		this.components=components;
	}
	
//	@Override
//	public boolean contradicts(Predicate<T> other) {
//		for (Predicate<T> predicate : components) {
//			if(predicate.contradicts(other))
//				return true;
//		}
//		return false;
//	}
//	@Override
//	public boolean satisfies(Predicate<T> other) {
//		for (Predicate<T> predicate : components) {
//			if(!predicate.satisfies(other))
//				return false;
//		}
//		return true;
//	}
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		for (Predicate<T> predicate : components) {
			sb.append("\n"+predicate.toString()+"\n");
		}
		return "** COMPLEX PREDICATE, Name: "+this.name+", preds:"+sb.toString();
	}
}
