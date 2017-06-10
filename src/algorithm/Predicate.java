package algorithm;

public abstract class Predicate<T> {
	
	T data;
	protected String name;
	Plannable<T> p;

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

	public boolean satisfies(Predicate<T> other) {// (A => B)
		return p.satisfies(this, other);
	}

	public boolean contradicts(Predicate<T> other)// (A => ~B)
	{
		return p.contradicts(this, other);
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
		return "Type: "+this.getClass().getSimpleName()+"\nName: "+this.name+"\nData: "+data.toString();
	}
}
