```
*************************************************************
*                                                           *
*       ________  __    __  ________    ____       ______   *
*      /_/_/_/_/ /_/   /_/ /_/_/_/_/  _/_/_/_   __/_/_/_/   *
*     /_/_____  /_/___/_/    /_/    /_/___/_/  /_/          *
*    /_/_/_/_/   /_/_/_/    /_/    /_/_/_/_/  /_/           *
*   ______/_/       /_/    /_/    /_/   /_/  /_/____        *
*  /_/_/_/_/       /_/    /_/    /_/   /_/    /_/_/_/ . io  *
*                                                           *
*************************************************************
```

# Sytac Java Exercise #

This development test is used as part of Sytac selection for Java developers. You are requested to develop a simple
application that covers all the requirements listed below. To have an indication of the criteria that will be used to
judge your submission, all the following are considered as metrics of good development:

+ Correctness of the implementation
+ Decent test coverage
+ Code cleanliness
+ Efficiency of the solution
+ Careful choice of tools and data formats
+ Use of production-ready approaches

While no specific time limit is mandated to complete the exercise, you will be asked to provide your code within a given
deadline from Sytac HR. You are free to choose any library as long as it can run on the JVM.

## Task ##

We would like you to write code that will cover the functionality explained below and provide us with the source,
instructions to build and run the application, as well as a sample output of an execution:

+ Connect
  to [Twitter Streaming API 1.1](https://developer.twitter.com/en/docs/twitter-api/v1/tweets/filter-realtime/overview)
    * Use the following values:
        + Consumer Key: `RLSrphihyR4G2UxvA0XBkLAdl`
        + Consumer Secret: `FTz2KcP1y3pcLw0XXMX5Jy3GTobqUweITIFy4QefullmpPnKm4`
    * The app name will be `java-exercise`
    * You will need to login with Twitter
+ Filter messages that track on "bieber"
+ Retrieve the incoming messages for 30 seconds or up to 100 messages, whichever comes first
+ Your application should return the messages grouped by user (users sorted chronologically, ascending)
+ The messages per user should also be sorted chronologically, ascending
+ For each message, we will need the following:
    * The message ID
    * The creation date of the message as epoch value
    * The text of the message
    * The author of the message
+ For each author, we will need the following:
    * The user ID
    * The creation date of the user as epoch value
    * The name of the user
    * The screen name of the user
+ All the above information is provided in either Standard output, or a log file
+ You are free to choose the output format, provided that it makes it easy to parse and process by a machine

### __Bonus points for:__ ###

+ Keep track of messages per second statistics across multiple runs of the application
+ The application can run as a Docker container

## Provided functionality ##

The present project in itself is a [Maven project](http://maven.apache.org/) that contains one class that provides you
with a `com.google.api.client.http.HttpRequestFactory` that is authorised to execute calls to the Twitter API in the
scope of a specific user. You will need to provide your _Consumer Key_ and _Consumer Secret_ and follow through the
OAuth process (get temporary token, retrieve access URL, authorise application, enter PIN for authenticated token). With
the resulting factory you are able to generate and execute all necessary requests. If you want to, you can also
disregard the provided classes or Maven configuration and create your own application from scratch.

## Delivery ##

You are assigned to you own private repository. Please use your own branch and do not commit on master. When the
assignment is finished, please create a pull request on the master of this repository, and your contact person will be
notified automatically.

# Solution

A simple command line application will fetch from provided url within a limit of 30 seconds or 100 tweets. Tweets will
then be sorted as required and output in a text file along with another file with statistics.

## Requirements :

* Java11
* Gradle
* Docker

## Getting Started

To create the jar file

```bash
./gradlew clean sytac
```

## Run

We can run the application as a jar file or inside a docker container.
In any case, once the application has started, we need to browse to the url provided, copy the token and paste it back in the command line to get the authorization.

### As a jar file

Every time we want to run the application:

```bash
java -jar ./build/libs/sytac.jar 
```

### Inside a docker container

Build a docker image:

```bash
docker build -t . sytac
```

We can create and run our container:

```bash
docker run --name=sytac -i -v /tmp:/tmp -p 8080:8080 sytac
```

We are sharing with the container the tmp folder, but we can use other folders.
As the container will stop after the execution, we need to start it again for every run:

```bash
docker start sytac -i
```

## Output

Either inside or outside the container, the application will append to output and statistics files in the \tmp folder.

An output example is:

```json
[
  {
    "tweetAuthor": {
      "id": 1354814453330530306,
      "created_at": "Thu Jan 28 16:32:04 +0100 2021",
      "name": "Janna ♥︎⨾",
      "screen_name": "chaeskeyk"
    },
    "tweetList": [
      {
        "id": 1471211675542245376,
        "creationDate": "Wed Dec 15 21:12:51 +0100 2021",
        "messageText": "'ika nga ni justin bieber \"some people teach you a million lessons. all that I learned,it wasn't my turn,its wasn't… https://t.co/Zs6wZTzl3Z"
      }
    ]
  }
]
```

A statistic example is:

```text
Execution of Wed Dec 15 21:25:55 CET 2021 found tweets 10 in 30 seconds
```