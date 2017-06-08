package algorithm;

import java.util.List;

public interface Plannable<T> {
	//public boolean isSatisifed(Predicate<T> p);
	public Predicate<T> getGoal();
	public AndPredicate<T> getKnowledgebase();
	public List<Action<T>> getSatisfyingActions(Predicate<T> top);
	public Action<T> getSatisfyingAction(Predicate<T> top);
}
