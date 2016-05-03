package com.github.vchimishuk.newground.resource.result;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ValueResult<T> {
    private T value;

    public ValueResult() {

    }

    public ValueResult(T value) {
        this.value = value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
