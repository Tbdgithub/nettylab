package com.saturn.lab.jcip.SharingObjects;

import java.util.HashSet;
import java.util.Set;

class Secrets {

    public static Set<Secret> knownSecrets;

    public void initialize() {
        knownSecrets = new HashSet<Secret>();
    }
}


class Secret {
}