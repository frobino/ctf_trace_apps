# CTF to JSON converter

## Requirements:

The java project is managed by Maven. Maven must be available in the shell.

## Setup (download required dependencies):

Run the tracecompass_plugin_downloader script to install tracecompass dependencies in m2 repo:
```
./tracecompass_plugin_downloader -i
```

If that does not work, download the necessary deps and install manually after downloadig from [eclipse][eclipse-repo]:

```
mvn install:install-file -Dfile=org.eclipse.tracecompass.common.core_5.2.0.202312190935.jar -DgroupId=org.eclipse.mytracecompass -DartifactId=org.eclipse.tracecompass.common.core -Dversion=1.0.0 -Dpackaging=jar
mvn install:install-file -Dfile=org.eclipse.tracecompass.ctf.core_4.4.0.202312190935.jar -DgroupId=org.eclipse.mytracecompass -DartifactId=org.eclipse.tracecompass.ctf.core -Dversion=1.0.0 -Dpackaging=jar
mvn install:install-file -Dfile=org.eclipse.tracecompass.ctf.parser_1.1.1.202312190935.jar -DgroupId=org.eclipse.mytracecompass -DartifactId=org.eclipse.tracecompass.ctf.parser -Dversion=1.0.0 -Dpackaging=jar
```

If we want to use State Providers:

```
mvn install:install-file -Dfile=org.eclipse.tracecompass.tmf.core_9.2.0.202312190935.jar -DgroupId=org.eclipse.mytracecompass -DartifactId=org.eclipse.tracecompass.tmf.core -Dversion=1.0.0 -Dpackaging=jar
mvn install:install-file -Dfile=org.eclipse.tracecompass.statesystem.core_5.3.0.202312190935.jar -DgroupId=org.eclipse.mytracecompass -DartifactId=org.eclipse.tracecompass.statesystem.core -Dversion=1.0.0 -Dpackaging=jar
mvn install:install-file -Dfile=org.eclipse.tracecompass.tmf.ctf.core_4.5.1.202312190935.jar -DgroupId=org.eclipse.mytracecompass -DartifactId=org.eclipse.tracecompass.tmf.ctf.core -Dversion=1.0.0 -Dpackaging=jar
```

So, the installed jars are:
```
org.eclipse.tracecompass.common.core_5.2.0.202312190935.jar  org.eclipse.tracecompass.statesystem.core_5.3.0.202312190935.jar
org.eclipse.tracecompass.ctf.core_4.4.0.202312190935.jar     org.eclipse.tracecompass.tmf.core_9.2.0.202312190935.jar
org.eclipse.tracecompass.ctf.parser_1.1.1.202312190935.jar   org.eclipse.tracecompass.tmf.ctf.core_4.5.1.202312190935.jar
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

[eclipse-repo]:https://download.eclipse.org/tracecompass/stable/repository/plugins/
