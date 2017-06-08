package algorithm;

import java.util.Set;

public interface Plannable<T> {
	public boolean isSatisifed(Predicate<T> p);
	Predicate<T> getGoal();
	AndPredicate<T> getKnowledgebase();
	public Set<Action<T>> getSatisfyingActions(Predicate<T> top);
	public Action<T> getSatisfyingAction(Predicate<T> top);
}
