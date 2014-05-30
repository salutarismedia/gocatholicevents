## Overview
The engine is responsible for running each parish adapter, storing the information in a local database, and then uploading this database to the main web site.  The engine iteself is made up of a couple of subprojects.

##Write an Adapter
Thank you for your interest in writing an adapter!  This is the biggest need we have right now.  To write an adapter, you'll need to know Java and be familiar with Unit Testing.  Here are the steps to get up and running:

Clone the repository (copy and paste these lines into a Terminal):

        git clone https://github.com/salutarismedia/gocatholicevents.git
        
Change directory to the main project directory where you checked out the code to:

        cd gocatholicevents
        
Change directory down into the engine sub project:

        cd engine
        
Build the common components for the engine (gradle will install itself):

        ./gradlew -P=test install
        
        ./gradlew -P=util install
        
        ./gradlew -P=model install
        
        ./gradlew -P=database install
        
You now have the system built and you're ready to write an adapter.  Copy the template project from engine/adapters/template to an appropriate sub folder beneath the main adapters folder.  For instance, if you want to create an adapter for [St. Patrick's](http://www.stpatricksf.org/) in San Fransisco, CA you would first make a folder for the adapter and then copy the template project to the folderyou made:

        cd adapters
        
        mkdir -p north-america/us/ca/san-fransisco
        
        cp -R template/ north-america/us/ca/san-fransisco/st-patrick

If the copy worked correctly you will see the following files listed in your template folder:

        cd north-america/us/ca/san-fransisco/st-patrick

        ls -lhart
        total 40
        .gitignore
        .classpath
        ..
        src
        build.gradle
        README.md
        .project
        .


Next, edit the eclipse .project file and rename the project from template to st-patrick.  You can do this manually with vi, or use sed as below:

        cd north-america/us/ca/san-fransisco/st-patrick/
        
        sed -i 's/template/st-patrick/g' .project

Within your template folder there is a README.md.  Follow the steps within the templte README to complete your adapter.

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
