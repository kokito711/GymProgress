package data.database.dao

import kotlinx.datetime.LocalDateTime

data class TrainingDao(
    private val id: Int,
    private val date: LocalDateTime
)
