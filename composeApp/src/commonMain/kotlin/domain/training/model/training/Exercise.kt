package domain.training.model.training

data class Exercise(
    val id: Long? = null,
    val sessionId: Long,
    val name: String,
    val duration: String,
    val description: String? = null,
    val comments: String? = null
)
