<?php

class ChurchDetail extends CI_Model {

    function __construct() {
        parent::__construct();
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
