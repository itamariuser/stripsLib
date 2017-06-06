package algorithm;

import java.util.Set;

public interface Plannable<T> {
	public boolean isSatisifed(Predicate<T> p);
	ComplexPredicate<T> getGoal();
	ComplexPredicate<T> getKnowledgebase();
	public Set<Action<T>> getSatisfyingActions(Predicate<T> top);
	public Action<T> getSatisfyingAction(Predicate<T> top);
}
