package algorithm;

import java.util.Collection;
import java.util.HashSet;

import forSokoban.AndPredicate;

public class ComplexPredicate<T> extends Predicate<T> {
	protected Collection<Predicate<T>> components;

	public ComplexPredicate(String name, Collection<Predicate<T>> components) {
		super(name);
		this.components = components;
	}

	public Collection<Predicate<T>> getComponents() {
		return components;
	}

	public void setComponents(Collection<Predicate<T>> components) {
		this.components = components;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Predicate<T> predicate : components) {
			sb.append("\n" + predicate.toString() + "\n");
		}
		return "** COMPLEX PREDICATE, Name: " + this.name + ", preds:" + sb.toString();
	}

	public void update(AndPredicate<T> effects) {
		effects.getComponents().forEach((Predicate<T> p)->components.removeIf((Predicate<T> pr)->p.contradicts(pr)));
		components.addAll(effects.getComponents());
		
	}
	
	public void add(Predicate<T> p)
	{
		if(this.components==null)
		{
			components=new HashSet<Predicate<T>>();
		}
		this.components.add(p);
		
	}
}
