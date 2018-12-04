
# Note Sharing Web Application
Introduction: This project would make use of the CS 601 Project 2-Inverted Index and Project 3-Publisher/Broker/Subscriber Model to build a web application which let the user publish the note of class and receive the note by the tags they add into their tag list of subscriber.

<br>

## **Basic Support Function and Pages**
* >**_Register Interface_**
    * allow user to register account
    * main register field(may modify): username, password
<br>

* >**_Login Interface_**
    * allow user to login
<br>

* >**_User Setting Interface_**
    * allow user to modify username, password
    * allow user to add more accept tags into their tag box to receive the notes which have the tags
<br>

* >**_Search Interface_**
    * provide user a search box to search note by term
    * return a list of notes' name contain the term
    * click one note will lead to the note page
    
<br>

* >**_Publish Notes_**
    * allow user to publish a note by entering the body of the note and add tags
    * the logic engine will also automatically generate the additional tags by the following logic:
    * note -> inverted index -> term frequency hashMap -> select the term which appeal most frequently <br>
      -> select the term which length is larger than 4(may be changed) -> add into the tags
    * send to all the subscribers whose tag box have one of the tags by the publisher-broker-subscriber engine.
    
<br>

* >**_Note Box_**
    * main page will remind the user if a new note is sent into his or her box
    * display all the notes writen by the user
    * display all the notes came from the others
    
 <br>

* >**_Data Base_**
    * use mysql to store all the user settings and tag box
    * also store all the notes (not sure if mysql can store so long text, may be changed)
    
<br>

* >**_Optional Features (if enough time)_** 
    * note ranking system
    * invited register system
    * follower system
    * pay to get the note by using the credit from publishing notes, the higher score your note get, the more credit you can get
    
<br>  
<br>
<br>

## **Tools and development environment**

**Main language**: _Java_

**System Engine**:  _Inverted Index, Publisher-Broker-Subscriber Model_

**Database**: _mysql_
