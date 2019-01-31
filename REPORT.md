# Report 

## In General
This app helps the user to keep track of his/her own challenges and visualizes the results. The user retrieves notivications when to enter their succes of the challenge and how they feel at that moment. Afterwards and during the challenge this data is visualized in a pie-chart (Library from AnyChart) and in a line Graph (library from GraphView). 

<img src="https://github.com/Quint-Langeveld/Project/blob/master/doc/Screenshot_20190130-163928.png" width="30%" height="30%" description="scherm1"/>

## Functionality

The first screen the user sees is the MainActivity. This is a modified App Theme throughout the app that removes the ActionBar at the top. There is a listView that uses a CursorAdapter to show just the ongoing challenges. When a right-swipeis detected via a GestureListener, the listView switches to another CursorAdapter that shows the finished challenges. Both adapters have a different onItemClick event. The ongoing challenges direct the user to the InputActivity to enter challengeValues that are stored inside a seperate SQLite database table. The onClick on finished challenges direct the user to the GraphActivity that shows the user all the data of a sertain challenge in two graphs. A pie-chart tht shows the success-rate of the challenge and a line-graph that shows the entered mood of the user troughout the time of the challenge. 

In the MainActivity the user can add a challenge to click on the floatingActionButton. Thereby the user gets directed to the EntryActivity. In here the user determines how long a the wanted challenge has to be followed and how often the user wants to be rememberd to it's challenge. This is done by Notifications. In the EntryActivity new instances of an AlarmManager (a reciever class) are created. The AlarmManager instances are activated by alarms and have two functions. First the notification and second to 'free' the InputActivity of a sertain challenge to be entered. This is because if this second function wasn't there, the user could finish a challenge in a minute of entering all the wanted datapoints. This function comes to play when the user clicks on a challenge in the ongoing listView. At first the app checks if the challenge is 'free' or 'locked'. This data is stored in the 'entries' table of the database. Then if the database notes 'free', the user is allowed to enter the InputActivity to fill in their mood. However, if the database notes 'locked', the user gets to see a pop-up message that tells them they have to wait a little longer. By entering the mood and succes in the InputActivity the challenge automatically sets to 'locked', so the AlarmManager has to unlock it when they recieve the next alarm. 


## Challenges

Major challenges were: 
- creating my own App Theme
- send notifications
- handling swipes


**Creating my own App Theme**: This took me several days after I figured out how everything was connected. Creating your own style/colors/string and then activating them in the Manifest file was terrible, but I managed to figure it out. This may had take a little less time, but I learned a lot from it and how Android Studio really works, so afterwards I am really glad is was a challenge for me. 

**Send Notifications**: I started with the wrong approach, namely an approach that was just compatible for API's above 26. Pritty quick I found a way that was compatible for as well above as below API's of 26. The 'notification manager' is now spread over two different classes and an Activity, but it works and I think there is no easier way to solve it. Not with the connection of the AlarmManager is is bound to. 

**Handling swipes**: It took me a real while to find the right swipe isteners, because it works with 'coordinates' on your screen, so there is no predefined method for swiping. And when I did find one I messed up with the OnItemCLickListener and the OnItemLongClickListener. Both are actually activated by a touch on the screen and returning a true or a false if they are activated of if the gestureListener has to go figuring out what kind of movement the user does with it's finger. Therefore I now understand the idear behind Click and Gesture Listeners and they work nicely despites each other. I guess there is also not a better way to handle the Listeners that I did, although it migth be a good try to just use the gesture listener, as well for the onItemLongClick, but then you have to link it to the listView and for now I don't know how. 






