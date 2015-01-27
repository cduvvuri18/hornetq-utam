package com.cduvvuri.hqutam.pool;

public abstract class AbstractConnectionProxy<T> {
	public abstract T create();
	public abstract boolean close(T t);
}
