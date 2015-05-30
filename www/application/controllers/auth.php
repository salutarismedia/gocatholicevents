<?php
if (! defined ( 'BASEPATH' ))
	exit ( 'No direct script access allowed' );
class Auth extends CI_Controller {
	public function __construct() {
		parent::__construct ();
		$this->load->library('session');
	}
	function index() {
		$this->load->helper ( 'form' );
		$this->load->library ( 'form_validation' );
		$this->load->model ( 'parishuser' );
		
		$this->form_validation->set_rules ( 'username', 'Username', 'trim|required' );
		$this->form_validation->set_rules ( 'password', 'Password', 'trim|required' );
		
		if ($this->form_validation->run () == FALSE) {
			$this->load->view ( 'login' );
		} else {
			$name = $this->input->post('username');
			$password = $this->input->post('password');
			$parishUser = $this->parishuser->findOneBy($name, $password);
			if ($parishUser != null) {
				// log_message('info', 'logged in');
				$this->session->set_userdata ( ID, $this->parishuser->id );
				$this->session->set_userdata ( NAME, $this->parishuser->name );
				$this->session->set_userdata ( EMAIL, $this->parishuser->email );
				$this->session->set_userdata ( CHURCH_ID, $this->parishuser->church_id );
				redirect ( 'parishaccount' );
			} else {
				// log_message('info', 'did not log in');
				$this->session->set_userdata ( INFO, 'Incorrect username or password.  Please try again.' );
				$this->load->view ( 'login' );
			}
		}
	}
	function logout() {
		$this->session->sess_destroy ();
		redirect ( 'index' );
	}
}