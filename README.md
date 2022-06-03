# Shipster

<img src="src/main/resources/static/images/shipster_yellow_black.svg"  width="300" height="300">

### Shipping made easy and fun
[Link to the deployed app](https://shipster-app.herokuapp.com/)

To run the application locally:

1: download and install [Docker Desktop](https://www.docker.com/products/docker-desktop/) and make sure it is running.

2: Open the project and select the DevUp configuration (you might have to select docker from the dropdown in the config menu).

3: Go to localhost port 8080 to access the application

After starting the application to access certain endpoints, one needs to authenticate. To do that you can go to the register (sign up) page, register and
then use your credentials to log in.

A user automatically gets the user role "USER" assigned. This doesn't give access to the admin page. To access the admin page use the admin account:

Username: admin

Password: password

Have fun!

### Detailed description of all pages

#### Home page

This is the start page. Accessible without login. The "Shop with us" button leads to the shop page.

The navbar contains a list of the available pages, along with login, signup. If user is signed in then also a link to the admin interface is shown. This can only be accessed, if a user has and admin role, otherwise a 403-Permission denied error is shown.

The footer also contains some additional information and links.


#### About page
This page presents some information about Shipster and looks quite cool ;).


#### Team page
Here the Shipster team is presented with wonderful pictures and some inspiring text.

#### Shop page
This page is where all the shipster products are presented. We have a list of 12 articles. Each article can be added to the basket (cart icon), liked (heart icon), viewed separately by clicking on the image or the info button. Additionally, the truck icon shows how much pallet space this article needs, as well as the max stack number.

#### Article page
Here the details of an article are shown, and can be added to the basket.

#### Profile page
This page can be accessed by clicking on the profile icon. Here one can change their user details, and even change their password. The shopping basket of the logged in user can also be accessed from this page as well as the previous orders (if present).

#### Basket page
This page displays all articles in the basket along with the prices. The user can add/remove articles to the basket as well as change the shipping provider with one click.

#### My Orders page
The order history of the user is displayed here. Each order can be viewed separately, and a recipe can be generated.

#### Admin
<ins>Only accessible with admin role!</ins>
This page leads to an overview page where the administrator can either select the user or orders page. The navigation also changes to a different color and links.

#### Admin - users
Here a list of all users are presented. By clicking on a row, the admin can change user information, update user details, or change roles and even delete the user easily and efficiently.

#### Admin - orders
Here a list of all orders are shown sorted according to order date. By clicking on a row, an administrator can view the order, and change the status of an order, as well as delete it.

