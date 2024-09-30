package org.example.project.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.navigation.LocalNavigationController
import org.example.project.navigation.LocalNavigator
import org.example.project.navigation.NavTarget
import org.example.project.navigation.Routers

@Composable
fun Screen1() {
    
    val navigator = LocalNavigator.current
    
    var textFieldName by remember { mutableStateOf("") }
    
    Column {
        Button(onClick = {}) {
            Text("Back")
        }
        Spacer(modifier = Modifier.height(12.dp))
        
        Text("Text screen 1")
        
        Spacer(modifier = Modifier.height(12.dp))
        
        TextField(
            value = textFieldName, modifier = Modifier.fillMaxWidth(),
            onValueChange = {
                textFieldName = it
            },
        )
        
        Button(onClick = {
            navigator.navigate(navTarget = NavTarget.Screen2(textFieldName))
        }) {
            Text("Navigate to screen 2 ")
        }
    }
}