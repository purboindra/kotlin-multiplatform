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
import org.example.project.navigation.Routers


@Composable
fun Screen2(name:String) {
    val navigationController = LocalNavigationController.current
    
    
    Column {
        Button(onClick = {
            navigationController.popBackStack()
        }) {
            Text("Back")
        }
        Spacer(modifier = Modifier.height(12.dp))
        
        Text("Text screen 2: ${name}")
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Button(onClick = {
            navigationController.navigate(Routers.SCREEN_3)
        }) {
            Text("Navigate to screen 3")
        }
    }
}