This is a template project for building your own adapter.  Before trying to run this project
you should follow the steps in the engine README.md to build the other sub modules first.

1. (If you haven't already), find where your parish should be located beneath the adapters folder, and
copy this template project to the appropriate location
2. (If you haven't already), replace the word "template" in the .project file with your adapter's name.
3. Adjust the path in build.gradle so that it can reach the build.gradle file in the main gocatholicevents directory.  e.g. the path will probably end up being (apply from: '../../../../../../build.gradle').
4. At this point you can import the project into eclipse or favorite IDE.
5. Rename the src and test packages replacing the word "parish" with your parish's name.
6. Rename ParishAdapter and ParishAdapter test to match your parish's name
7. Fill out your unit test class.  You test class must inherit from the AbstractParserTest so that your adapter will plug in to the larger engine project.  The goal of your adapter class should be to populate the ChurchDetail instance for your parish.  The unit tests will verify that each part of the adapter is working correctly.  The adapter should use regular expressions to validate the accuracy of the expected information from the parish web site.  The unit tests are monitored to check for failures which signal parish web site changes, and that the adapter needs updating.
8. You can test your project with `gradle test`.  (If you don't have gradle installed, you can run gradlew from the root of the project directory with "<path to gce root>/gradlew test".  The output will be saved to build/churchDetail.xml.  This will ultimately be uploaded by the GCE engine to gocatholicevents.com.

When you're satisified with how your build/churchDetail.xml is being populated after running "gradle test", commit it to GitHub and send us a pull request.  If you have any questions, feel free to contact us at support at go catholic events.com



