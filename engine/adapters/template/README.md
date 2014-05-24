This is a template project for building your own adapter.  Before trying to run this project
you should follow the steps in the engine README.md to build the other sub modules first.
1. If you haven't already, find where your parish should be located beneath the adapters folder, and
copy this template project to the appropriate location
2. If you haven't already, replace the word "template" in the .project file with your adapter's name.
3. Adjust the path in build.gradle so that it can reach the build.gradle file in the main gocatholicevents directory (add "../"'s to the path
4. Rename the src and test packages replacing the word "parish" with your parish's name
5. Rename ParishAdapter and ParishAdapter test to match your parish's name
6. Fill out your first unit test.  It's important to keep the same structure of the template unit tests (inheriting from the AbstractParserTest) so that your adapter will plug in to the larger engine project
7. Write the code in the adapter class to get your unit test to pass.  
8. Repeat steps 5&6 until all unit tests are passing and your ChurchDetail object is returning all of the
information you want to index.  You can test your project with `gradle test`.  (If you don't have gradle installed, you can run gradlew from the root of the project directory with something like "../../<more ../>/gradlew test".  The output will be saved to build/churchDetail.xml.  This file is what will ultimately be uploaded by the GCE engine to gocatholicevents.com.

When you're satisified with everything, commit it to GitHub and send us a pull request.  If you have any questions, feel free to contact us a support at go catholic events.com



