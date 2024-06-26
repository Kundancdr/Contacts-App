package com.example.contactapp.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.contactapp.Presentation.ContacsViewModel
import com.example.contactapp.Presentation.ContactState
import com.example.contactapp.Presentation.screens.AddContacts
import com.example.contactapp.Presentation.screens.AllContacts

@Composable
fun NavGraph ( modifier: Modifier = Modifier,viewModel:ContacsViewModel, navController: NavHostController) {
val state by viewModel.state.collectAsState()

    NavHost(navController = navController, startDestination = Routes.AllContact.route) {

        composable(Routes.AllContact.route) {
            AllContacts(state = state, navController =navController , viewModel =viewModel  )

        }

        composable(Routes.AddContact.route) {
            AddContacts(state =viewModel.state.collectAsState().value, navController = navController) {
                viewModel.saveContact()

            }

        }
    }
}
