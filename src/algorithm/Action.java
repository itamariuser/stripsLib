package algorithm;

public class Action<T> extends Predicate<T> {
	public Action(String name) {
		super(name);
	}

	protected ComplexPredicate<T> preconditions;
	
	protected ComplexPredicate<T> effects;

	public ComplexPredicate<T> getPreconditions() {
		return preconditions;
	}

	public void setPreconditions(ComplexPredicate<T> preconditions) {
		this.preconditions = preconditions;
	}

	public ComplexPredicate<T> getEffects() {
		return effects;
	}

	public void setEffects(ComplexPredicate<T> effects) {
		this.effects = effects;
	}

	
	
}
