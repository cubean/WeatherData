# A toy simulation of the environment

[![CircleCI](https://circleci.com/gh/cubean/WeatherData.svg?style=svg)](https://circleci.com/gh/cubean/WeatherData)

## Requirement

Create a toy simulation of the environment (taking into account things like atmosphere, topography, geography, oceanography, or similar) 
that evolves over time. 

Then take measurements at various locations and times, and have your program emit that data, as in the following:

 |Location   |Position          | Local Time          |Conditions  |Temperature| Pressure | Humidity|
 |-----------|---------|---------|---------|---------|---------|------|
 |Sydney   | -33.86,151.21,39   | 2015-12-23 16:02:12   |   Rain        |+12.5     | 1010.3    |  97|
 | Melbourne| -37.83,144.98,7   |2015-12-25 02:30:55     | Snow       | -5.3        |998.4    |  55|
 |Adelaide  |-34.92,138.62,48  |2016-01-04 23:05:37     | Sunny     |  +39.4     | 1114.1     | 12|

Submit your data to us in the following format

```
Sydney|-33.86,151.21,39|2015-12-23T05:02:12Z|Rain|+12.5|1004.3|97
Melbourne|-37.83,144.98,7|2015-12-24T15:30:55Z|Snow|-5.3|998.4|55
Adelaide|-34.92,138.62,48|2016-01-03T12:35:37Z|Sunny|+39.4|1114.1|12
```

Where

-	Location is an optional label describing one or more positions,
-	Position is a comma-separated triple containing latitude, longitude, and elevation in metres above sea level,
-	Local time is an ISO8601 date time,
-	Conditions is either Snow, Rain, Sunny,
-	Temperature is in °C,
-	Pressure is in hPa, and
-	Relative humidity is a %.

Your toy weather simulation should report data from a reasonable number of positions; 10±. 

The weather simulation will be used for games and does not need to be meteorically accurate, 
it just needs to be emit weather data that looks plausible to a layperson.

So far we have assumed that our game takes place on Earth, 
leading to the use of latitude and longitude for co-ordinates and earth-like conditions. 

If you choose to assume that the game takes place elsewhere, please document any corresponding changes to the output format.

## Run the program

Program running results:

```cmd
Sydney|-33.92,151.19,9|2015-12-23T05:02:12Z|Sunny|+25.1|1012.2|53
Melbourne|-37.82,144.98,86|2015-12-24T15:30:55Z|Sunny|+20.0|1003.0|71
Adelaide|-34.94,138.60,6|2016-01-04T12:35:37Z|Sunny|+21.7|1012.5|66
```

### Run jar package

Download the jar package first from: 
https://github.com/cubean/WeatherData/tree/master/jar/WeatherData-assembly-0.1.jar

- If you only installed java 8 in your environment

Go to the your local folder where the WeatherData-assembly-0.1.jar sits in, then run next command:

```cmd
java -jar WeatherData-assembly-0.1.jar
```

- If you also installed scala 2.12.x, you could run

```cmd
scala WeatherData-assembly-0.1.jar
```

### Run with the source code

You need to ensure sbt 1.2.x, scala 2.12.x and java 8 installed in your environment.

After you download the source code, you could run the program with sbt under the project folder.

```sh
> WeatherData/sbt run
```
Then will get next results:

```cmd
...

Output weather info:
Sydney|-33.92,151.19,9|2015-12-23T05:02:12Z|Sunny|+25.1|1012.2|53
Melbourne|-37.82,144.98,86|2015-12-24T15:30:55Z|Sunny|+20.0|1003.0|71
Adelaide|-34.94,138.60,6|2016-01-04T12:35:37Z|Sunny|+21.7|1012.5|66

...
```