package de.unitygaming.bukkit.vsign.invoker;

public abstract interface ReturningInvoker<A, B> {

	public abstract B invoke(A object);
	
}