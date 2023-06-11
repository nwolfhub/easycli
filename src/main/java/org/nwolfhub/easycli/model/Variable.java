package org.nwolfhub.easycli.model;

public class Variable {
    public String name;
    public FlexableValue value;

    public Variable() {}
    public Variable(String name) {
        this.name=name;
    }

    public Variable(String name, FlexableValue value) {
        this.name = name;
        this.value = value;
    }

    public String processText(String text) {
        return text.replace(name, value.call());
    }
}
