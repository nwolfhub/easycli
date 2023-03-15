package org.nwolfhub.easycli;

public abstract class InputTask {


    public InputTask() {

    }


    /**
     * Things to be done after command is recognized
     * @param cli EasyCLI instance. Will be set automatically
     * @param params text after space
     */
   public abstract void act(EasyCLI cli, String... params);
}
