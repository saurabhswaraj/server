# server

## to build

> You need to have Java Installed
>
Go inside the server project
```
./gradlew build
```

```shell
java -classpath build/libs/server-1.0-SNAPSHOT.jar  org.arcticwolf.Main
```

## config File Location
```shell
server/src/main/config/config.properties
```

### config properties default
```shell
output.dir=src/main/resources
server.port=8282
```
**output.path is the path where .properties file will be created**

**server.port is port on which server will listen to**

## Default Setup
> The directory src/main/resources is already created
> with a dummy file so that I can commit folder structure in git


