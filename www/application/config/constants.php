<?php
if (! defined('BASEPATH'))
    exit('No direct script access allowed');
    
    /*
 * |-------------------------------------------------------------------------- | Database Tables |--------------------------------------------------------------------------
 */
define('TABLE_CHURCHES', 'ChurchDetails');
define('TABLE_EVENTS', 'ChurchEvents');
define('TABLE_EVENTS_FK_CHURCH_DETAIL', 'church_detail_id');
define('JOIN_CHURCHES_TO_EVENTS', TABLE_CHURCHES . '.id = ' . TABLE_EVENTS . '.' . TABLE_EVENTS_FK_CHURCH_DETAIL);
define('PARISH_USERS_TABLE', 'ParishUsers');
define('ID', 'id');
define('NAME', 'name');
define('EMAIL', 'email');
define('INFO', 'info');
define('USER', 'user');
define('CHURCH_ID', 'church_id');
define('CHURCH', 'church');
define('COL_EVENT_TYPE', 'type');
define('COL_COUNTRY', 'country');
define('COL_STATE', 'state');
define('COL_CITY', 'city');
define('COL_CITY_SLUG', 'citySlug');
define('COL_NAME', 'name');
define('COL_NAME_SLUG', 'nameSlug');
define('COL_START_DATE', 'startDate');
define('COL_START_TIME', 'startTime');
define('EVENT_TYPE_MASS', 'MASS');
define('EVENT_TYPE_VIGIL_MASS', 'VIGIL_MASS');
define('EVENT_TYPE_ADORATION', 'ADORATION');
define('EVENT_TYPE_CONFESSION', 'CONFESSION');
define('EVENT_TYPE_EVENTS', 'EVENTS');
define('URL_MASSES', "mass");
define('URL_ADORATIONS', "adoration");
define('URL_CONFESSIONS', "confession");
define('URL_EVENTS', "events");
define('LENGTH_EVENT_TITLE', 100);
define('LENGTH_EVENT_DESC', 300);
define('LAT', 'LAT');
define('LON', 'LON');
define('CITY', 'CITY');
define('STATE', 'STATE');
define('LOCATION', 'LOCATION');
define('LIMIT_SMALL', 10);
define('LIMIT_LARGE', 100);
define('ASC', "asc");
define('DESC', "desc");

/*
 * |-------------------------------------------------------------------------- | File and Directory Modes |-------------------------------------------------------------------------- | | These prefs are used when checking and setting modes when working | with the file system. The defaults are fine on servers with proper | security, but you may wish (or even need) to change the values in | certain environments (Apache running a separate process for each | user, PHP under CGI with Apache suEXEC, etc.). Octal values should | always be used to set the mode correctly. |
 */
define('FILE_READ_MODE', 0644);
define('FILE_WRITE_MODE', 0666);
define('DIR_READ_MODE', 0755);
define('DIR_WRITE_MODE', 0777);

/*
 * |-------------------------------------------------------------------------- | File Stream Modes |-------------------------------------------------------------------------- | | These modes are used when working with fopen()/popen() |
 */

define('FOPEN_READ', 'rb');
define('FOPEN_READ_WRITE', 'r+b');
define('FOPEN_WRITE_CREATE_DESTRUCTIVE', 'wb'); // truncates existing file data, use with care
define('FOPEN_READ_WRITE_CREATE_DESTRUCTIVE', 'w+b'); // truncates existing file data, use with care
define('FOPEN_WRITE_CREATE', 'ab');
define('FOPEN_READ_WRITE_CREATE', 'a+b');
define('FOPEN_WRITE_CREATE_STRICT', 'xb');
define('FOPEN_READ_WRITE_CREATE_STRICT', 'x+b');


/* End of file constants.php */
/* Location: ./application/config/constants.php */