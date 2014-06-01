package com.sm.gce.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.sm.gce.common.model.Adapter;
import com.sm.gce.common.model.ChurchDetail;
import com.sm.gce.common.model.ChurchEvent;
import com.sm.gce.common.model.Tag;
import com.sm.gce.common.model.enums.EventType;

public class AdapterDaoTest {

    private static final String TXT_UPDATED_TEST = "updated-name";
    private static final String TXT_TEST = "name";
    private static final String TXT_ADAPTER_PATH = "adapter-path";
    private AdapterDao adapterDao;

    @Before
    public void before() {
        adapterDao = new AdapterDao();
    }

    @Test
    public void resetAdapterDeletesChurchDetail() {
        Adapter adapter = addDisabledAdapter();
        ChurchDetail churchDetail = new ChurchDetail();
        churchDetail.setAdapter(adapter);
        adapter.setChurchDetail(churchDetail);
        adapterDao.save(adapter);
        adapterDao.deleteDataFor(adapter);
        Adapter chkAdapter = adapterDao.get(adapter);
        assertNull(chkAdapter.getChurchDetail());

    }

    @Test
    public void findByPath() {
        adapterDao.deleteAdapters();
        addDisabledAdapter();
        Adapter adapter = adapterDao.findOneByPath(TXT_ADAPTER_PATH);
        assertEquals(TXT_ADAPTER_PATH, adapter.getPath());
    }

    @Test
    public void getAdapters() {
        adapterDao.deleteAdapters();
        addDisabledAdapter();
        addEnabledAdapter();
        List<Adapter> adapters = adapterDao.getAdapters();
        assertTrue(adapters.size() == 2);
    }

    @Test
    public void adapterSavesEventTag() {
        adapterDao.deleteAdapters();
        Adapter adapter = new Adapter();
        ChurchDetail churchDetail = new ChurchDetail();
        churchDetail.setAdapter(adapter);
        adapter.setChurchDetail(churchDetail);
        ChurchEvent churchEvent = new ChurchEvent();
        churchEvent.setEventType(EventType.MASS);
        churchEvent.setChurchDetail(churchDetail);
        churchDetail.getEvents().add(churchEvent);
        Tag tag = new Tag();
        tag.setValue(TXT_TEST);
        tag.setChurchEvent(churchEvent);
        churchEvent.getTags().add(tag);
        adapterDao.save(adapter);
        Tag updateTag = adapter.getChurchDetail().getEvents(EventType.MASS)
                .get(0).getTags().get(0);
        updateTag.setValue(TXT_UPDATED_TEST);
        adapterDao.save(adapter);
        Adapter chkAdapter = adapterDao.get(adapter);
        ChurchDetail chkChurchDetail = chkAdapter.getChurchDetail();
        ChurchEvent chkChurchEvent = chkChurchDetail.getEvents(EventType.MASS)
                .get(0);
        Tag chkTag = chkChurchEvent.getTags().get(0);
        assertEquals(TXT_UPDATED_TEST, chkTag.getValue());
    }

    @Test
    public void adapterCascadeSavesChurchEvent() {
        adapterDao.deleteAdapters();
        Adapter adapter = new Adapter();
        ChurchDetail churchDetail = new ChurchDetail();
        churchDetail.setAdapter(adapter);
        adapter.setChurchDetail(churchDetail);
        ChurchEvent churchEvent = new ChurchEvent();
        churchEvent.setEventType(EventType.MASS);
        churchEvent.setName(TXT_TEST);
        churchEvent.setChurchDetail(churchDetail);
        churchDetail.getEvents().add(churchEvent);
        adapterDao.save(adapter);
        adapter.getChurchDetail().getEvents(EventType.MASS).get(0)
                .setName(TXT_UPDATED_TEST);
        adapterDao.save(adapter);
        Adapter chkAdapter = adapterDao.get(adapter);
        ChurchDetail chkChurchDetail = chkAdapter.getChurchDetail();
        ChurchEvent chkChurchEvent = chkChurchDetail.getEvents(EventType.MASS)
                .get(0);
        assertEquals(TXT_UPDATED_TEST, chkChurchEvent.getName());
    }

    @Test
    public void adapterSavesChurchDetail() {
        adapterDao.deleteAdapters();
        Adapter adapter = new Adapter();
        ChurchDetail churchDetail = new ChurchDetail();
        churchDetail.setName(TXT_TEST);
        churchDetail.setAdapter(adapter);
        adapter.setChurchDetail(churchDetail);
        adapterDao.save(adapter);
        adapter.getChurchDetail().setName(TXT_UPDATED_TEST);
        adapterDao.save(adapter);
        Adapter chkAdapter = adapterDao.get(adapter);
        ChurchDetail chkChurchDetail = chkAdapter.getChurchDetail();
        assertEquals(TXT_UPDATED_TEST, chkChurchDetail.getName());
    }

    @Test
    public void disableAdapter() {
        adapterDao.deleteAdapters();
        Adapter adapter = addEnabledAdapter();
        adapterDao.disableAdapter(adapter);
        List<Adapter> adapters = adapterDao.getDisabledAdapters();
        assertTrue(adapters.size() == 1);
    }

    @Test
    public void enableAdapter() {
        adapterDao.deleteAdapters();
        Adapter adapter = addDisabledAdapter();
        adapterDao.enableAdapter(adapter);
        List<Adapter> adapters = adapterDao.getEnabledAdapters();
        assertTrue(adapters.size() == 1);
    }

    @Test
    public void getEnabledAdapters() {
        adapterDao.deleteAdapters();
        addEnabledAdapter();
        List<Adapter> adapters = adapterDao.getEnabledAdapters();
        assertTrue(adapters.size() == 1);
    }

    @Test
    public void getDisabledAdapters() {
        adapterDao.deleteAdapters();
        addDisabledAdapter();
        List<Adapter> adapters = adapterDao.getDisabledAdapters();
        assertTrue(adapters.size() == 1);
    }

    private Adapter addDisabledAdapter() {
        Adapter adapter = new Adapter();
        adapter.setName("disabled-adapter");
        adapter.setEnabled(Boolean.FALSE);
        adapter.setPath(TXT_ADAPTER_PATH);
        adapterDao.save(adapter);
        return adapter;
    }

    private Adapter addEnabledAdapter() {
        Adapter adapter = new Adapter();
        adapter.setName("enabled-adapter");
        adapter.setEnabled(Boolean.TRUE);
        adapter.setPath(TXT_ADAPTER_PATH);
        adapterDao.save(adapter);
        return adapter;
    }

    @Ignore
    @Test
    public void viewSchema() {
        AnnotationConfiguration conf = (new AnnotationConfiguration())
                .configure();
        new SchemaExport(conf).create(true, false);
    }
}
