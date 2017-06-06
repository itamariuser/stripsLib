package algorithm;

public abstract class Predicate<T> {

	T data;
	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean satisfies(Predicate<T> other) {
		return this.getData().equals(other.getData());
	}

	public boolean contradicts(Predicate<T> other)// override to fit sokoban / block world
	{
		return false;
	}

	public Predicate(String name) {
		this.name = name;
	}

	public Predicate(String name, T data) {
		this.name = name;
		this.data = data;
	}

	@Override
	public int hashCode() {
		return data.hashCode();
	}

	@Override
	public String toString() {
		return "Name: "+this.name+"\nData: "+data.toString();
	}
}
