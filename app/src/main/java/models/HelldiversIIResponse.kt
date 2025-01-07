package models

data class HelldiversIIResponse(
    val data: Map<String, PlanetInfo>
)

data class PlanetInfo(
    val name: String,
    val sector: String,
    val biome: Biome?,
    val environmentals: List<Environmental>,
)

data class Biome(
    val slug: String,
    val description: String
)

data class Environmental(
    val name: String,
    val description: String
)
