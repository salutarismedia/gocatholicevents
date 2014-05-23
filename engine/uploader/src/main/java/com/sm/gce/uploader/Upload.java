package com.sm.gce.uploader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;

import com.sm.gce.util.LoggingObject;

public class Upload extends LoggingObject {

    private static final String SQL_FILE = "/tmp/gce.sql";
    Properties properties = new Properties();

    public Upload() {
        loadPropertiesFile();
    }

    private void loadPropertiesFile() {
        try {
            String filePath = this.getClass().getClassLoader()
                    .getResource("uploader.properties").getFile();
            File file = new File(filePath);
            properties.load(new FileInputStream(file));
        } catch (Exception e) {
            throw new RuntimeException("Could not load properties file");
        }
    }

    public void uploadDatabaseToServer() throws Exception {
        exportLocalDb();
        uploadExportedDb();
    }

    private void uploadExportedDb() throws Exception {
        logger.info("uploaded database to " + properties.getProperty("db.host"));
        CommandLine cmd = new CommandLine("mysql");
        cmd.addArgument("--host=" + properties.getProperty("db.host"));
        cmd.addArgument("--port=" + properties.getProperty("db.port"));
        cmd.addArgument("--user=" + properties.getProperty("db.user"));
        cmd.addArgument("--password=" + properties.getProperty("db.password"));
        cmd.addArgument("--ssl");
        cmd.addArgument(properties.getProperty("db.name"));
        FileInputStream fileInputStream = new FileInputStream(SQL_FILE);
        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(null, null,
                fileInputStream);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(pumpStreamHandler);
        int exitValue = executor.execute(cmd);
        if (exitValue != 0) {
            throw new RuntimeException("Could not export local database");
        }
    }

    /**
     * performs a mysqldump on the local database after the engine populates it
     * 
     * @throws IOException
     * @throws ExecuteException
     */
    private void exportLocalDb() throws Exception {
        logger.info("dumping database to " + SQL_FILE);
        CommandLine cmd = new CommandLine("mysqldump");
        cmd.addArgument("--user=engine");
        // this is on a secure server, password not sensitive
        cmd.addArgument("--password=engine");
        cmd.addArgument("ce");
        FileOutputStream fileOutputStream = new FileOutputStream(SQL_FILE);
        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(
                fileOutputStream);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(pumpStreamHandler);
        int exitValue = executor.execute(cmd);
        if (exitValue != 0) {
            throw new RuntimeException("Could not export local database");
        }
    }

}
