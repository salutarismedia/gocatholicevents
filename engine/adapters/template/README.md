This is a template project for building your own adapter.  Before trying to run this project
you should run a "gradle install" on each of the other projects (common, database, common-model,
and comment-test as one or more of those can be dependencies of an adapter template)
1. Find where your parish should be located beneath the adapters folder, and
copy this template project to the appropriate location
2. Adjust the path in build.gradle so that it can reach the build.gradle file in common-gradle (add 
"../"'s to the path
3. Rename the src and test packages replacing the word "parish" with your parish's name
4. Rename ParishAdapter and ParishAdapter test to match your parish's name
5. Fill out your first unit test.  It's important to keep the same structure of the template unit tests (inheriting from the AbstractParserTest) so that your adapter will plug in to the larger engine project
6. Write the code in the adapter class to get your unit test to pass
7. Repeat steps 5&6 until all unit tests are passing and your ChurchDetail object is returning all of the
information you want to index

