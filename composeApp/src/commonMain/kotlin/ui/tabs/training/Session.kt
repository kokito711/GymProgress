package ui.tabs.training

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import domain.training.model.training.Exercise
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import presentation.SessionViewModel
import presentation.TrainingViewModel
import ui.core.navigation.Routes
import ui.elements.training.EmptyExerciseList
import ui.elements.training.ExerciseList
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun Session(
    navController: NavHostController,
    sessionId: Long?,
    trainingViewModel: TrainingViewModel = koinViewModel(),
    sessionViewModel: SessionViewModel = koinViewModel(parameters = { parametersOf(sessionId ?: -1L) })
) {
    val exercises by sessionViewModel.exercises.collectAsState()
    val sessionDetails by sessionViewModel.sessionDetails.collectAsState()
    
    val formattedDate = sessionDetails?.date?.let { trainingViewModel.getFormattedDate(it) } ?: "Loading..."
    
    var showDeleteDialog by remember { mutableStateOf<Exercise?>(null) }

    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Text(
                    text = "Session: $formattedDate",
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            actions = {
                IconButton(onClick = { 
                    navController.navigate("${Routes.AddExercise.route}/$sessionId")
                }) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add Exercise")
                }
            }
        )

        Box(modifier = Modifier.background(Color.LightGray).fillMaxSize()) {
            if (exercises.isEmpty()) {
                EmptyExerciseList()
            } else {
                ExerciseList(
                    exercises = exercises,
                    onExerciseClick = { exercise ->
                        navController.navigate("${Routes.ExerciseDetail.route}/${exercise.id}")
                    },
                    onExerciseLongClick = { exercise ->
                        showDeleteDialog = exercise
                    }
                )
            }
        }
    }

    // Mantener solo el diálogo de confirmación de borrado
    showDeleteDialog?.let { exercise ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("Delete Exercise") },
            text = { Text("Are you sure you want to delete '${exercise.name}'?") },
            confirmButton = {
                Button(
                    onClick = {
                        exercise.id?.let { sessionViewModel.deleteExercise(it) }
                        showDeleteDialog = null
                    },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) { Text("Delete") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = null }) { Text("Cancel") }
            }
        )
    }
}
