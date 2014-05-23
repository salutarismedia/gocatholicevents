package com.sm.gce.common.test;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.junit.AfterClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sm.gce.common.exceptions.ParseException;
import com.sm.gce.common.model.ChurchDetail;
import com.sm.gce.common.model.ChurchDetailProvider;
import com.sm.gce.util.Constants;
import com.sm.gce.util.LoggingObject;

/**
 * a generic base class for parser tests. useful for working with directories
 * 
 */
public abstract class AbstractParserTest extends LoggingObject {

    private ChurchDetailProvider churchDetailProvider;
    private static ChurchDetail churchDetail;

    public AbstractParserTest(ChurchDetailProvider churchDetailProvider) {
        this.churchDetailProvider = churchDetailProvider;
    }

    public synchronized ChurchDetail getChurchDetail() throws ParseException {
        if (churchDetail == null) {
            churchDetail = churchDetailProvider.getChurchDetail();
        }
        return churchDetail;
    }

    @AfterClass
    public static void after() throws Exception {
        Logger logger = LoggerFactory.getLogger(AbstractParserTest.class
                .getName());
        logger.debug("marshalling church detail to disk");
        JAXBContext context = JAXBContext.newInstance(ChurchDetail.class);
        Marshaller marshaller = context.createMarshaller();
        File file = new File(Constants.OUTPUT_FILE);
        marshaller.marshal(churchDetail, file);
    }

}
