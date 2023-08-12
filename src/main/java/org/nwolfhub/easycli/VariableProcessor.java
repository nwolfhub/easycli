package org.nwolfhub.easycli;

import org.nwolfhub.easycli.model.Level;
import org.nwolfhub.easycli.model.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

public class VariableProcessor {
    private final List<Variable> variables = new ArrayList<>();
    public VariableProcessor() {}

    /**
     * Adds a new variable to the list of variables.
     * @param v the variable to be added
     */
    public void addVariable(Variable v) {
        variables.add(v);
    }
    /**
     * Returns a new ArrayList containing all variables in the list.
     *
     * @return a new ArrayList of variables
     */
    public List<Variable> getVariables() {
        return new ArrayList<>(variables);
    }

    /**
     * Removes the variable at the specified index from the list of variables.
     *
     * @param index the index of the variable to be removed
     */
    public void removeVariable(int index) {
        variables.remove(index);
    }

    /**
     * Clears all variables from the list.
     */
    public void clearAll() {
        variables.clear();
    }

    /**
     * Applies all variables to provided text
     * @param text original text
     * @return updated text
     */
    public String processText(String text) {
        AtomicReference<String> modified = new AtomicReference<>(text);
        variables.forEach(variable -> modified.set(variable.processText(modified.get())));
        return modified.get();
    }
}
