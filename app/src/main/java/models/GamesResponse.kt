package models

data class DarkSoulsGamesResponse(
    val count: Int,
    val results: List<DarkSoulsGame>
)

data class DarkSoulsGame(
    val id: Int,
    val name: String,
    val released: String?,
    val background_image: String?,
    val rating: Double,
    val ratings_count: Int,
    val platforms: List<PlatformWrapper>?
)

data class PlatformWrapper(
    val platform: Platform
)

data class Platform(
    val id: Int,
    val name: String,
    val slug: String
)
