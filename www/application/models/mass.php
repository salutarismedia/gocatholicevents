<?php
require_once (APPPATH . 'models/geomodel.php');

class Mass extends GeoModel {

    function __construct() {
        parent::__construct();
    }

    function findByLatAndLon($lat, $lon, $limit) {
        $this->prepareGeoEventQuery($lat, $lon, $limit);
        $this->db->where(array (
                COL_EVENT_TYPE => EVENT_TYPE_MASS 
        ));
        $this->db->or_where(COL_EVENT_TYPE . " =", EVENT_TYPE_VIGIL_MASS);
        $this->orderByDay();
        $query = $this->db->get();
        
        return $query;
    }

    function findByChurchId($churchId) {
        $this->db->select('*');
        $this->db->from(TABLE_EVENTS);
        $this->db->where(TABLE_EVENTS_FK_CHURCH_DETAIL, $churchId);
        $this->db->where("(" . COL_EVENT_TYPE . " = '" . EVENT_TYPE_MASS . "' or " . COL_EVENT_TYPE . " = '" . EVENT_TYPE_VIGIL_MASS . "')");
        $this->db->order_by(COL_START_DATE, ASC);
        $query = $this->db->get();
        return $query;
    }
}
