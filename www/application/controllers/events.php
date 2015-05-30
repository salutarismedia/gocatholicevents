<?php
if (! defined ( 'BASEPATH' ))
	exit ( 'No direct script access allowed' );
require_once (APPPATH . 'util/JsonUtility.php');
class Events extends CI_Controller {
	public function __construct() {
		parent::__construct ();
		$this->load->model ( 'churchevent' );
	}
	public function json($churchId) {
		if (isset ( $churchId )) {
			$events = $this->churchevent->findByChurchId ( $churchId );
			echoAsJson ( $events->result() );
		} else {
			show_error ( 'Missing parameter', 400 );
		}
	}
}
