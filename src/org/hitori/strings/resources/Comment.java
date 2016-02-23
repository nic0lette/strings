package org.hitori.strings.resources;

/**
 * Special package private "resource" for holding onto comments in the strings file
 */
/* package */ class Comment extends StringResource {
    public final String comment;

    /* package */ Comment(final String key, final String comment) {
        super(key, Type.None);
        this.comment = comment;
    }
}
