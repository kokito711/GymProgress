package ui.tabs.training

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import presentation.ExerciseDetailViewModel
import ui.elements.training.TimePickerWheel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailScreen(
    navController: NavHostController,
    exerciseId: Long,
    sessionId: Long = -1L,
    viewModel: ExerciseDetailViewModel = koinViewModel(parameters = { parametersOf(exerciseId, sessionId) })
) {
    val exercise by viewModel.exercise.collectAsState()

    var name by remember(exercise) { mutableStateOf(exercise?.name ?: "") }
    var description by remember(exercise) { mutableStateOf(exercise?.description ?: "") }
    var comments by remember(exercise) { mutableStateOf(exercise?.comments ?: "") }
    
    // Parsear duración actual para el TimePicker (HH:MM:SS)
    val durationParts = (exercise?.duration ?: "00:00:00").split(":")
    var hours by remember(exercise) { mutableStateOf(durationParts.getOrNull(0)?.toIntOrNull() ?: 0) }
    var minutes by remember(exercise) { mutableStateOf(durationParts.getOrNull(1)?.toIntOrNull() ?: 0) }
    var seconds by remember(exercise) { mutableStateOf(durationParts.getOrNull(2)?.toIntOrNull() ?: 0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (exerciseId == -1L) "Add Exercise" else "Edit Exercise") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (name.isNotBlank()) {
                                val durationFormatted = "${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
                                viewModel.saveExercise(name, durationFormatted, description.ifBlank { null }, comments.ifBlank { null })
                                navController.popBackStack()
                            }
                        },
                        enabled = name.isNotBlank()
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = "Save",
                            tint = if (name.isNotBlank()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(16.dp))
            
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Exercise Name") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(Modifier.height(24.dp))
            
            Text("Duration", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            
            TimePickerWheel(
                hours = hours,
                minutes = minutes,
                seconds = seconds,
                onHoursChange = { hours = it },
                onMinutesChange = { minutes = it },
                onSecondsChange = { seconds = it }
            )
            
            Spacer(Modifier.height(24.dp))
            
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description (optional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            
            Spacer(Modifier.height(16.dp))
            
            OutlinedTextField(
                value = comments,
                onValueChange = { comments = it },
                label = { Text("Comments (optional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            
            Spacer(Modifier.height(16.dp))
        }
    }
}
