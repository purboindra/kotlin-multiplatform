package org.example.project.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.navigation.LocalNavigationController

@Composable
fun Screen3() {
    val navigationController = LocalNavigationController.current
    
    Column {
        Button(onClick = {
            navigationController.popBackStack()
        }) {
            Text("Back")
        }
        Spacer(modifier = Modifier.height(12.dp))
        
        Text("Text screen 3")
        
        Spacer(modifier = Modifier.height(12.dp))
        
        
    }
}