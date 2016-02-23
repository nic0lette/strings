package org.hitori.strings.resources;

/**
 * Base class to represent a string resource
 */
public abstract class StringResource {
    public enum Type {
        None,
        Simple,
        Plural,
        Array
    }

    public final String name;
    public final Type type;

    protected StringResource(final String name, final Type type) {
        this.name = name;
        this.type = type;
    }
}
