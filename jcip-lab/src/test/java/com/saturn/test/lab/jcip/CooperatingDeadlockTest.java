package com.saturn.test.lab.jcip;


import com.saturn.lab.jcip.AvoidLivenessHazards.CooperatingDeadlock.Dispatcher;
import com.saturn.lab.jcip.AvoidLivenessHazards.CooperatingDeadlock.Taxi;
import org.junit.Test;

public class CooperatingDeadlockTest {

    @Test
    public void test1()
    {
        Dispatcher dispatcher =new Dispatcher();
        Taxi taxi=new Taxi(dispatcher) ;
        //dispatcher.notifyAvailable(taxi);

        dispatcher.getImage();

    }
}
