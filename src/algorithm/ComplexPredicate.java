package algorithm;

import java.util.Collection;
import java.util.HashSet;

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

	public void update(ComplexPredicate<T> effects) {//TODO: complete from 0000002 video eli
		//effects.getComponents().forEach((Predicate<T> p)->components.removeIf(p.contradicts(other)));

	}
	
	public void add(Predicate<T> p)
	{
		if(this.components==null)
		{
			components=new HashSet<Predicate<T>>();
		}
		this.components.add(p);
		//this.updateDescription();//TODO: complete from eli 0000002
	}
}
