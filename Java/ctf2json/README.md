# CTF to JSON converter

## Requirements:

The java project is managed by Maven. Maven must be available in the shell.

## Setup (download required dependencies):

Run the tracecompass_plugin_downloader script to install tracecompass dependencies in m2 repo:
```
./tracecompass_plugin_downloader -i
```

## Import in Eclipse

Import the project as a Maven project
File -> Import -> Existing Maven project
Then right click on the project in the workspace -> Maven -> Update project

## Run from shell

In the same shell where we have added the maven module, we do the following:
```
cd .../<folder containing pom.xml>
mvn clean
mvn compile
mvn exec:java 
```