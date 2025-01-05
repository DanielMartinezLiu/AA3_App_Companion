package HelldiversIIApi

data class HelldiversIIResponse(
    val data: HelldiversIIData
)

data class HelldiversIIData(
    val results: List<PlanetInfo>
)

data class PlanetInfo(
    val index: Int,
    val name: String,
    val sector: String,
    val biome: Biome,
    val environmentals: List<Environmental>,
    val initialOwner: Int
)

data class Biome(
    val slug: String,
    val description: String
)

data class Environmental(
    val name: String,
    val description: String
)