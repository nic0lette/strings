package org.hitori.strings.resources;

import java.util.Collections;
import java.util.List;

/**
 * Class for representing a string array
 */
public class StringsArray extends StringResource {
    public final List<String> values;

    public StringsArray(final String name, final List<String> values) {
        super(name, Type.Array);
        this.values = Collections.unmodifiableList(values);
    }
}
