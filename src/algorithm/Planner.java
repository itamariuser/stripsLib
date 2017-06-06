package algorithm;

import java.util.List;

public interface Planner<T> {
	List<Action<T>> plan(Plannable<T> plannable);
	
}
