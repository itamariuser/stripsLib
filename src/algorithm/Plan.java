package algorithm;

import java.util.List;

public class Plan<T> {
	List<Action<T>> actions;

	public List<Action<T>> getActions() {
		return actions;
	}

	public void setActions(List<Action<T>> actions) {
		this.actions = actions;
	}
	
	public void add(Action<T> action)
	{
		actions.add(action);
	}
	
	public void remove(Action<T> action)
	{
		actions.remove(action);
	}

	public Plan(List<Action<T>> actions) {
		super();
		this.actions = actions;
	}
	
	
}
