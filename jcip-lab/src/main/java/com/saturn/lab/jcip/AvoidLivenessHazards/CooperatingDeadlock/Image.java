package com.saturn.lab.jcip.AvoidLivenessHazards.CooperatingDeadlock;


import com.saturn.lab.jcip.ComposingObjects.Point;

public class Image {
    public void drawMarker(Point p) {

        System.out.println("point:(" + p.x + "," + p.y + ")");
    }
}