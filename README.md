# Scala Project : Scal'house

## Members
Maxime Mouffron, François Mutti, Antoine Pigny, Roquier Cantin 

## Our project
Our project is an application that simulates a self-sufficient energy house. 
The goal is to integrate a weather API and use its data to make the house 
interact with its virtual environment. Consequently, the house will be able 
to generate energy based on the weather conditions and will also consume 
resources over time.

## How to run the app
- Simply use "sbt run"
- To run tests, use "sbt test"
- Don't forget to have the libraries in the build.sbt file correctly imported
on your side!

## What does it do
The app contacts the open-meteo API every 30 seconds. After that, it gathers
information such as the wind speed or the cloud coverage, which then affects
the house's resources. The app processes this data and displays the status 
of the house's resources. With this setup, we can observe how the house 
behaves over time.

## How does it work
1. We send a request to the open-meteo API (fetchData(), line 18 in "main.scala").
This call is performed every 30 seconds.
2. The API returns a text in JSON format, that we then deserialize into our own 
objects, represented by case classes. The main case class object is 
"WeatherLocationData.scala".
3. We update our self-sufficient energy house with the new data. This data is 
currently used to define how much electricity should be produced, how much water
is stored etc... 

## How is the data used
The data we queried at step 2 is used within the "AutonomousHome.scala" class.
We perform simple calculations (just for the sake of it) within this class, to show
the implementation of an actual process that could be really useful. Finally, using 
all the results from the previous calculations, we define how much energy AND water 
is used and produced, and then update the house inventory.

## Problems we encountered
We encountered many problems during this project. Most of them weren't due to some
Scala constraints. It was mainly due to the fact that we didn't practice enough. 
Our poor practical skills didn't stand a chance against the ZIO Framework. Here
are some examples.
- For Comprehensions : Even though we did understand this tool in class, it took us
a really long time to understand how they were used with ZIO's system of "mapZIO", 
return types etc...
- ZIO : This framework, even though it has a great documentation, 
isn't beginner-friendly at all. We got stuck as soon as we started using it, even
with the documentation right before our eyes.
- Some bad Scala jokes : Something we didn't know is that Scala works differently
depending on where the file is situated. When our opaque types were in the "Main"
object, the implicits conversions were done automatically. However, when we 
separated everything in the adequate files and classes / objects, the implicit 
conversions weren't done anymore. This resulted in a broken code, until we found
out about the problem and looked for the solution.