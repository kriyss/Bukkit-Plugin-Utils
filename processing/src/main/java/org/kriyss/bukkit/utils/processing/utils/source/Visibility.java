package org.kriyss.bukkit.utils.processing.utils.source;

public enum Visibility{
    PUBLIC("public"),
    PACKAGE(""),
    PRIVATE("private"),
    PROTECTED("protected");

    public String get(){
        return value;
    }
    private final String value;

    private Visibility(String value) {
        this.value = value;
    }
}
