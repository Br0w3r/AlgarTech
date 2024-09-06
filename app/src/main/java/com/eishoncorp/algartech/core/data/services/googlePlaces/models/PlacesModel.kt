package com.eishoncorp.algartech.core.data.services.googlePlaces.models

data class PlacesModel(
    val predictions: List<PredictionPlace>,
    val status: String
)

data class PredictionPlace(
    val description: String,
    val matched_substrings: List<MatchedSubstring>,
    val place_id: String,
    val reference: String,
    val structured_formatting: StructuredFormatting,
    val terms: List<Term>,
    val types: List<String>
)

data class MatchedSubstring(
    val length: Int,
    val offset: Int
)

data class StructuredFormatting(
    val main_text: String,
    val main_text_matched_substrings: List<MatchedSubstring>,
    val secondary_text: String
)

data class Term(
    val offset: Int,
    val value: String
)
