package com.sm.gce.adapter.runner;

public class App {

    // run with gradle run -Parg=<path to adapter>
    // gradle run -Parg=../adapters/north-america/us/va/chantilly/st-timothy/
    public static void main(String[] args) throws Exception {
        if (!validInput(args)) {
            printUsage();
        } else {
            AdapterRunner adapterRunner = new AdapterRunner();
            adapterRunner.runAdapter(args[0]);
        }
    }

    private static boolean validInput(String[] args) {
        return args.length == 1;
    }

    private static void printUsage() {
        StringBuilder builder = new StringBuilder();
        builder.append("Adapter Runner will run the unit tests and then retrieve ");
        builder.append("the data before uploading it to the database for a given ");
        builder.append("adapter:  Usage adapter-runner <path-to-adapter-project>");
        System.out.println(builder);
    }

}
