## Overview
The engine is responsible for running each parish adapter, storing the information in a local database, and then uploading this database to the main web site.  The engine iteself is made up of a couple of subprojects.

##Subprojects
These subprojects work together to gather the information from the different parsishes and upload it to the main site

###Adapter Runner
Runs each of the adapters in the system

###Adapters
Each parish has its own adapter in a sub folder within this folder

###Database
Module for interacting with the database

###Engine
Invokes the adapter-runner to run the adapters, then the uploader to upload the output to the main site

###Model
Contains model classes

###Test
Common test classes

###Uploader
Exports the local db and uplaods it to the main site

###Util
Common util classes
