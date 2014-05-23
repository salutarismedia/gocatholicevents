package com.sm.gce.uploader;

public class App {
    // run with gradle run
    public static void main(String[] args) throws Exception {
        Upload uploader = new Upload();
        uploader.uploadDatabaseToServer();
    }
}
