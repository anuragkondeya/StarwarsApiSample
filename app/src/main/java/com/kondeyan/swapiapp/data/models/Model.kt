package com.kondeyan.swapiapp.data.models

interface SWApiType

data class Root(val films: String?,
                val people: String?,
                val planets: String?,
                val species: String?,
                val starships: String?,
                val vehicles: String?) :SWApiType

data class PagedResponse <T> (
    val count: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: String? = null) :SWApiType

data class People(
    val name: String?,
    val height: String?,
    val mass: String?,
    val hair_color: String?,
    val skin_color: String?,
    val eye_color: String?,
    val birth_year: String?,
    val gender: String?,
    val homeworld: String?,
    val films: List<String>?,
    val species: List<String>?,
    val vehicles: List<String>?,
    val starships: List<String>?,
    val created: String?,
    val edited: String?,
    val url: String?,
) :SWApiType

data class Film(
    val title: String?,
    val episode_id: Int?,
    val opening_crawl: String?,
    val director: String?,
    val producer: String?,
    val release_date: String?,
    val characters: List<String>?,
    val planets: List<String>?,
    val starships: List<String>?,
    val vehicles: List<String>?,
    val species: List<String>?,
    val created: String?,
    val edited: String?,
    val url: String?,
) :SWApiType

data class StarShip(
    val name: String?,
    val model: String?,
    val manufacturer: String?,
    val cost_in_credits: String?,
    val length: String?,
    val max_atmosphering_speed: String?,
    val crew: String?,
    val passengers: String?,
    val cargo_capacity: String?,
    val consumables: String?,
    val hyperdrive_rating: String?,
    val MGLT: String?,
    val starship_class: String?,
    val pilots: List<String>?,
    val films: List<String>?,
    val created: String?,
    val edited: String?,
    val url: String?
) :SWApiType

data class Vehicle(
    val cargo_capacity: String?,
    val consumables: String?,
    val cost_in_credits: String?,
    val created: String?,
    val crew: String?,
    val edited: String?,
    val length: String?,
    val manufacturer: String?,
    val max_atmosphering_speed: String?,
    val model: String?,
    val name: String?,
    val passengers: String?,
    val pilots: List<String>?,
    val films: List<String>?,
    val url: String?,
    val vehicle_class: String?
) :SWApiType

data class Species(
    val name: String?,
    val classification: String?,
    val designation: String?,
    val average_height: String?,
    val skin_colors: String?,
    val hair_colors: String?,
    val eye_colors: String?,
    val average_lifespan: String?,
    val homeworld: String?,
    val language: String?,
    val people: List<String>?,
    val films: List<String>?,
    val created: String?,
    val edited: String?,
    val url: String?
) :SWApiType

data class Planet(
    val name: String?,
    val rotation_period: String?,
    val orbital_period: String?,
    val diameter: String?,
    val climate: String?,
    val gravity: String?,
    val terrain: String?,
    val surface_water: String?,
    val population: String?,
    val residents: List<String>?,
    val films: List<String>?,
    val created: String?,
    val edited: String?,
    val url: String?
) :SWApiType
