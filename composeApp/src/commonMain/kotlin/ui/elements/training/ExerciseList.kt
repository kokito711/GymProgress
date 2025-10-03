package ui.elements.training

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.training.model.training.Exercise

@Composable
fun ExerciseList(exercises: List<Exercise>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) { items(exercises) { exercise -> ExerciseItem(exercise = exercise) } }
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