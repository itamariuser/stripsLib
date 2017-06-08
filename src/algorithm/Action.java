package algorithm;

public class Action<T> extends Predicate<T> {
	

	protected AndPredicate<T> preconditions;
	
	protected AndPredicate<T> effects;

	
	public AndPredicate<T> getPreconditions() {
		return preconditions;
	}


	public void setPreconditions(AndPredicate<T> preconditions) {
		this.preconditions = preconditions;
	}


	public AndPredicate<T> getEffects() {
		return effects;
	}


	public void setEffects(AndPredicate<T> effects) {
		this.effects = effects;
	}


	public Action(String name) {
		super(name);
	}
	
	
}
