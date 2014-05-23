<?php
if (! defined('BASEPATH'))
    exit('No direct script access allowed');

/**
 * converts from database day values to
 * textual representations
 *
 * @param
 *            $dayEnum
 */
if (! function_exists('toDay')) {

    function toDay($dayEnum) {
        switch ($dayEnum) {
            case "MON" :
                return "Mon";
            case "TUE" :
                return "Tue";
            case "WED" :
                return "Wed";
            case "THU" :
                return "Thu";
            case "FRI" :
                return "Fri";
            case "SAT" :
                return "Sat";
            case "SUN" :
                return "Sun";
            case "FIRST_SAT" :
                return "First Sat";
            case "FIRST_FRI" :
                return "First Fri";
            case "HOLY":
                return "Holy Days";
            default:
                return $dayEnum;
        }
    }
}

if (! function_exists('toTime')) {

    /**
     * convert military time to local time
     *
     * @param
     *            $time
     */
    function toTime($time) {
        return date("g:i a", strtotime($time));
    }
}

if (! function_exists('church_url')) {

    /**
     * creates a church url from an object containing the requisite fields
     *
     * @param
     *            $time
     */
    function church_url($churchObj) {
        
        return site_url("church/detail") 
        . "/" . strtolower($churchObj->country)
        . "/" . strtolower($churchObj->state)
        . "/" . $churchObj->citySlug
        . "/" . $churchObj->nameSlug;
        
    }
}
