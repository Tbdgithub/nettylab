package com.saturn.lab.jcip.SharingObjects;

class UnsafeStates {
    private String[] states = new String[]{
            "AK", "AL" /*...*/
    };

    public String[] getStates() {
        return states;
    }
}