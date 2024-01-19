# Scala Project : Scal'house

## Members
Maxime Mouffron, François Mutti, Antoine Pigny, Roquier Cantin 

## What's the project ?
It's an application that will simulate a self-sufficient energy house. The goal is to integrate a weather API and use its data to make the house interact with its virtual environment. Consequently, the house will be able to generate energy based on the weather conditions and will also consume resources over time.

## How does it works
The app contacts the API every 30 seconds. After that, it gathers information like wind speed or cloud coverage, which then affects the house's resources. The app processes this data and displays the status of the house's resources. With this setup, we can observe how the house behaves over time.

## How to run the app
- Simply use sbt run
- To run tests, use sbt test
