
# Note Sharing Web Application
Introduction: This project would make use of the CS 601 Project 2-Inverted Index and Project 3-Publisher/Broker/Subscriber Model to build a web application which let the user publish the note of class and receive the note by the tags they add into their tag list of subscriber.

<br>

## **Basic Support Function and Pages**
* >**_Register Interface_**
    * allow user to register account
    * main register field(may modify): username, password, email
<br>

* >**_Login Interface_**
    * allow user to login
<br>

* >**_User Setting Interface_**
    * allow user to modify username, password, email
<br>

* >**_Search Interface_**
    * provide user a search box to search stock by term
    * return a list of stocks' name contain the term
    * click one single stock on the list will redirect to stock information page
    
<br>

* >**_Stock Information_**
    * provide one stock's specific informaiton: _price_, _from which exchange_, _price at open_, _day high_, _day low_, _52 week high_, _52 week low_, _day change_,_etc.._
    * provding buy/sell option for this specific stock, user have to enter buy/sell volume and buy/sell price, it will record into account portfolio
    * (option) still looking for API to display the chart of stock price...
    * (option) still looking for API to cover stock price in other currencies
    
<br>

* >**_Portfolio_**
    * display all the stocks user own(due to free API request limit, only can return 5 stocks' information for each request)
    * each stock information contain: cost price, current price, gain/loss price, gain/loss percentage
    * Porfolio over all information: total assets value, toal gain/loss percentage
    
<br>

* >**_Ranking(optional, if have enough time)_** 
    * A list of users' ranking by portfolio performance; click one user, can see which kind of stock that user is investing
    
<br>  

>**Additional Information:**
1. This web application don't support actual buy/sell behavior. It is just an account book that you can record how many share of stock user buy/sell at a certain price, help user to see how much gain/loss users are investing. It is more like a practice machine and  for investment, to test out if users' decision 

2. The total value users have is not limited. Users can record any amount shares of stock at any prices.

<br>
<br>

## **Tools and development environment**

**Main language**: _Javascript_

**Backend**: _Nodejs, Expressjs, JWT Token_

**Frontend**: _React, Axios, Redux, React-Boostrap/Ant Design/Font Awesome_

**Database**: _MangoDB_

**Other Resources**: [World Trading Data](https://www.worldtradingdata.com/)    <br><br>
API will be used for access stock information data. However, Due to free API limited access, each API request will be able to ask max 5 stocks information, users will be able to only have max 5 stocks in their portfolio.
