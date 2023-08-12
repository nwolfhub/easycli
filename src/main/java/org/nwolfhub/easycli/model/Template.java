package org.nwolfhub.easycli.model;

import org.nwolfhub.easycli.VariableProcessor;

import java.util.List;
import java.util.Locale;

public class Template {
    public String name;
    public String prefix;
    public String postfix;
    public Border border;

    private final VariableProcessor processor = new VariableProcessor();

    public Template() {}

    public Template(String name, String prefix, String postfix) {
        this.name = name;
        this.prefix = prefix;
        this.postfix = postfix;
    }

    public String getName() {
        return name;
    }

    /**
     * sets a name for the template that can be used later to use it in EasyCLI
     * @param name - name of a template
     */
    public Template setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Retrieves the prefix value.
     *
     * @return the prefix value as a String.
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Sets the prefix value. Prefix will be added before the text
     * @param prefix the prefix value to be set as a String.
     */

    public Template setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    /**
     * Retrieves the postfix value.
     *
     * @return the postfix value as a String.
     */
    public String getPostfix() {
        return postfix;
    }
    /**
     * Sets the postfix value. Postfix will be added after the text
     *
     * @param postfix the postfix value to be set as a String.
     */
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

    /**
     * Applies all template rules to the text
     * @param text - unedited text
     * @return modified text
     */
    public String formatText(String text, Level level) {
        text = processor.processText(prefix + text.replace("{logLevel}", level.toString().toUpperCase(Locale.ROOT)) + postfix);
        return border==null?(text):border.applyBorder(text);
    }

    /**
     * Adds a new variable to the list of variables processed by the variable processor.
     *
     * @param v the variable to be added
     */
    public Template addVariable(Variable v) {
        processor.addVariable(v);
        return this;
    }

    /**
     * Removes the variable at the specified index from the list of variables processed by the variable processor.
     *
     * @param index the index of the variable to be removed
     */
    public Template removeVariable(int index) {
        processor.removeVariable(index);
        return this;
    }

    /**
     * Clears all the variables in the list of variables processed by the variable processor.
     */
    public Template clearVariables() {
        processor.clearAll();
        return this;
    }
    /**
     * Returns a list of all variables processed by the variable processor.
     *
     * @return a list of Variable objects
     */
    public List<Variable> getVariables() {
        return processor.getVariables();
    }
}
