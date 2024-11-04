# 3. semester skriftlig eksamen i Back-end Dokumentation - Jenny J. L. Nielsen, 04-11-2024

### Task 1.2
Jeg har brugt startkode fra mock exam 2 - medical clinic, der bl.a. indeholder HibernateConfig, ApplicationConfig, Security-package og Utils. Jeg har bagefter opsat en database i Postgres - trip_planning,
og har ædret db_name i config.properties til det, samt forbundet databasen til IntelliJ. Nu skal jeg så begynde at lave nye entiteter, der skal erstatte entiteterne fra min startkode.


### Task 3.3.2 & 3.3.3 - test af endpoints i trip.http

POST http://localhost:7070/api/trips/populate

HTTP/1.1 201 Created
Date: Mon, 04 Nov 2024 11:19:21 GMT
Content-Type: application/json
Content-Length: 18

Database populated

--------------------------

GET http://localhost:7070/api/trips

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:20:07 GMT
Content-Type: application/json
Content-Encoding: gzip
Content-Length: 498

```json
[
{
"id": 1,
"startTime": [
10,
0
],
"endTime": [
12,
0
],
"startPosition": "Budapest",
"name": "Sightseeing",
"price": 150.99,
"category": "CITY",
"guide": {
"id": 1,
"firstName": "Gertha",
"lastName": "Nerthoosen",
"email": "guidegertha@localtours",
"phone": "2375790",
"yearsOfExperience": 4,
"trips": []
}
},
{
"id": 2,
"startTime": [
14,
0
],
"endTime": [
16,
0
],
"startPosition": "Balaton",
"name": "Sailing",
"price": 200.99,
"category": "LAKE",
"guide": {
"id": 2,
"firstName": "Hans",
"lastName": "Müller",
"email": "guidehans@globaltours",
"phone": "3257880020",
"yearsOfExperience": 2,
"trips": []
}
},
{
"id": 3,
"startTime": [
8,
0
],
"endTime": [
20,
0
],
"startPosition": "Zakopane",
"name": "Skiing",
"price": 300.99,
"category": "SNOW",
"guide": {
"id": 3,
"firstName": "Maria",
"lastName": "Garcia",
"email": "guidemaria@localtours",
"phone": "9862048576",
"yearsOfExperience": 10,
"trips": []
}
},
{
"id": 4,
"startTime": [
10,
0
],
"endTime": [
15,
0
],
"startPosition": "Barcelona",
"name": "Beach",
"price": 250.99,
"category": "BEACH",
"guide": {
"id": 4,
"firstName": "Józef",
"lastName": "Kowalski",
"email": "guidejozef@globaltours",
"phone": "3257880020",
"yearsOfExperience": 5,
"trips": []
}
},
{
"id": 5,
"startTime": [
10,
0
],
"endTime": [
18,
0
],
"startPosition": "Rome",
"name": "Sightseeing",
"price": 150.99,
"category": "CITY",
"guide": {
"id": 1,
"firstName": "Gertha",
"lastName": "Nerthoosen",
"email": "guidegertha@localtours",
"phone": "2375790",
"yearsOfExperience": 4,
"trips": []
}
},
{
"id": 6,
"startTime": [
8,
0
],
"endTime": [
18,
0
],
"startPosition": "Las Palmas",
"name": "Tour around Gran Canaria",
"price": 150.99,
"category": "SEA",
"guide": {
"id": 2,
"firstName": "Hans",
"lastName": "Müller",
"email": "guidehans@globaltours",
"phone": "3257880020",
"yearsOfExperience": 2,
"trips": []
}
},
{
"id": 7,
"startTime": [
10,
0
],
"endTime": [
16,
0
],
"startPosition": "Tatras",
"name": "Hiking",
"price": 150.99,
"category": "FOREST",
"guide": {
"id": 3,
"firstName": "Maria",
"lastName": "Garcia",
"email": "guidemaria@localtours",
"phone": "9862048576",
"yearsOfExperience": 10,
"trips": []
}
}
]
```

--------------------------

GET http://localhost:7070/api/trips/4

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:21:21 GMT
Content-Type: application/json
Content-Length: 268

```json
{
"id": 4,
"startTime": [
10,
0
],
"endTime": [
15,
0
],
"startPosition": "Barcelona",
"name": "Beach",
"price": 250.99,
"category": "BEACH",
"guide": {
"id": 4,
"firstName": "Józef",
"lastName": "Kowalski",
"email": "guidejozef@globaltours",
"phone": "3257880020",
"yearsOfExperience": 5,
"trips": []
}
}
```

--------------------------

POST http://localhost:7070/api/trips

HTTP/1.1 201 Created
Date: Mon, 04 Nov 2024 11:21:48 GMT
Content-Type: application/json
Content-Length: 140

```json
{
"id": 8,
"startTime": [
11,
0
],
"endTime": [
19,
0
],
"startPosition": "Seoul",
"name": "Hongdae Shopping",
"price": 240.59,
"category": "CITY",
"guide": null
}
```

--------------------------

PUT http://localhost:7070/api/trips/4

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:22:12 GMT
Content-Type: application/json
Content-Length: 288

```json
{
"id": 4,
"startTime": [
21,
0
],
"endTime": [
23,
0
],
"startPosition": "Reykjavik",
"name": "Spotting Northern Lights",
"price": 300.59,
"category": "FOREST",
"guide": {
"id": 4,
"firstName": "Józef",
"lastName": "Kowalski",
"email": "guidejozef@globaltours",
"phone": "3257880020",
"yearsOfExperience": 5,
"trips": []
}
}
```

--------------------------

DELETE http://localhost:7070/api/trips/4

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:37:48 GMT
Content-Type: application/json
Content-Length: 12

Trip deleted

--------------------------

PUT http://localhost:7070/api/trips/8/guides/4

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 11:41:24 GMT
Content-Type: application/json
Content-Length: 19

Guide added to trip


### Task 3.3.5 - Why do we suggest a PUT method for adding a guide to a trip instead of a POST method?
Fordi vi gerne vil opdatere en eksisterende trip, og ikke oprette en ny trip. 
Således kan vi også bevare den samme guide i den opdaterede trip - hvis vi ikke vælger at ændre på det goså.





