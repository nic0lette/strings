package org.hitori.strings.resources;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for representing a plurals/quantity string
 */
public class PluralString extends StringResource {
    public enum Qty {
        zero,
        one,
        two,
        few,
        many,
        other
    }

    private final Map<Qty, String> qtyStringMap = new HashMap<>();

    protected PluralString(final String name, final Map<String,String> map) {
        super(name, Type.Plural);

        qtyStringMap.put(Qty.zero, map.get(Qty.zero.name()));
        qtyStringMap.put(Qty.one, map.get(Qty.one.name()));
        qtyStringMap.put(Qty.two, map.get(Qty.two.name()));
        qtyStringMap.put(Qty.few, map.get(Qty.few.name()));
        qtyStringMap.put(Qty.many, map.get(Qty.many.name()));
        qtyStringMap.put(Qty.other, map.get(Qty.other.name()));
    }

    public String getQtyString(final Qty qty) {
        return qtyStringMap.get(qty);
    }
}
