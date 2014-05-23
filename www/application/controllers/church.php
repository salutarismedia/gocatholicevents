<?php
if (! defined('BASEPATH'))
    exit('No direct script access allowed');

class Church extends CI_Controller {

    public function __construct() {
        parent::__construct();
        $this->load->model('mass');
        $this->load->model('adoration');
        $this->load->model('confession');
        $this->load->model("churchdetail");
        $this->load->model("churchevent");
        $this->load->helper("text");
    }

    public function detail($country, $state, $citySlug, $nameSlug) {
        $church = $this->churchdetail->findOneBy($country, $state, $citySlug, $nameSlug);
        $churchAddress = $church->streetAddress . ", " . $church->city . ", " . $church->state . " " . $church->zip;
        $events = $this->churchevent->findByChurchIdAndLimit($church->id, 5);
        $data = array (
                'church' => $church,
                'churchAddress' => $churchAddress,
                'events' => $events,
                'mass' => $this->mass->findByChurchId($church->id),
                'adoration' => $this->adoration->findByChurchId($church->id),
                'confession' => $this->confession->findByChurchId($church->id) 
        );
        // $this->output->enable_profiler(TRUE);
        $this->load->view('church', $data);
    }
}
