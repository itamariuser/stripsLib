package algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import forSokoban.AndPredicate;

public class Strips<T> implements Planner<T> {
	Plannable<T> plannable;
	@Override
	public List<Action<T>> plan(Plannable<T> plannable) {
		LinkedList<Action<T>> actionsToDo=new LinkedList<Action<T>>();
		this.plannable=plannable;
		Stack<Predicate<T>> stack = new Stack<>();
		stack.push(plannable.getGoal());
		while(!stack.isEmpty())
		{
			Predicate<T> top=stack.peek();
			if(!(top instanceof Action))
			{
				if(!plannable.getKnowledgebase().satisfies(top))
				{
					if(top instanceof AndPredicate)//if complex then unpack components to stack
					{
						//TODO: add a way to decide order of insertion (comparator)
						AndPredicate<T> andPred=(AndPredicate<T>)top;
						for (Predicate<T> predicate : andPred.getComponents()) {
							stack.push(predicate);
						}
					}
					else if(top instanceof SimplePredicate)//
					{
						stack.pop();
						Action<T> action=plannable.getSatisfyingAction(top);//Change to a set of actions
						stack.push(action.getPreconditions());
						stack.push(action);
					}
				}
				else//satisfied
				{
					stack.pop();
				}
			}
			else//top is an action
			{
				stack.pop();//remove from top
				Action<T> a=(Action<T>) top;
				plannable.getKnowledgebase().update(a.getEffects());
				actionsToDo.add(a);
			}
		}
		return actionsToDo;
	}


}
