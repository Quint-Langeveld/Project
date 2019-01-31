# My Process Book



## Week 1 (7-11 jan)
### day 2
- created README and DESIGN documents on GitHub 

### day 3
- Added the final design document:
<img src="https://github.com/Quint-Langeveld/Project/blob/master/doc/IMG_20190108_154433%20kopie.jpg" width="50%" height="50%"/>

### day 4
- The first code on paper

Started with really rough code, but made a lot of progress. 
The hardest parts were to think of the rigth types of variables and I actually changed then a lot. 
For example, how to store the length of a challenge. Especcially when the user wants to retreive ultiple notifications per day. 

### day 5
- Tried to reach a screen style without actionBar. 

This really took a long time, and I figured out almost everything on my one. I learnt a lot of these days, but they cost me valuable time as well. The hardest part was to create a style of my one that workes for every activity. I also tried the FullscreenActivity there, but that didn't turn out to be a succes. 

The eventual style became as follows:
```
<style name="empty" parent="Theme.AppCompat.Light.NoActionBar">
        <item name="colorPrimaryDark">@color/tintedBlue</item>
        <item name="android:backgroundTint">@color/tintedBlue</item>
        <item name="android:statusBarColor">@color/littleMoreBlue</item>
    </style>
```

## Week 2 (14-18 jan)
### day 6
Created the InputActivity with all it's functionality. That raised the question how the user wants to fill in their mood. I was doupting between a scale bar from 1 to 10, but then the difference between for example 6 and 7 isn't really that much. Actually I wanted to implement a circular scale bar as pictured below, but that turned out to be to difficult. 

<img src="https://github.com/Quint-Langeveld/Project/blob/master/doc/68747470733a2f2f7261772e6769746875622e636f6d2f4e6563617430722f5365656b436972636c652f6d61737465722f73616d706c652f5365656b436972636c652e706e67.png" width="50%" height="50%"/>

### day 7
I asked myself the next questions:
- How to organize the tables in the database?
- Which notificationManger do I have to use? 
- How to organize the adapters with ongoing challenges and finished challenges. 


I got to answer the first two today. I am going to use two tables. One table for all the challenges' names, periods and status and one table for input data of the challenges. This had to be a good and effective way to deal with the fact that for different challenges different timespans are needed and that I still am able to create a grapgh of the data.  

Also figuered out that the notification Manager I was trying to implement was wrong. I used one for API levels 26 or higher, but more on that tomorrow.

### dag 8
Switched to a notification manager that is compatible with both higher and lower API levels that 26. This caused me to implement two extra classe eventually, but for now it works with just one. 


### dag 9

Vandaag heb ik de onSwipeListener geinstaleerd. Dit is goed gegaan op het feit dat m'n onItemLongClickListener om list items te verwijderen niet meer reageert.

Daarnaast werken de knoppen aan de bovenkant van m'n scherm ook niet meer. Het is dus nog even verder uitzoeken hoe dit nou de juiste kant op moet gaan...



## Week 3 (21-25 jan)



## Week 4 (28-1)

### day 11
to do:
- in de input activity een knop maken naar 'view process'. Hiervoor dus ook de graph lib implementeren

- eindelijk de swipelistener fixen!!

- ongoing en finised knoppen fixen. nu crashed mn app nmlk.

gedaan:
- icoontjes ingesreld!
