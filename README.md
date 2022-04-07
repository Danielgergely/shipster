# Shipster

<img src="src/main/resources/static/images/shipster_yellow_black.svg"  width="300" height="300">

### Shipping made easy and fun
[Link to the deployed app](https://shipster-app.herokuapp.com/){:target="_blank"}

To run the application locally go to ***src/main/java/ch/shipster/ShipsterApplication*** and run the main class.

**IMPORTANT**
In ***src/resources/application.properties*** make sure that the correct database settings are active.
1. If you have a local postgres db running, check the connection credentials and uncomment line 21-26 (postgresql local database)
2. If you have no local database, uncomment lines 2-10 (in memory h2 database)
3. if you deploy to heroku, uncomment lines 13-18 (heroku postgresql database)

To access the running application go to [localhost port 8080](http//:localhost:8080) and you will be redirected to the home page.

After starting the application one needs to authenticate. To do that you can go to the register (sign up) page, register and
then use your credentials to log in.
