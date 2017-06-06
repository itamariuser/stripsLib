package forSokoban;

import algorithm.Predicate;

public class SokPredicate<T> extends Predicate<T>{//SokPredicate extends Predicate<GameData>
	
	public SokPredicate(String name) {
		super(name);
	}


	
	
	@Override
	public boolean contradicts(Predicate<T> other) { //TODO: Change to fit sokoban
		return (super.contradicts(other) || false); 
	}
}
