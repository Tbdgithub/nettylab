package com.saturn.lab.jcip.SharingObjects;

public class ThisEscape {

    /**
     * bad
     * 构造还没完，就register ,发布了
     * @param source
     */
    public ThisEscape(EventSource source) {

        source.registerListener(new EventListener() {
            public void onEvent(Event e) {
                ThisEscape.this.doSomething(e);
            }
        });
    }

    void doSomething(Event e) {

        System.out.println("e:" + "dosomething");
    }


    interface EventSource {
        void registerListener(EventListener e);
    }

    interface EventListener {
        void onEvent(Event e);
    }

    interface Event {
    }

    public static void main(String[] args) {

        EventSource eventSource = new EventSource() {
            @Override
            public void registerListener(EventListener e) {

                System.out.println("registerListener");

                e.onEvent(new Event() {
                });
            }
        };

        ThisEscape thisEscape = new ThisEscape(eventSource);

        //eventSource.


    }


}
