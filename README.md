# PKI Scheduler

PKI Scheduler is an application designed to simplify building schedules. It includes options to import data, edit data, quickyly view data, edit rules for building schedules, and to export the final, optimized schedule.

The application is accessible through the web interface.

The main screen of the application allows the user to import data and assign rooms to courses. The edit screen allows the user to make changes to the imported data. The room by room view allows the user to view the courses assigned to rooms, and finally the export view allows the user to export the data to a CSV file. 

## Release Notes

Release 0.0

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

Release 0.3

* Added ability to assign rooms to courses
* Added ability to log changes in UI
* Added client specified fields to UI

Release 0.4 

* Improved algorithm which allocates rooms for courses. 
* Improved data import feature
* Added audit log export feature
* Added CSV export feature

Release 0.5 (Latest)

In this latest release of the PKI Scheduler, the team has worked to improve the algorithm which allocates courses for 
rooms. The classroom scheduling algorithm is capable of handling cross listed courses, avoiding conflicts, and 
it also will disregard online courses which do not need to be scheduled in a room. Additionally, the audit log was updated
and now serves as a system event logger which records errors and schedule changes in the system for later review. 
Finally, the UI has been updated in each view with a grid to allow the grid to be sorted by any combination of fields. 

* Completed audit log
* Improved algorithm which allocated rooms for courses
* Added grid multi-sorting capability


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

Once the WAR file is built, it can be deployed to the target environment. 

## Project structure

- `MainLayout.java` in `src/main/java` contains the navigation setup (i.e., the
  side/top bar and the main menu). This setup uses
  [App Layout](https://vaadin.com/docs/components/app-layout).
- `views` package in `src/main/java` contains the server-side Java views of your application.
- `views` folder in `frontend/` contains the client-side JavaScript views of your application.
- `themes` folder in `frontend/` contains the custom CSS styles.

## Additional Branches 

1. Preliminary - Branch used for initial skeleton code. This branch is not up to date and should not be used. 
2. CourseTest - Branch used for testing improved import feature. 
3. Export - Branch used for testing CSV export feature.
