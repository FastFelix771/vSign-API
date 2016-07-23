package de.unitygaming.bukkit.vsign.invoker;

/**
 * @description This is a replacement for Java 8's Consumer to keep the plugin backwards-compatible.
 * @author FastFelix771
 */
public interface Invoker<T> {

    public void invoke(T parameter);

}