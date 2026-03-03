package domain.training.model

import domain.training.model.training.Session
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


data class TrainingModel(
    val id: Long,
    val date: Long,
    val startTime: Long,
    val endTime: Long?
)

/**
 * Maps a TrainingModel from the domain layer to a Session object for the presentation layer.
 */
fun TrainingModel.toSession(): Session {
    val durationMillis = if (this.endTime != null) {
        this.endTime - this.startTime
    } else {
        0L
    }
    return Session(
        id = this.id,
        date = formatDate(this.date),
        duration = formatDuration(durationMillis)
    )
}

/**
 * Formats a Unix timestamp (in milliseconds) into a "dd/MM/yyyy" string.
 * @param millis The timestamp to format.
 * @return A formatted date string.
 */
@OptIn(ExperimentalTime::class)
private fun formatDate(millis: Long): String {
    val instant = Instant.fromEpochMilliseconds(millis)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    val day = localDateTime.day.toString().padStart(2, '0')
    val month = localDateTime.month.number.toString().padStart(2, '0')
    val year = localDateTime.year

    return "$day/$month/$year"
}

/**
 * Formats a duration (in milliseconds) into a "hh hours mm minutes" string.
 * @param millis The duration to format.
 * @return A formatted duration string.
 */
private fun formatDuration(millis: Long): String {
    if (millis <= 0L) return "0 minutes"
    val duration = millis.milliseconds

    val hours = duration.inWholeHours
    val minutes = duration.inWholeMinutes % 60

    return when {
        hours > 0 && minutes > 0 -> "$hours hours $minutes minutes"
        hours > 0 -> "$hours hours"
        else -> "$minutes minutes"
    }
}

