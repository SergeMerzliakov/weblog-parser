# Weblog Parser
A **very basic** web log parser, which reads log entries and extracts the IP address
and URL values. Statistics and counts are then available.

It is written in Kotlin and uses gradle 6 as the build tool.


## Data Format of Log

Each line represents a single http request

    177.71.128.21 - - [10/Jul/2018:22:21:28 +0200] "GET /intranet-analytics/ HTTP/1.1" 200 3574 "-" "Mozilla/5.0 (X11; U; Linux x86_64; fr-FR) AppleWebKit/534.7 (KHTML, like Gecko) Epiphany/2.30.6 Safari/534.7"
    168.41.191.40 - - [09/Jul/2018:10:11:30 +0200] "GET http://example.net/faq/ HTTP/1.1" 200 3574 "-" "Mozilla/5.0 (Linux; U; Android 2.3.5; en-us; HTC Vision Build/GRI40) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1"


## Setup

Prerequisites:
  * Java 8+
  

### Building the project

A gradlew wrapper is deployed inside the repository and will download all relevant
dependencies.

To build and run all unit tests

    gradlew build
    
To just run the unit tests

    gradlew test

## Execution

The unit tests inside the **org.weblog.LogProcessorTest** classes highlight the basic statistics

The sample log file to be processed is inside the repo at:

    src/test/resources/programming-task-example-data.log



## Limitations
* Limited parsing of log file format, using regular expressions to extract key values, rather
 than a more complete (grammer based) parser
* Needs better processing of URL to distinguish valid URLs (full vs paths only)
* Better test coverage - current tests highlight the kinds of tests required (positive, negative and edge cases)
