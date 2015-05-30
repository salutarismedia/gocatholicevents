<?php
if (! defined ( 'BASEPATH' ))
	exit ( 'No direct script access allowed' );
class Auth extends CI_Controller {
	public function __construct() {
		parent::__construct ();
	}
	function index() {
		$this->load->helper ( 'form' );
		$this->load->library ( 'form_validation' );
		$this->load->model ( 'user' );
		
		$this->form_validation->set_rules ( 'username', 'Username', 'trim|required' );
		$this->form_validation->set_rules ( 'password', 'Password', 'trim|required' );
		
		if ($this->form_validation->run () == FALSE) {
			$this->load->view ( 'login' );
		} else {
			
			if ($this->user->login ()) {
				// log_message('info', 'logged in');
				$this->session->set_userdata ( 'id', $this->user->id );
				$this->session->set_userdata ( 'name', $this->user->name );
				$this->session->set_userdata ( 'email', $this->user->email );
				redirect ( 'account' );
			} else {
				// log_message('info', 'did not log in');
				$this->session->set_userdata ( 'info', 'Incorrect username or password.  Please try again.' );
				$this->load->view ( 'login' );
			}
		}
	}
	function logout() {
		$this->session->sess_destroy ();
		redirect ( 'index' );
	}
}