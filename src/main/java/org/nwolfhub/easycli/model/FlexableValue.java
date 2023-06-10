package org.nwolfhub.easycli.model;


public abstract class FlexableValue {
    public Object meta; //meta object can be used not to recreate it many times (e.g. random or data)
    public FlexableValue() {
    }

    public FlexableValue(Object meta) {
        this.meta = meta;
    }

    public Object getMeta() {
        return meta;
    }

    public FlexableValue setMeta(Object meta) {
        this.meta = meta;
        return this;
    }

    /**
     * This method is called each time
     * @return text that will be used in replace
     */
    public abstract String call();
}
