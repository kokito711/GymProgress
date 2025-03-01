package ui.home.tabs.training

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.core.navigation.trainingnavigation.TrainingMenuItem


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TrainingScreen(navController: NavHostController) {
    var expanded by remember { mutableStateOf(false) }

    val items = listOf(
        TrainingMenuItem.StartSession(),
        TrainingMenuItem.FinishSession(),
        TrainingMenuItem.AddActivity()
    )
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
            title = { Text(text = "Training") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            actions = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(imageVector = Icons.Rounded.Menu, contentDescription = null)

                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    items.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item.title) },
                            onClick = {
                                navController.navigate(route = item.route) {
                                    //This goes to the stack and check if view is already created. If it does,
                                    //Nav won´t open a new view and load the existing one
                                    navController.graph.startDestinationRoute?.let { route ->
                                        popUpTo(route) {
                                            saveState = true
                                        }
                                    }
                                    //Prevent to open the same view more than one time
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            leadingIcon = item.icon
                        )
                    }
                }
            }
        )
        Box(
            modifier = Modifier
                .background(Color.LightGray) // Optional background color
        ) {
            if (exercises.isEmpty()) {
                EmptyExerciseListMessage()
            } else {
                ExerciseList(exercises = exercises)
            }
        }
    }
}


@Composable
fun EmptyExerciseListMessage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "It's a good moment to make exercise :)",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun ExerciseList(exercises: List<Exercise>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(exercises) { exercise ->
            ExerciseItem(exercise = exercise)
        }
    }
}

@Composable
fun ExerciseItem(exercise: Exercise) {
    Column(
        modifier = Modifier
            .fillMaxWidth() // Make each item fill the width
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(text = exercise.name, style = MaterialTheme.typography.titleMedium)
        Text(text = "Duration: ${exercise.duration}", style = MaterialTheme.typography.bodyMedium)
    }
}

data class Exercise(val name: String, val duration: String) //TODO extract to data/model