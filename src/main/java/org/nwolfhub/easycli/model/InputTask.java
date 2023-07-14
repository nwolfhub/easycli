package org.nwolfhub.easycli.model;

import org.nwolfhub.easycli.EasyCLI;

public abstract interface InputTask {


    /**
     * Things to be done after command is recognized
     * @param cli EasyCLI instance. Will be set automatically
     * @param params text after space
     */
    void act(EasyCLI cli, String... params);
}
