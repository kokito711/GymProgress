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
import androidx.navigation.NavHostController
import expects.logDebug
import org.koin.compose.viewmodel.koinViewModel
import presentation.TrainingViewModel
import ui.core.navigation.Routes
import ui.core.navigation.trainingnavigation.TrainingMenuItem
import ui.elements.training.TrainingDatePicker
import ui.elements.training.session.SessionList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingScreen(
    navController: NavHostController,
    trainingViewModel: TrainingViewModel = koinViewModel()
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
    val savedSessions by trainingViewModel.savedTrainings.collectAsState()

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
                                    trainingViewModel.finishCurrentTrainingSession()
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
            SessionList(
                sessions = savedSessions,
                onSessionClick = { session ->
                    // Navegar a la pantalla de detalles usando el ID de la sesión
                    // Nota: Routes.StartSession.route se usa aquí porque es la ruta que acepta el parámetro date (o id)
                    navController.navigate(route = "${Routes.StartSession.route}?date=${session.id}")
                }
            )
        }
    }

    if (showDatePicker) {
        TrainingDatePicker(
            onDateSelected = { newSelectedDateMillis -> // Renombrado para claridad
                trainingViewModel.startNewTrainingSession(newSelectedDateMillis)
                logDebug("TrainingScreen", "Selected date (ms since epoch): $newSelectedDateMillis")
                trainingViewModel.setSelectedDate(newSelectedDateMillis)
                showDatePicker = false

                val sessionPreviouslyStarted = trainingViewModel.isSessionStarted.value
                val previousSelectedDateMillis = trainingViewModel.selectedDate.value

                // Navigate only if a date is selected
                selectedItem?.let { item ->
                    // Si el ítem es para iniciar una sesión, marca la sesión como iniciada en el ViewModel.
                    // Esto es importante para tu lógica condicional de restoreState
                    if (item is TrainingMenuItem.StartSession) {
                        trainingViewModel.setSessionStarted(true)
                    }
                    logDebug(
                        "TrainingScreen",
                        "Navigating to: ${item.route}?date=${newSelectedDateMillis}"
                    )
                    navController.navigate(route = "${item.route}?date=${newSelectedDateMillis}") {
                        //This goes to the stack and check if view is already created. If it does,
                        //Nav won´t open a new view and load the existing one
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState =
                                    true // Siempre guarda el estado de las pantallas de la bottom bar al hacer pop
                            }
                        }
                        launchSingleTop = true //Prevent to open the same view more than one time

                        val shouldRestore =
                            sessionPreviouslyStarted && (previousSelectedDateMillis == newSelectedDateMillis)
                        if (shouldRestore) {
                            logDebug(
                                "TrainingScreen",
                                "Session was already started. Setting restoreState = true"
                            )
                            restoreState = true
                        } else {
                            logDebug(
                                "TrainingScreen",
                                "Session is starting now (or was not started). Setting restoreState = false"
                            )
                            restoreState = false
                        }
                    }
                }
            },
            onDismiss = { showDatePicker = false }
        )
    }
}
