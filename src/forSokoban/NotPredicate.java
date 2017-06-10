package forSokoban;

import algorithm.Predicate;

public class NotPredicate<T> extends Predicate<T> {

	Predicate<T> inner;

	public NotPredicate(Predicate<T> inner) {
		super("No "+inner.getName());
		this.setData(inner.getData());
		this.inner = inner;
	}
	
	@Override
	public boolean satisfies(Predicate<T> other) {
		boolean var=inner.satisfies(other);
		return !(var);
	}
	@Override
	public String toString() {
		return super.toString();
	}

}
