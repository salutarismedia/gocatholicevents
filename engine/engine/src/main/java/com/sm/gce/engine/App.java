package com.sm.gce.engine;

public class App {

    // run with gradle run
    public static void main(String[] args) throws Exception {
        Engine engine = new Engine();
        engine.processAdapters();
    }

}
