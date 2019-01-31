# My Process Book



## Week 1 (7-11 jan)
### day 2
- created README and DESIGN documents on GitHub 

### day 3
- Added the final design document to DESIGN.md:
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

<img src="https://github.com/Quint-Langeveld/Project/blob/master/doc/Screenshot_1548940990.png" width="20%" height="20%"/>


### dag 9
- Installed an onSwipeListener

With help from BRON: https://stackoverflow.com/questions/937313/fling-gesture-detection-on-grid-layout, I managed to implement a way to redirect a swipe movement to the angoing and finished adapters. 

At first the gesture detection overwrote the onItemLongClickListener for deleting items, but with a simple adjustment it went all good. 

### dag 10 
- improved old functionality

The swipeListener, database tables and App Theme were a bit strange, so I improved them. Especially layout wise. Took the time for a better design. 

## Week 3 (21-25 jan)

### dag 11
- Set an App icon. 

This ectually turned out to be extremely simple, but took me a full morning to find out. But finally choosen an icon:

<img src="https://github.com/Quint-Langeveld/Project/blob/master/doc/winner.png" width="20%" height="20%"/> <img src="https://github.com/Quint-Langeveld/Project/blob/master/doc/Screenshot_1548941767.png" width="20%" height="20%"/>
retrieved from the internet, but I can't find the creator for now...


### dag 12
- Startted with the GraphActivity

Found out the best Library to use for my data. 
Found out that 'AnyChart' for the pie chart is going to be the best choice and 'GraphView' for the ling graph.  

### dag 13


### dag 14


### dag 15

## Week 4 (28-1)

### day 16
to do:
- in de input activity een knop maken naar 'view process'. Hiervoor dus ook de graph lib implementeren

- eindelijk de swipelistener fixen!!

- ongoing en finised knoppen fixen. nu crashed mn app nmlk.

