package domain.model

import kotlinx.datetime.LocalDateTime


data class TrainingModel(
    private val id: Int,
    private val date: LocalDateTime
)
