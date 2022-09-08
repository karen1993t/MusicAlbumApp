package com.applemarketingtools.musicapp.album_details_screen.presentation.screens

data class FGDFg(
    val businesses: List<Businesse?>?,
    val region: Region?,
    val total: Int?
) {
    data class Businesse(
        val categories: List<Category?>?,
        val coordinates: Coordinates?,
        val distance: Double?,
        val id: String?,
        val image_url: String?,
        val is_closed: Boolean?,
        val location: Location?,
        val name: String?,
        val phone: String?,
        val price: String?,
        val rating: Int?,
        val review_count: Int?,
        val transactions: List<String?>?,
        val url: String?
    ) {
        data class Category(
            val alias: String?,
            val title: String?
        )

        data class Coordinates(
            val latitude: Double?,
            val longitude: Double?
        )

        data class Location(
            val address1: String?,
            val address2: String?,
            val address3: String?,
            val city: String?,
            val country: String?,
            val state: String?,
            val zip_code: String?
        )
    }

    data class Region(
        val center: Center?
    ) {
        data class Center(
            val latitude: Double?,
            val longitude: Double?
        )
    }
}