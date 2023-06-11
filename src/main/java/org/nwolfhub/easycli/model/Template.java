package org.nwolfhub.easycli.model;

public class Template {
    public String name;
    public String prefix;
    public String postfix;
    public Border border;

    public Template() {}

    public Template(String name, String prefix, String postfix) {
        this.name = name;
        this.prefix = prefix;
        this.postfix = postfix;
    }

    public String getName() {
        return name;
    }

    public Template setName(String name) {
        this.name = name;
        return this;
    }

    public String getPrefix() {
        return prefix;
    }

    public Template setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public String getPostfix() {
        return postfix;
    }

    public Template setPostfix(String postfix) {
        this.postfix = postfix;
        return this;
    }

    public Border getBorder() {
        return border;
    }

    public Template setBorder(Border border) {
        this.border = border;
        return this;
    }

    public String formatText(String text) {
        return border==null?(prefix + text + postfix):border.applyBorder(prefix + text + postfix);
    }

}
