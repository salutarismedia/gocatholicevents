## Overview
Go Catholic Events or GCE ([http://www.gocatholicevents.com]) is an open-source project to aggregate everything happening at Catholic Churches into one location.  Every week many events are happening at our local churches, but there's no real way to aggregate this information together and make it easily discoverable.  That's where GCE comes in.  

##Components
The system is made of a few different components.  Each is detailed below and has its own respective README for how to get up and running.

##Writing an adapter for your parish
If you're looking to write an adapter for your parish, check out the [engine](https://github.com/salutarismedia/gocatholicevents/tree/master/engine) module.


###Engine
Located in the [engine folder](https://github.com/salutarismedia/gocatholicevents/tree/master/engine), this is the heart of GCE.  It is responsible for running each parish adapter, storing the information in the database, and uploading it to the main web site database.  The engine iteself is made of more than one component which are explained in its [README.md](https://www.github.com/salutarismedia/gocatholicevents/blob/master/engine/README.md)

###Web Site
Located in the [www folder](https://github.com/salutarismedia/gocatholicevents/tree/master/www), this is the web site that appears at ([http://www.gocatholicevents.com]).  For more information, see the subproject [README.md](https://www.github.com/salutarismedia/gocatholicevents/blob/master/www/README.md)
 
 ###iOS app
 We are currently looking for someone who can contribute an iOS app. 
 
 ###Android app
 We are also looking for someone who can contribute an Android app.  :)
