package com.sm.gce.common.test;

import java.io.File;

import com.sm.gce.util.LoggingObject;

public abstract class AbstractCeTest extends LoggingObject {

    public File loadFileFromClasspath(String fileName) {
        String filePath = this.getClass().getClassLoader()
                .getResource(fileName).getFile();
        File file = new File(filePath);
        return file;
    }
}
