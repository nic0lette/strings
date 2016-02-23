package org.hitori.strings.resources;

/**
 * Class for representing a simple string resource of the form <string name="name">value</string>
 */
public class SimpleString extends StringResource {
    public final String value;

    public SimpleString(final String name, final String value) {
        super(name, Type.Simple);
        this.value = value;
    }
}
