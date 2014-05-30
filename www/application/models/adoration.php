<?php
require_once (APPPATH . 'models/geomodel.php');

class Adoration extends GeoModel {

    function __construct() {
        parent::__construct();
    }

    /**
     * returns nearby items
     *
     * @param latitude $lat            
     * @param latitude $lon            
     * @return unknown
     */
    function findByLatAndLon($lat, $lon, $limit) {
        $this->prepareGeoEventQuery($lat, $lon, $limit);
        
        $this->db->where(array (
                COL_EVENT_TYPE => EVENT_TYPE_ADORATION 
        ));
        
        $query = $this->db->get();
        
        return $query->result();
    }

    function findByChurchId($churchId) {
        $this->db->select('*');
        $this->db->from(TABLE_EVENTS);
        $this->db->where(TABLE_EVENTS_FK_CHURCH_DETAIL, $churchId);
        $this->db->where(COL_EVENT_TYPE, EVENT_TYPE_ADORATION);
        $this->db->order_by("startDate", "asc");
        $query = $this->db->get();
        return $query->result();
    }
}

?>