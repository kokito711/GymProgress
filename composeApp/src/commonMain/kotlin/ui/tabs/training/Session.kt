package ui.tabs.training

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import domain.model.training.TrainingViewModel

@Composable
fun Session(
    navController: NavHostController,
    date: Long?,
    trainingViewModel: TrainingViewModel = viewModel()
) {
    val formattedDate = trainingViewModel.getFormattedDate(date)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header con la fecha
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Session Date: $formattedDate",
                    style = MaterialTheme.typography.headlineSmall
                )
                Button(onClick = { /* TODO: Acción para añadir ejercicios */ }) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Exercise")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Add Exercise")
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Resto del contenido de la pantalla Session
        Text(text = "Content of the session")
    }
}