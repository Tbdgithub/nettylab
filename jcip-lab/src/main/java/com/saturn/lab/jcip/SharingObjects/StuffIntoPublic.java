package com.saturn.lab.jcip.SharingObjects;

public class StuffIntoPublic {
    public  static Holder holder;

    public void initialize() {
        holder = new Holder(42);
    }

    public static void main(String [] args)
    {
        StuffIntoPublic stuffIntoPublic=new StuffIntoPublic();
        stuffIntoPublic.initialize();

        stuffIntoPublic.holder.assertSanity();




    }
}