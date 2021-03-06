package com.saturn.lab.jcip.AvoidLivenessHazards.CooperatingDeadlock;

import com.saturn.lab.jcip.ComposingObjects.Point;
import net.jcip.annotations.GuardedBy;

import java.util.HashSet;
import java.util.Set;


// Warning: deadlock-prone!
public class Taxi {
    @GuardedBy("this")
    private Point location, destination;
    private final Dispatcher dispatcher;

    public Taxi(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public synchronized Point getLocation() {
        return location;
    }

    //先lock taxi ,再lock dispacher , 在次序不同的情况下,容易造成死锁
    public synchronized void setLocation(Point location) {
        this.location = location;
        if (location.equals(destination))
            dispatcher.notifyAvailable(this);
    }

    public synchronized Point getDestination() {
        return destination;
    }

    public synchronized void setDestination(Point destination) {
        this.destination = destination;
    }
}



