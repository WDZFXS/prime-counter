package com.wdzfxs.primecounter.generator;

import java.io.Serializable;

public class PrimeIntegers implements Serializable {

    private static final long serialVersionUID = 1L;
    private boolean[] integers;

    public PrimeIntegers(int arraySize) {
        integers = new boolean[arraySize];
    }

    public void add(int integer) {
        integers[integer] = true;
    }

    public boolean[] getIntegers() {
        return integers;
    }

    public void setIntegers(boolean[] integers) {
        this.integers = integers;
    }
}