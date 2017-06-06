package forSokoban;

import algorithm.Predicate;

public class NotPredicate<T> extends Predicate<T> {

	Predicate<T> other;

	public NotPredicate(String name, Predicate<T> other) {
		super(name);
		this.other = other;
	}
	
	

}
