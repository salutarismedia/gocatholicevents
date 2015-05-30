<?php

/*
 * models a user in the system TODO - this class should be updated to use active record syntax
 */
class User extends CI_Model {
	var $id;
	var $name;
	var $email;
	var $password;
	var $rid;
	function __construct() {
		parent::__construct ();
		$this->rid = uniqid();
	}
	function findOne($id) {
		$this->db->where ( "id", $id );
		$query = $this->db->get ( USERS_TABLE );
		$result = $query->row ();
		$this->id = $result->id;
		$this->name = $result->name;
		$this->email = $result->email;
		$this->rid = $result->rid;
		return $this;
	}
	function exists() {
		$this->db->where ( 'id', $this->id );
		$this->db->from ( USERS_TABLE );
		$count = $this->db->count_all_results ();
		return $count === 1;
	}
	function save() {
		if ($this->exists ()) {
			$this->updateRecord();
		} else {
			$this->insertRecord();
		}
	}
	function updateRecord() {
		echo "Updating record " . $this->id;
		$data = array (
				'name' => $this->name,
				'email' => $this->email,
					
		);
		$this->db->where ( 'id', $this->id );
		$this->db->update ( USERS_TABLE, $data );
	}
	function insertRecord() {
		echo "Adding new record";
		$data = array (
				'name' => $this->name,
				'email' => $this->email,
				'password' => password_hash($this->password, PASSWORD_BCRYPT),
				'rid' => uuid()
		);
		$this->db->insert ( USERS_TABLE, $data );
	}
	// TODO - switch to active record syntax
	function isUniqueUsername($username) {
		$query = $this->db->query ( "SELECT * FROM (`" . USERS_TABLE . "`) WHERE LOWER(`username`)=LOWER('$username') LIMIT 1" );
		return ($query->num_rows () == 0);
	}
	// TODO - switch to active record syntax
	function isUniqueEmail($email) {
		$query = $this->db->query ( "SELECT * FROM (`" . USERS_TABLE . "`) WHERE LOWER(`email`)=LOWER('$email') LIMIT 1" );
		return ($query->num_rows () == 0);
	}
	// TODO - switch to active record syntax
	function findOneBy($username, $password) {
		if ($username && $password) {
			$password = password_hash($password, PASSWORD_BCRYPT);
			$sql = "SELECT `id`, username, email FROM " . USERS_TABLE . " WHERE LOWER(`username`)= LOWER(?) AND `password`=?";
			$query = $this->db->query ( $sql, array (
					$username,
					$password 
			) );
			// log_message('debug', "logging in with sql of $sql");
			if ($query->num_rows () == 1) {
				$this->id = $query->row ()->id;
				$this->name = $query->row ()->name;
				$this->email = $query->row ()->email;
				/*
				 * log_message('debug', "user logged in with $sql and account name " . $this->input->post('username') . " and was assigned account name " . $this->name . " and id " . $this->id);
				 */
				return TRUE;
				// DB::Query("UPDATE `Accounts` SET `lastLoggedIn`='" . mktime() . "' WHERE `username`='$username'");
			}
		}
		return FALSE;
	}
	// TODO - switch to active record syntax
	function confirmRid($username, $rid) {
		$sql = "SELECT `rid` FROM " . USERS_TABLE . " WHERE username = ? AND rid = ?";
		log_message ( 'debug', "confirming $rid for $username with sql of $sql" );
		
		$query = $this->db->query ( $sql, array (
				$username,
				$rid 
		) );
		
		if ($query->num_rows () == 1) {
			return TRUE;
		} else {
			log_message ( 'error', "could not confirm $username and $rid" );
			return FALSE;
		}
	}
	// TODO - switch to active record syntax
	function unlock($username) {
		$sql = "UPDATE " . USERS_TABLE . " SET enabled='true' WHERE username = ?";
		log_message ( 'debug', "attempting to unlock $username account with sql $sql" );
		$this->db->query ( $sql, array (
				$username 
		) );
		
		if (mysql_affected_rows () == 1) {
			return TRUE;
		} else {
			log_message ( 'error', "Couldn't unlock account" );
			return FALSE;
		}
	}
}