<?php
require_once (APPPATH . 'models/geomodel.php');
class ChurchEvent extends GeoModel {
	function __construct() {
		parent::__construct ();
	}
	function findByLatAndLon($lat, $lon, $limit) {
		$this->prepareGeoEventQuery ( $lat, $lon, $limit );
		// can't put these in an array because the keys overwrite one another
		$this->db->where ( COL_EVENT_TYPE . " !=", EVENT_TYPE_MASS );
		$this->db->where ( COL_EVENT_TYPE . " !=", EVENT_TYPE_VIGIL_MASS );
		$this->db->where ( COL_EVENT_TYPE . " !=", EVENT_TYPE_ADORATION );
		$this->db->where ( COL_EVENT_TYPE . " !=", EVENT_TYPE_CONFESSION );
		$this->orderByDate ();
		$this->limitByExpiration ();
		log_message ( 'debug', "query was " . $this->db->last_query () );
		$query = $this->db->get ();
		return $query;
	}
	function limitByExpiration() {
		// $afterTime = date('Y-m-d', strtotime("-1 days"));
		$afterTime = date ( 'Y-m-d', strtotime ( "now" ) );
		$this->db->where ( "startDate" . " >=", $afterTime );
	}
	function orderByDate() {
		$this->db->order_by ( "startDate", ASC );
		$this->db->order_by ( "startTime", ASC );
	}
	function setUpChurchQuery($churchId) {
		$this->db->select ( '*' );
		$this->db->from ( TABLE_EVENTS );
		$this->db->where ( TABLE_EVENTS_FK_CHURCH_DETAIL, $churchId );
		// can't put these in an array because the keys overwrite one another
		$this->db->where ( COL_EVENT_TYPE . " !=", EVENT_TYPE_MASS );
		$this->db->where ( COL_EVENT_TYPE . " !=", EVENT_TYPE_VIGIL_MASS );
		$this->db->where ( COL_EVENT_TYPE . " !=", EVENT_TYPE_ADORATION );
		$this->db->where ( COL_EVENT_TYPE . " !=", EVENT_TYPE_CONFESSION );
		$this->orderByDate ();
	}
	function findByChurchIdAndLimit($churchId, $limit) {
		$this->setUpChurchQuery ( $churchId );
		$this->db->limit ( $limit );
		$query = $this->db->get ();
		log_message ( 'debug', "query was " . $this->db->last_query () );
		return $query;
	}
	function findByChurchId($churchId) {
		$this->setUpChurchQuery ( $churchId );
		$this->orderByDate ();
		$query = $this->db->get ();
		return $query;
	}
}
