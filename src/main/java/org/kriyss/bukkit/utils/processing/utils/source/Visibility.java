package org.kriyss.bukkit.utils.processing.utils.source;

/**
 * Created on 04/09/2014.
 */
public enum Visibility{
    PUBLIC("public"),
    PACKAGE(""),
    PRIVATE("private"),
    PROTECTED("protected");

    public String get(){
        return value;
    }
    private String value;

    private Visibility(String value) {
        this.value = value;
    }
}
