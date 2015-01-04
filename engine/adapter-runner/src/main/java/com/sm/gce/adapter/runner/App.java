package com.sm.gce.adapter.runner;

public class App {

    // run with gradle run -Parg=<path to adapter>
    // gradle run -Parg=../adapters/north-america/us/va/chantilly/st-timothy/
    // gradle run -Parg=../adapters/north-america/us/va/fairfax/st-leo-the-great
    public static void main(String[] args) {
        if (!validInput(args)) {
            printUsage();
            System.exit(1);
        } else {
            AdapterRunner adapterRunner = new AdapterRunner();
            try {
                adapterRunner.runAdapter(args[0]);
                System.exit(0);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
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
