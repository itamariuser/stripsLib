package algorithm;

import java.util.ArrayList;
import java.util.List;

public class AndPredicate<T> extends ComplexPredicate<T> {
	public AndPredicate(String name, List<Predicate<T>> components) {
		super(name, components);
	}
	@SafeVarargs
	public AndPredicate(Predicate<T>... preds) {
		super("Default AndPredicate",new ArrayList<Predicate<T>>());
		for (Predicate<T> predicate : preds) {
			this.add(predicate);
		}
	}
	public AndPredicate(List<Predicate<T>> preds) {
		super("Default AndPredicate",new ArrayList<Predicate<T>>());
		for (Predicate<T> predicate : preds) {
			this.add(predicate);
		}
	}
	
	private void splitAndPreds(AndPredicate<T> pred)
	{
		for (Predicate<T> predicate : pred.getComponents()) {
			if(predicate instanceof AndPredicate)
			{
				splitAndPreds((AndPredicate<T>)predicate);
			}
			else //=>(predicate instanceof SimplePredicate)
			{
				components.add(predicate);
			}
		}
		components.remove(pred);
	}
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		for (Predicate<T> predicate : components) {
			sb.append("\n"+predicate.toString()+"\n");
		}
		return "** 'And' PREDICATE, Name: "+this.name+", preds:"+sb.toString();
	}
	
	public void update(AndPredicate<T> effects) {
		effects.getComponents().forEach((Predicate<T> p)->components.removeIf((Predicate<T> pr)->plannable.contradicts(p,pr)));
		components.addAll(effects.getComponents());
		splitAndPreds(effects);
	}
}
