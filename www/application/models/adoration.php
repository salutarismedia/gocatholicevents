<?php
require_once (APPPATH . 'models/geomodel.php');

class Adoration extends GeoModel {

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
        
        return $query;
    }

    function findByChurchId($id) {
        return $this->findByChurchIdAndEventType($id, EVENT_TYPE_ADORATION);
    }
}

?>