<?php
require_once (APPPATH . 'models/geomodel.php');

class ChurchEvent extends GeoModel {

    function __construct() {
        parent::__construct();
    }

    function findByLatAndLon($lat, $lon, $limit) {
        $this->prepareGeoEventQuery($lat, $lon, $limit);
        // can't put these in an array because the keys overwrite one another
        $this->db->where(COL_EVENT_TYPE . " !=", EVENT_TYPE_MASS);
        $this->db->where(COL_EVENT_TYPE . " !=", EVENT_TYPE_VIGIL_MASS);
        $this->db->where(COL_EVENT_TYPE . " !=", EVENT_TYPE_ADORATION);
        $this->db->where(COL_EVENT_TYPE . " !=", EVENT_TYPE_CONFESSION);
        $this->db->order_by("startDate", "desc");
        $this->db->order_by("startTime", "desc");
        $afterTime = date('Y-m-d', strtotime("-1 months"));
        $this->db->where("startDate" . " >=", $afterTime);
        $query = $this->db->get();
        return $query;
    }

    function setUpChurchQuery($churchId) {
        $this->db->select('*');
        $this->db->from(TABLE_EVENTS);
        $this->db->where(TABLE_EVENTS_FK_CHURCH_DETAIL, $churchId);
        // can't put these in an array because the keys overwrite one another
        $this->db->where(COL_EVENT_TYPE . " !=", EVENT_TYPE_MASS);
        $this->db->where(COL_EVENT_TYPE . " !=", EVENT_TYPE_VIGIL_MASS);
        $this->db->where(COL_EVENT_TYPE . " !=", EVENT_TYPE_ADORATION);
        $this->db->where(COL_EVENT_TYPE . " !=", EVENT_TYPE_CONFESSION);
        $this->db->order_by("startDate", "desc");
        $this->db->order_by("startTime", "desc");
    }

    function findByChurchIdAndLimit($churchId, $limit) {
        $this->setUpChurchQuery($churchId);
        $this->db->limit($limit);
        $query = $this->db->get();
        return $query;
    }

    function findByChurchId($churchId) {
        $this->setUpChurchQuery();
        $query = $this->db->get();
        return $query;
    }
}
