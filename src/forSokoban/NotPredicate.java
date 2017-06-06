package forSokoban;

import algorithm.Predicate;

public class NotPredicate<T> extends Predicate<T> {

	Predicate<T> inner;

	public NotPredicate(String name, Predicate<T> inner) {
		super(name);
		this.inner = inner;
	}
	
	@Override
	public boolean satisfies(Predicate<T> other) {
		return !(inner.satisfies(other));
	}

}
