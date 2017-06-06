package algorithm;

import java.util.Collection;

public class Action<T> {
	Collection<Predicate<T>> preconditions;
	
	Collection<Predicate<T>> effects;

	public Action() {
		
	}
}
