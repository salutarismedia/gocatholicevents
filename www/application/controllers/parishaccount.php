<?php
if (! defined ( 'BASEPATH' ))
	exit ( 'No direct script access allowed' );
class ParishAccount extends CI_Controller {
	public function __construct() {
		parent::__construct ();
		$this->load->library ( 'session' );
		$this->load->model ( 'parishuser' );
		$this->load->model ( 'churchdetail' );
	}
	public function index() {
		$churchId = $this->session->userdata ( CHURCH_ID );
		if (isset ( $churchId )) {
			$user = $this->parishuser->findOne ( $this->session->userdata ( ID ) );
			$parish = $this->churchdetail->findOne($user->church_id);
			$data = array (
					USER => $user,
					CHURCH => $parish
			);
			$this->load->view ( 'parishaccount', $data );
		} else {
			$this->load->view ( 'index' );
		}
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
