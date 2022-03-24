# Shipster

## Shipping made easy and fun

To run the application go to app/src/main/java/ch/shipster/ShipsterApplication and run the main class.

After starting the application one needs to authenticate. There are three users with different roles. 
test testpassword
developer developerpassword
admin adminpassword

At the moment not much configuration is made for the different roles. All users can access all endpoints, 
except localhost/api/v1/users/{userid} which can only be accessed by the admin user.


Spring security basic course https://amigoscode.com/courses/728126/lectures/13124056 (login required)
