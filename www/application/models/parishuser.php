<?php

/*
 * models a user in the system
 */
class ParishUser extends CI_Model {
	var $id;
	var $name;
	var $email;
	var $password;
	var $church_id;
	function __construct() {
		parent::__construct ();
	}
	function findOne($id) {
		$this->db->where ( "id", $id );
		$query = $this->db->get ( PARISH_USERS_TABLE );
		$result = $query->row ();
		$this->id = $result->id;
		$this->name = $result->name;
		$this->email = $result->email;
		$this->church_id = $result->church_id;
		return $this;
	}
	function exists() {
		$this->db->where ( 'id', $this->id );
		$this->db->from ( PARISH_USERS_TABLE );
		$count = $this->db->count_all_results ();
		return $count === 1;
	}
	function save() {
		if ($this->exists ()) {
			$this->updateRecord ();
		} else {
			$this->insertRecord ();
		}
	}
	function updateRecord() {
		echo "Updating record " . $this->id;
		$data = array (
				'name' => $this->name,
				'email' => $this->email 
		);
		$this->db->where ( 'id', $this->id );
		$this->db->update ( PARISH_USERS_TABLE, $data );
	}
	// TODO
	// function insertRecord() {
	// echo "Adding new record";
	// $data = array (
	// 'name' => $this->name,
	// 'email' => $this->email
	// // TODO 'password' => password_hash ( $this->password, PASSWORD_BCRYPT )
	// );
	// $this->db->insert ( PARISH_USERS_TABLE, $data );
	// }
	// TODO - switch to active record syntax
	function isUniquename($name) {
		$query = $this->db->query ( "SELECT * FROM (`" . PARISH_USERS_TABLE . "`) WHERE LOWER(`name`)=LOWER('$name') LIMIT 1" );
		return ($query->num_rows () == 0);
	}
	// TODO - switch to active record syntax
	function isUniqueEmail($email) {
		$query = $this->db->query ( "SELECT * FROM (`" . PARISH_USERS_TABLE . "`) WHERE LOWER(`email`)=LOWER('$email') LIMIT 1" );
		return ($query->num_rows () == 0);
	}
	// TODO - switch to active record syntax
	function findOneBy($name, $password) {
		if ($name && $password) {
			// TODO - $password = password_hash ( $password, PASSWORD_BCRYPT );
			$sql = "SELECT `id`, name, email FROM " . PARISH_USERS_TABLE . " WHERE LOWER(`name`)= LOWER(?) AND `password`=SHA(?)";
			$query = $this->db->query ( $sql, array (
					$name,
					$password 
			) );
			// log_message('debug', "logging in with sql of $sql");
			if ($query->num_rows () == 1) {
				$this->id = $query->row ()->id;
				$this->name = $query->row ()->name;
				$this->email = $query->row ()->email;
				/*
				 * log_message('debug', "user logged in with $sql and account name " . $this->input->post('name') . " and was assigned account name " . $this->name . " and id " . $this->id);
				 */
				return TRUE;
				// DB::Query("UPDATE `Accounts` SET `lastLoggedIn`='" . mktime() . "' WHERE `name`='$name'");
			}
		}
		return FALSE;
	}
	// TODO - switch to active record syntax
	function unlock($name) {
		$sql = "UPDATE " . PARISH_USERS_TABLE . " SET enabled='true' WHERE name = ?";
		log_message ( 'debug', "attempting to unlock $name account with sql $sql" );
		$this->db->query ( $sql, array (
				$name 
		) );
		
		if (mysql_affected_rows () == 1) {
			return TRUE;
		} else {
			log_message ( 'error', "Couldn't unlock account" );
			return FALSE;
		}
	}
}