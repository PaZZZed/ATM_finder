

# Projet MOBG5 - Août 2023

Ce dépôt contient les sources du projet **ATM Finder**.

## Description

L'application a pour but de faciliter la localisation de guichets automatiques (ATMs) à Bruxelles. Les utilisateurs peuvent également marquer des guichets comme favoris pour un accès rapide ultérieurement.

## Persistance des données

Les données relatives aux ATMs(favoris) et aux comptes Users sont stockées localement dans une base de données Room.

## Services REST

Pour collecter les données sur les ATMs, des appels sont effectués à une API(https://opendata.bruxelles.be/api). De plus, l'application utilise l'API de Google Maps pour la géolocalisation et le rendu des cartes.

## Build

Pour construire le projet, quelques étapes sont nécessaires :

1. **Ajoutez votre clé API Google Maps** dans le fichier `local.properties` :

    ```
    MAPS_API_KEY=Votre_Clé_API_Ici
    ```



## Auteur

**[Zedzian Pawel]** [53204]



