<?php
require_once (APPPATH . 'models/geomodel.php');

class Mass extends GeoModel {

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
                COL_EVENT_TYPE => EVENT_TYPE_MASS 
        ));
        $this->db->or_where(COL_EVENT_TYPE . " =", EVENT_TYPE_VIGIL_MASS);
        $query = $this->db->get();
        
        return $query;
    }

    /**
     * returns masses recently added
     */
    function getRecentMasses() {
        $this->db->select(TABLE_CHURCHES . '.*,' . TABLE_EVENTS . '.*,' . TABLE_CHURCHES . ".id as churchId," . TABLE_CHURCHES . ".name as churchName," . TABLE_EVENTS . '.id as eventId,' . TABLE_EVENTS . '.name as eventName');
        $this->db->from(TABLE_EVENTS);
        $this->db->where(array (
                COL_EVENT_TYPE => EVENT_TYPE_MASS 
        ));
        $this->db->or_where(COL_EVENT_TYPE . " =", EVENT_TYPE_VIGIL_MASS);
        $this->db->join(TABLE_CHURCHES, JOIN_CHURCHES_TO_EVENTS);
        $this->db->limit(10);
        $this->db->order_by("startTime", "desc");
        
        $query = $this->db->get();
        
        return $query;
    }

    function findByChurchId($churchId) {
        $this->db->select('*');
        $this->db->from(TABLE_EVENTS);
        $this->db->where(TABLE_EVENTS_FK_CHURCH_DETAIL, $churchId);
        $this->db->where("(" . COL_EVENT_TYPE . " = '" . EVENT_TYPE_MASS . "' or " . COL_EVENT_TYPE . " = '" . EVENT_TYPE_VIGIL_MASS . "')");
        $this->db->order_by("startDate", "asc");
        $query = $this->db->get();
        return $query;
    }
}

?>