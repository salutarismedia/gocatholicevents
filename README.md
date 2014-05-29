## Overview
Go Catholic Events (http://www.gocatholicevents.com) or GCE for short is an open-source project to aggregate events happening at Catholic Churches into one location.  Every week many events are happening at our local churches, but there's currently no centralized repository for this information.  That's where GCE comes in.  

##Writing an adapter for your parish
Just a quick note as most folks will probably want to contribute by adding adapters for their area.  For that, see the adapters section in the [engine](https://github.com/salutarismedia/gocatholicevents/tree/master/engine) module.


##Components
The system is made of a few different components.  Each is detailed below and has its own respective README for how to get up and running.


###Engine
Located in the [engine project](https://github.com/salutarismedia/gocatholicevents/tree/master/engine), this is the heart of GCE.  It is responsible for running each parish adapter, storing the information in a local database, and then uploading this database to the main web site.  The engine iteself is made up of a couple of components detailed in the [README.md](https://www.github.com/salutarismedia/gocatholicevents/blob/master/engine/README.md)

###Web Site
Located in the [www project](https://github.com/salutarismedia/gocatholicevents/tree/master/www), this is the web site that appears at http://www.gocatholicevents.com.  For more information, see the www [README.md](https://www.github.com/salutarismedia/gocatholicevents/blob/master/www/README.md)

###iOS app
We are currently looking for someone who can contribute an iOS app. 
 
###Android app
We are also looking for someone who can contribute an Android app.  :)

###Eclipse Tooling
This project is built using Eclipse, but should run just fine in another IDE.  The project is build using Gradle.  If you are using Eclipse, the Gradle STS plugin can speed up development:  https://github.com/spring-projects/eclipse-integration-gradle/
