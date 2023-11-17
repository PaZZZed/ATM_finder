package com.example.mobg5_53204.database.api

data class ATMResponse(val records: List<ATMRecord>)

data class ATMRecord(
    val recordid: String,  // Ajout de 'recordid'
    val fields: ATMFields
)

data class ATMFields(
    val coord: List<Double>,
    val adresse_fr: String,
    val adresse_nl: String,
    val agence: String
)

//pour des id precis
data class ATMFieldsObject(
    val coord: Coord,
    val adresse_fr: String,
    val adresse_nl: String,
    val agence: String
)

data class Coord(
    val lat: Double,
    val lon: Double
)