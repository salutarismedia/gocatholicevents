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
            case "HOLY" :
                return "Holy Days";
            default :
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
        return site_url("church/detail") . "/" . strtolower($churchObj->country) . "/" . strtolower($churchObj->state) . "/" . $churchObj->citySlug . "/" . $churchObj->nameSlug;
    }
}

if (! function_exists('smallSacramentLink')) {

    /**
     * prints out a sacrament link on a single line when the parish is already known
     */
    function smallSacramentLink($item) {
        return toDay($item->day) . " - " . toTime($item->startTime) . "</td></tr>";
    }
}

if (! function_exists('smallParishSacramentLink')) {

    /**
     * prints out a sacrament link with the parish name on a single line
     */
    function smallParishSacramentLink($item) {
        // print_r($item);
        return toDay($item->day) . " - " . toTime($item->startTime) . " @ " . "<a href='" . church_url($item) . "'>" . $item->churchName . "</a>";
    }
}

if (! function_exists('smallParishEventLink')) {

    /**
     * prints out an event link on a single line with the parish name
     */
    function smallParishEventLink($item) {
        // print_r($item);
        $link = "";
        if ($item->url) {
            $link .= "<a href='" . $item->url . "' target='_blank'>";
        }
        $link .= $item->name;
        if ($item->url) {
            $link .= "</a>";
        }
        $link .= " @ <a href='" . church_url($item) . "'>" . $item->churchName . "</a>";
        return $link;
    }
}

if (! function_exists('largeEventLink')) {

    /**
     * prints out an event link on two lines
     */
    function largeEventLink($event) {
        $link = "<h3>";
        if ($event->url) {
            $link .= "<a href='" . $event->url . "' target='_blank'>";
        }
        $link .= ellipsize($event->name, LENGTH_EVENT_TITLE);
        if ($event->url) {
            $link .= "</a>";
        }
        $link .= "&nbsp;&nbsp;&nbsp;<small>" . $event->startDate . "</small>";
        $link .= "</h3>";
        $link .= "<span>";
        $link .= ellipsize($event->description, LENGTH_EVENT_DESC);
        $link .= "</span>";
        return $link;
    }
}
