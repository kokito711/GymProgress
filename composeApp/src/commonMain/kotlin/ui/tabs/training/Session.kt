package ui.tabs.training

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import domain.training.model.training.Exercise
import expects.logDebug
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import presentation.TrainingViewModel
import ui.elements.training.EmptyExerciseList
import ui.elements.training.ExerciseList
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun Session(
    navController: NavHostController,
    date: Long?,
    trainingViewModel: TrainingViewModel = viewModel()
) {
    logDebug(
        tag = "Session", message = "Session Screen - date: ${
            Instant.fromEpochMilliseconds(date!!).toLocalDateTime(TimeZone.UTC)
        }"
    )
    val formattedDate = trainingViewModel.getFormattedDate(date)
    logDebug("SessionComposable", "Formatted date: $formattedDate")
    //val exercises: List<Exercise> = emptyList()
    val exercises: List<Exercise> = listOf(
        Exercise("Exercise 1", "30"),
        Exercise("Exercise 2", "45"),
        Exercise("Exercise 3", "60"),
        Exercise("Exercise 4", "30"),
        Exercise("Exercise 5", "45"),
        Exercise("Exercise 6", "60"),
        Exercise("Exercise 7", "30"),
        Exercise("Exercise 8", "45"),
        Exercise("Exercise 9", "60"),
        Exercise("Exercise 10", "30"),
        Exercise("Exercise 11", "45"),
        Exercise("Exercise 12", "60"),
        Exercise("Exercise 13", "30"),
        Exercise("Exercise 14", "45"),
        Exercise("Exercise 15", "60"),
        Exercise("Exercise 16", "30"),
    )

    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Text(
                    text = "Session Date: $formattedDate",
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            actions = {
                IconButton(onClick = { //TODO: Acción para añadir ejercicios
                }) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                }
            }
        )
        // Resto del contenido de la pantalla Session
        Box(modifier = Modifier.background(Color.LightGray)) {
            if (exercises.isEmpty()) {
                EmptyExerciseList()
            } else {
                ExerciseList(exercises = exercises)
            }
        }
    }
}