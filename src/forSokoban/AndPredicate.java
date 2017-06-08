package forSokoban;

import java.util.Collection;

import algorithm.ComplexPredicate;
import algorithm.Predicate;

public class AndPredicate<T> extends ComplexPredicate<T> {
	
	
	public AndPredicate(String name, Collection<Predicate<T>> components) {
		super(name, components);
	}

	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		for (Predicate<T> predicate : components) {
			sb.append("\n"+predicate.toString()+"\n");
		}
		return "** 'And' PREDICATE, Name: "+this.name+", preds:"+sb.toString();
	}
	
	@Override
	public boolean satisfies(Predicate<T> other) {
		for(Predicate<T> pr:this.components)
		{
			if(pr.satisfies(other))
			{
				return true;
			}
		}
		return false;
	}
	
	public void update(AndPredicate<T> effects) {//TODO: complete from 0000002 video eli
		effects.getComponents().forEach((Predicate<T> p)->components.removeIf((Predicate<T> pr)->p.contradicts(pr)));
		components.addAll(effects.getComponents());
		
	}

	
	public boolean satisfies(AndPredicate<T> complex)
	{
		for (Predicate<T> predicate : complex.getComponents()) {
			if(!satisfies(predicate))
			{
				return false;
			}
		}
		return true;
	}
}
