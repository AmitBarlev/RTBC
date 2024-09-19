# RTBC
Reactive Text Based Calculator

# Run
## Pre Requisites:
* Java 17+
* Docker (Optional)

## Docker Instructions
### Build
docker build -t <tag name> -f <docker file location> .

### Run
docker run -d -p 8080:8080 <tag name>

## Swagger
### <a href="http://localhost:8080/v3/webjars/swagger-ui/index.html">UI</a>
Since Json doesn't handle newline that well, make sure to use '\n'
