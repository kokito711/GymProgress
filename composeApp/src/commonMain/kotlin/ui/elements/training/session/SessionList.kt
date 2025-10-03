package ui.elements.training.session

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
import domain.training.model.training.Session

@Composable
fun SessionList(sessions: List<Session>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    //TODO How do I know the id of each session to pass it to the Session screen?
    { items(sessions) { exercise -> SessionItem(session = exercise) } }
}

@Composable
fun SessionItem(session: Session) {
    Column(
        modifier = Modifier
            .fillMaxWidth() // Make each item fill the width
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(text = session.date, style = MaterialTheme.typography.titleMedium)
        Text(text = "Duration: ${session.duration}", style = MaterialTheme.typography.bodyMedium)
    }
}