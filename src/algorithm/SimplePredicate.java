package algorithm;

public class SimplePredicate<T> extends Predicate<T> {

	public SimplePredicate(String name,T data) {
		super(name,data);
	}

	public SimplePredicate(String name) {
		super(name);
	}
	
	
	@Override
	public boolean satisfies(Predicate<T> other) {
		return this.equals(other);
	}

}
