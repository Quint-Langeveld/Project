# Project Proposal


## Algemeen
**Ik ga een TestApp maken. Hierin kan de user een nieuwe challenge voor zichzelf aanmaken en aangeven hoe lang deze vol te houden en hoe de score bij te houden.**

```
Het doel van mijn app is het onthouden van resultaten van bepaalde challenges en deze weergeven op een  
prettige manier.  
```


## App Structuur
<img src="https://github.com/Quint-Langeveld/Project/blob/master/doc/IMG_20190107_111622.jpg" width="100%" height="100%"/>

## Info
#### Data Scources 
De data komt van de user. Dit zullen waarderingen zijn in het geval van een integer van 1 tot 10. Deze waardes zullen uiteindelijk in een grafiek weeregegeven moeten worden. Dit zal met een externe library gedaan worden (nog uit te zoeken).


#### External components
Alle data zal opgeslagen worden in een lokale bibliotheek. SQLite lijkt mij gewoon geschikt.


#### Similar Apps
De apps die het meest hierop lijken bevatten 1 vaste challenge. Niet roken/niet drinken etc. Deze app zal dus een grotere variatie bevatten aan het weergeven van data, aangezien verschillende challeges verschillende resultaten zullen bevatten!


#### Hardest Parts
Het lastigste zal het weergeven van de resultten zijn. Allereerst met een grafiek en daarnaast zal de grafiek steeds een verschillend aantal waardes uit een SQLite database moeten halen. Hier verwacht ik de meeste moeilijkheden. 
