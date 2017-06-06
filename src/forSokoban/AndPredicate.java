package forSokoban;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import algorithm.ComplexPredicate;
import algorithm.Predicate;
import algorithm.SimplePredicate;

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
