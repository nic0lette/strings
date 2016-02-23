package org.hitori.strings;

import org.hitori.strings.resources.StringResources;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        new StringResources(new File("/Users/nicole/strings.xml"));
    }
}
