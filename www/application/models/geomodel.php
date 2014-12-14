<?php
if (! defined('BASEPATH'))
    exit('No direct script access allowed');

class GeoModel extends CI_Model {

    function __construct() {
        parent::__construct();
    }

    function prepareGeoEventQuery($lat, $lon, $limit) {
    	// TODO - can this be moved to a class variable?
    	$weekDays = array('SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT');
    	
        // TODO - make user configurable to
        // search within <distance> miles
        $distance = 500;
        
        $this->db->select(TABLE_CHURCHES . '.*,' . TABLE_EVENTS . '.*,' . TABLE_CHURCHES . ".id as churchId," . TABLE_CHURCHES . ".name as churchName, ( 3959 * acos( cos( radians(" . $lat . ") ) * cos( radians( " . TABLE_CHURCHES . ".lat ) ) * cos( radians( " . TABLE_CHURCHES . ".lon ) - radians(" . $lon . ") ) + sin( radians(" . $lat . ") ) * sin( radians( lat ) ) ) ) AS distance");
        $this->db->having('distance < ' . $distance);
        $this->db->from(TABLE_EVENTS);
        $this->db->join(TABLE_CHURCHES, JOIN_CHURCHES_TO_EVENTS);
        $this->db->limit($limit);        
        $this->db->order_by(COL_START_DATE, ASC);
        
        // beginning today, order out by week day
        $today = strtoupper(date('D'));
        $dayIndex = array_search($today, $weekDays);
        for ($i = 0; $i < 7; $i++) {
        	$this->db->order_by("day = '" . $weekDays[$dayIndex] ."'", DESC);
        	$dayIndex = ($dayIndex + 1) % 7;
        }
        
        $this->db->order_by(COL_START_TIME, ASC);
    }
}

