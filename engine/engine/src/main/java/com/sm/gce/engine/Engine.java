package com.sm.gce.engine;

import java.io.File;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.joda.time.LocalDateTime;

import com.sm.gce.common.model.Adapter;
import com.sm.gce.database.AdapterDao;
import com.sm.gce.util.LoggingObject;

public class Engine extends LoggingObject {

    private AdapterDao adapterDao = new AdapterDao();

    public void processAdapters() throws Exception {
        List<Adapter> adapters = adapterDao.getEnabledAdapters();
        for (Adapter adapter : adapters) {
            runAdapter(adapter);
        }
        uploadData();
    }

    private void uploadData() throws Exception {
        CommandLine cmd = new CommandLine("gradle");
        cmd.addArgument("run");
        cmd.addArgument("-Parg=../uploader/");
        DefaultExecutor executor = new DefaultExecutor();
        executor.setWorkingDirectory(new File("../uploader"));
        int exitValue = executor.execute(cmd);
        if (exitValue != 1) {
            logger.info("engine run complete");
        } else {
            throw new RuntimeException("Could not upload data");
        }
    }

    private void runAdapter(Adapter adapter) throws Exception {
        if (adapter != null) {
            logger.info("Running adapter named " + adapter.getName());
            CommandLine cmd = new CommandLine("gradle");
            cmd.addArgument("run");
            cmd.addArgument("-Parg=" + adapter.getPath());
            DefaultExecutor executor = new DefaultExecutor();
            executor.setWorkingDirectory(new File("../adapter-runner"));
            int exitValue = executor.execute(cmd);
            if (exitValue != 1) {
                onAdapterSuccess(adapter);
            } else {
                onAdapterFailure(adapter);
            }
        }
    }

    private void onAdapterFailure(Adapter adapter) {
        if (adapter != null) {
            logger.error("Could not update " + adapter.getName());
        }
    }

    private void onAdapterSuccess(Adapter adapter) {
        if (adapter != null) {
            adapter.setLastRunOn(new LocalDateTime());
            adapterDao.save(adapter);
            logger.info("Successfully updated " + adapter.getName());
        }
    }

}
