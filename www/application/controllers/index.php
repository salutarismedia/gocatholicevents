<?php
if (! defined ( 'BASEPATH' ))
	exit ( 'No direct script access allowed' );

require_once (APPPATH . 'util/GoogleMapsApi.php');
require_once (APPPATH . 'util/ip2locationlite.class.php');
class Index extends CI_Controller {
	public function __construct() {
		parent::__construct ();
	}
	public function index() {
		$this->setUserLatLon ();
		$data = array (
				'masses' => $this->getMasses ( LIMIT_SMALL ),
				'adorations' => $this->getAdorations ( LIMIT_SMALL ),
				'confessions' => $this->getConfessions ( LIMIT_SMALL ),
				'events' => $this->getEvents ( LIMIT_SMALL ) 
		);
		$this->load->view ( 'index', $data );
	}
	public function view($type) {
		// $this->output->enable_profiler(TRUE);
		$this->setUserLatLon ();
		$items = $this->getItemsByType ( $type );
		if ($type) {
			$data = array (
					'items' => $items,
					'type' => $type 
			);
			$this->load->view ( 'list', $data );
		} else {
			return "Unknown event type of " . $type;
		}
	}
	public function search() {
		$location = $this->input->get ( "location", TRUE );
		$gMap = new GoogleMapsApi ();
		$gMap->query ( $location );
		$this->setLat ( $gMap->getLat () );
		$this->setLon ( $gMap->getLon () );
		$this->setLocation ( $location );
		return $this->index ();
	}
	public function setUserLatLon() {
		if (! $this->isUserLatLonSet ()) {
			$ipLite = new ip2location_lite ();
			$ip = $this->input->server ( 'REMOTE_ADDR', TRUE );
			log_message ( 'debug', "looking up ip $ip" );
			$ipLite->init ( $ip );
			$lat = $ipLite->getLat ();
			$lon = $ipLite->getLon ();
			log_message ( 'debug', "got " . LAT . " and " . LON . " of:  $lat, $lon" );
			$this->setLat ( $lat );
			$this->setLon ( $lon );
		}
	}
	public function setLat($value) {
		$this->session->set_userdata ( LAT, $value );
	}
	public function setLon($value) {
		$this->session->set_userdata ( LON, $value );
	}
	public function setLocation($value) {
		$this->session->set_userdata ( LOCATION, $value );
	}
	public function getLat() {
		return $this->session->userdata ( LAT );
	}
	public function getLon() {
		return $this->session->userdata ( LON );
	}
	public function isUserLatLonSet() {
		$lat = $this->getLat ();
		$lon = $this->getLon ();
		if (is_numeric ( $lat ) && is_numeric ( $lon )) {
			return TRUE;
		}
		return FALSE;
	}
	public function getMasses($limit) {
		$this->load->model ( 'mass' );
		return $this->mass->findByLatAndLon ( $this->getLat (), $this->getLon (), $limit );
	}
	public function getAdorations($limit) {
		$this->load->model ( 'adoration' );
		return $this->adoration->findByLatAndLon ( $this->getLat (), $this->getLon (), $limit );
	}
	public function getConfessions($limit) {
		$this->load->model ( 'confession' );
		return $this->confession->findByLatAndLon ( $this->getLat (), $this->getLon (), $limit );
	}
	public function getEvents($limit) {
		$this->load->model ( 'churchevent' );
		return $this->churchevent->findByLatAndLon ( $this->getLat (), $this->getLon (), $limit );
	}
	public function getItemsByType($type) {
		switch ($type) {
			case URL_MASSES :
				return $this->getMasses ( LIMIT_LARGE );
			case URL_ADORATIONS :
				return $this->getAdorations ( LIMIT_LARGE );
			case URL_EVENTS :
				return $this->getEvents ( LIMIT_LARGE );
			case URL_CONFESSIONS :
				return $this->getConfessions ( LIMIT_LARGE );
			default :
				return null;
		}
	}
}
