package cz.cuni.amis.clear2d.utils;

public abstract class Lazy<T> {

	private T value;

	protected abstract T create();
	
	public T get() {
		if (value == null) value = create();
		return value;
	}
	
	public boolean isNull() {
		return value == null;
	}
	
}
