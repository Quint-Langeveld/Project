# Test it!


## Algemeen
**Een TestApp. De user kan een nieuwe challenge voor zichzelf aanmaken en aangeven hoe lang deze vol te houden en hoe de score bij te houden.**

```
Het doel van deze app is het onthouden van resultaten van bepaalde challenges en deze weergeven op een  
prettige manier.  
```


## De App

In het thuisscherm (scherm 1) van de app zit een listView met challenges waar de user mee bezig is. Er wordt aangegeven hoe de challenge heet, hoe lang deze duurt en de progressie van de challenge. Door middel van een 'floating action button' in scherm 1 wordt er een nieuwe challenge aangemaakt. Scherm 2 laat zien hoe deze actifity eruit ziet. Door naar rechts te swipen in scherm 1 kom je bij scherm 3. Hier zijn alle afgeronde challenges te zien. Door op een challenge te klikken kom je bij scherm 4. Hier is een pie-chart te zien die het succes van de challenge laat zien en een lijn-grafiek. Deze laat het 'gevoel' zien dat de user heeft ervaren tijdens de challenge. 

<img src="https://github.com/Quint-Langeveld/Project/blob/master/doc/Screenshot_20190130-163928.png" width="23%" height="23%" description="scherm1"/> <img src="https://github.com/Quint-Langeveld/Project/blob/master/doc/Screenshot_20190130-163823.png" width="23%" height="23%"/> <img src="https://github.com/Quint-Langeveld/Project/blob/master/doc/Screenshot_20190130-163940.png" width="23%" height="23%"/> <img src="https://github.com/Quint-Langeveld/Project/blob/master/doc/Screenshot_20190125-223320.png" width="23%" height="23%"/>

 
## Info
#### Data Scources 
The user's input is stored in a local SQLite database in two tables. One contains the names of the challenges etc. and the other contains the actual data values of the challenges. 

#### External components

##### Libraries
This app makes use of two external libraries. The first is AnyChart Library and computes the Pie chart (BRON: https://www.anychart.com). The second is a library from GraphView (BRON: http://www.android-graphview.org) and computes the line graph. Both libraries get their data from the local SQLite database to show their graphs with. 

##### Scripts
*Gesture Detection*: The implementation of a gesture detection in the MainActivity if from BRON: https://stackoverflow.com/questions/937313/fling-gesture-detection-on-grid-layout.
The script for the *Pop-up Messages* was retrieved from BRON: http://www.androiddom.com/2011/06/displaying-android-pop-up-dialog.html.

