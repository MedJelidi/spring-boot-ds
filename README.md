# Restaurant Management

Projet DS JEE -- GLSI-A

## Question 1

Table API

```java
/api/table
/api/table/{numero}
```
Client API

```java
/api/client
/api/client/{id}
```
Met API

```java
/api/met
/api/met/{nom}
```
Ticket API

```java
/api/ticket
/api/ticket/{numero}
```

## Question 2
src/main/resources/restaurant.sql (used for tests)

## Question 3
a) Pour une période donnée quel est le plat le plus acheté ?
```java
/api/met/mostBookedPlatInPeriod/{beginDate}/{endDate}
```
b) Quel est le client le plus fidèle au restaurant ?
```java
/api/client/mostLoyal
```
c) Quelle est la table la plus réservée ?
```java
/api/table/mostBooked
```
d) Quel est le jour de la semaine le plus réservé par un client donné ?
```java
/api/client/mostBookedWeekDay/{id}
```
e) Retourner le revenu par jour, semaine et mois.
```java
/api/ticket/dayWeekMonthRevenue
```
f) Retourner le revenu pour une période donnée.
```java
/api/ticket/periodRevenue/{beginDate}/{endDate}
```
## Question 4
Create user
```java
/api/user

{
    "username": "username",
    "password": "password"
}
```

## Authors
[JELIDI Mohammed Habib](https://github.com/MedJelidi)