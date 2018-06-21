package com.saturn.lab.jcip.AvoidLivenessHazards.CooperatingDeadlock;

import net.jcip.annotations.GuardedBy;

import java.util.HashSet;
import java.util.Set;

public class Dispatcher {
    @GuardedBy("this")
    private final Set<Taxi> taxis;
    @GuardedBy("this")
    private final Set<Taxi> availableTaxis;

    public Dispatcher() {
        taxis = new HashSet<Taxi>();
        availableTaxis = new HashSet<Taxi>();
    }

    public synchronized void notifyAvailable(Taxi taxi) {
        availableTaxis.add(taxi);
    }

    //先lock dispater,再lock taxi
    public synchronized Image getImage() {
        Image image = new Image();
        for (Taxi t : taxis)
            image.drawMarker(t.getLocation());
        return image;
    }
}