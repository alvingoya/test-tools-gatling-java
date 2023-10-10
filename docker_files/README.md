# Base Docker Image

# Usage

Use image to run container

```
docker run -it --rm denvazh/gatling
```
 
Mount configuration and simulation files from the host machine and run gatling in interactive mode

```
docker run -it --rm -v /home/core/gatling/conf:/opt/gatling/conf \
-v /home/core/gatling/user-files:/opt/gatling/user-files \
-v /home/core/gatling/results:/opt/gatling/results \
denvazh/gatling
```

Use the `-e` switch to use JAVA_OPTS to pass parameters to gatling tests

```
docker run -e JAVA_OPTS="-Dusers=10" -it --rm denvazh/gatling
```
