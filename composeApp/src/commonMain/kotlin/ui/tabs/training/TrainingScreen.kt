package ui.tabs.training

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import domain.model.training.Exercise
import domain.model.training.TrainingViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.core.navigation.trainingnavigation.TrainingMenuItem
import ui.elements.training.EmptyExerciseList
import ui.elements.training.ExerciseList
import ui.elements.training.TrainingDatePicker


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TrainingScreen(
    navController: NavHostController,
    trainingViewModel: TrainingViewModel = viewModel()
) {
    var expanded by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<TrainingMenuItem?>(null) }

    val isSessionStarted by trainingViewModel.isSessionStarted.collectAsState()
    val selectedDate by trainingViewModel.selectedDate.collectAsState()

    // Lista de items condicional
    val items = remember(isSessionStarted) {
        if (isSessionStarted) {
            listOf(TrainingMenuItem.FinishSession())
        } else {
            listOf(TrainingMenuItem.StartSession())
        }
    }
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
                                selectedItem = item
                                // Mostrar el DatePicker solo si el item es StartSession
                                if (item is TrainingMenuItem.StartSession) {
                                    showDatePicker = true
                                } else {
                                    // Si es FinishSession, navegar directamente
                                    trainingViewModel.setSelectedDate(null)
                                    trainingViewModel.setSessionStarted(false)
                                }
                                expanded = false
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
                EmptyExerciseList()
            } else {
                ExerciseList(exercises = exercises)
            }
        }
    }

    if (showDatePicker) {
        TrainingDatePicker(
            onDateSelected = { date ->
                trainingViewModel.setSelectedDate(date)
                showDatePicker = false
                // Navigate only if a date is selected
                selectedItem?.let { item ->
                    if (item is TrainingMenuItem.StartSession) {
                        trainingViewModel.setSessionStarted(true)
                    }
                    navController.navigate(route = "${item.route}?date=${date}") {
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
                }
            },
            onDismiss = { showDatePicker = false }
        )
    }
}
