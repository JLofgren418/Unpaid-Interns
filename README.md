# PKI Scheduler

PKI Scheduler is an application designed to simplify building schedules. It includes options to import data, edit data, edit rules for building schedules, and to export the final, optimized schedule.

The application is accessible through the web interface.

The main screen of the application allows the user to import data and assign rooms to courses. The edit screen allows the user to make changes to the imported data. The room by room view allows the user to view the courses assigned to rooms, and finally the export view allows the user to export the data to a CSV file. 

## Release Notes

Release 0.0

This release is more developed than what was originally planned in the original project plan. Our original goal was to simply have a basic test function running, but have managed to gain ground on the database and UI sections of the application.

UI

* Preliminary UI design utilizing vaadin
* Added ability to upload local files into the application
* Added page for importing schedule
* Added page for exporting working schedule
* Added page for editing working schedule
* Created ability to sort course list by title, day, time, status (room assigned/room not assigned), and current room
* Added ability to search for course
* Database
* Created preliminary database for testing
* Created MYSQL server for use with public application

Release 0.2

* Added room by room view
* Added ability to parse CSV files
* Updated user interface

## Running the application

The project is a standard Maven project. To run it from the command line,
type `mvnw` (Windows), or `./mvnw` (Mac & Linux), then open
http://localhost:8080 in your browser.

You can also import the project to your IDE of choice as you would with any Maven project.

## Deploying to Production

To create a production build, call `mvnw clean package -Pproduction` (Windows),
or `./mvnw clean package -Pproduction` (Mac & Linux).
This will build a WAR file with all the dependencies and front-end resources,
ready to be deployed. The file can be found in the `target` folder after the build completes.

Once the WAR file is built, you can run it using
`java -jar target/flowcrmtutorial-1.0-SNAPSHOT.jar`

## Project structure

- `MainLayout.java` in `src/main/java` contains the navigation setup (i.e., the
  side/top bar and the main menu). This setup uses
  [App Layout](https://vaadin.com/docs/components/app-layout).
- `views` package in `src/main/java` contains the server-side Java views of your application.
- `views` folder in `frontend/` contains the client-side JavaScript views of your application.
- `themes` folder in `frontend/` contains the custom CSS styles.

## Additional Branches 
The alternative branch for this project is called Preliminary Which for now is only slightly behind main but is for testing as well as other odds and ends.
