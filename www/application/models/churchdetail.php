<?php

class ChurchDetail extends CI_Model {
	
	var $id;
	var $name;
	var $streetAddress;
	var $state;
	var $city;
	var $zip;
	var $phone;
	var $url;
	var $lat;
	var $lon;
	
    function __construct() {
        parent::__construct();
    }

    function findOne($id) {
    	$this->db->where ( "id", $id );
		$query = $this->db->get ( TABLE_CHURCHES );
		$result = $query->row ();
		$this->id = $result->id;
		$this->name = $result->name;
		$this->streetAddress = $result->streetAddress;
		$this->state = $result->state;
		$this->city = $result->city;
		$this->zip = $result->zip;
		$this->phone = $result->phone;
		$this->url = $result->url;
		$this->lat = $result->lat;
		$this->lon = $result->lon;
		return $this;
    }
    
    function findOneBy($country, $state, $citySlug, $nameSlug) {
        $this->db->select('*');
        $this->db->from(TABLE_CHURCHES);
        $this->db->where(array (
                COL_COUNTRY => $country,
                COL_STATE => $state,
                COL_CITY_SLUG => $citySlug,
                COL_NAME_SLUG => $nameSlug 
        ));
        $query = $this->db->get();
        return $query->row();
    }
}
