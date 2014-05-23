package com.sm.gce.adapter.runner;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.joda.time.LocalDateTime;

import com.sm.gce.common.model.Adapter;
import com.sm.gce.common.model.ChurchDetail;
import com.sm.gce.database.AdapterDao;
import com.sm.gce.util.Constants;
import com.sm.gce.util.LoggingObject;

public class AdapterRunner extends LoggingObject {

    private AdapterDao adapterDao = new AdapterDao();

    public void runAdapter(String adapterPath) throws Exception {
        if (adapterPath != null) {
            File file = new File(adapterPath);
            if (file.exists()) {
                Adapter adapter = adapterDao.findOneByPath(adapterPath);
                if (adapter != null) {
                    adapter.setLastRunOn(new LocalDateTime());
                    if (unitTestsPassForAdapter(adapter)) {
                        ChurchDetail churchDetail = getChurchDetailFromAdapter(adapterPath);
                        updateAdapter(adapter, churchDetail);
                    }
                } else {
                    throw new RuntimeException(
                            "Could not find an adapter in the database for path "
                                    + adapterPath);
                }
            } else {
                throw new RuntimeException(adapterPath + " does not exist!");
            }
        } else {
            throw new RuntimeException("Null adapter path");
        }
    }

    private void updateAdapter(Adapter adapter, ChurchDetail churchDetail) {
        if (adapter != null && churchDetail != null) {
            logger.info("adding church with name " + churchDetail.getName()
                    + " to database..");
            adapterDao.deleteDataFor(adapter);
            churchDetail.setUpdatedOn(new LocalDateTime());
            churchDetail.setAdapter(adapter);
            adapter.setChurchDetail(churchDetail);
            logger.info("saving updated adapter...");
            adapterDao.save(adapter);
        } else {
            throw new RuntimeException("Invalid object passed to updateAdapter");
        }
    }

    private ChurchDetail getChurchDetailFromAdapter(String adapterPath) {
        String filePath = adapterPath + File.separatorChar
                + Constants.OUTPUT_FILE;
        ChurchDetail churchDetail = null;
        File file = new File(filePath);
        if (file.exists()) {
            try {
                logger.info("unmarshalling church detail at " + filePath);
                JAXBContext context = JAXBContext
                        .newInstance(ChurchDetail.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                churchDetail = (ChurchDetail) unmarshaller.unmarshal(file);
                // the record comes in simply unmarshalled from jaxb, which
                // does not establish the parent/child relationships needed
                // by hibernate, so we'll do that here before persisting
                churchDetail.refreshHibernateRelationships();
            } catch (Exception e) {
                throw new RuntimeException(
                        "Could not unmarshall church detail at " + filePath, e);
            }

        } else {
            throw new RuntimeException(filePath
                    + " does not exist after successfully unit testing.");
        }
        return churchDetail;
    }

    private boolean unitTestsPassForAdapter(Adapter adapter)
            throws ExecuteException, IOException {
        logger.info("Checking if " + adapter.getPath() + " passes test...");
        CommandLine cmd = new CommandLine("gradle");
        cmd.addArgument("test");
        DefaultExecutor executor = new DefaultExecutor();
        executor.setWorkingDirectory(new File(adapter.getPath()));
        int exitValue = executor.execute(cmd);
        if (exitValue != 1) {
            logger.info("Project successfully passed.");
            return Boolean.TRUE;
        } else {
            logger.info("Project failed test.. skipping update...");
            return Boolean.FALSE;
        }
    }
}
