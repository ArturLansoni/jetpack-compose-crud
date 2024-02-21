package com.arturlansoni.servicelist.presentation.ui.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.arturlansoni.servicelist.R
import com.arturlansoni.servicelist.domain.entities.Service
import com.arturlansoni.servicelist.presentation.navigation.Screens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navigator: (String) -> Unit,
    onLogout: (String, Boolean) -> Unit,
    viewModel: ListViewModel,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val services = remember { mutableStateOf(listOf<Service>()) }
    val filterText = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.onLoad()
        services.value = viewModel.state.value.services
    }

    val filteredList = services.value.filter {
        it.name.trim().lowercase()
            .contains(filterText.value.trim().lowercase())
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(context.getString(R.string.list_screen_title)) },
            actions = {
                IconButton(onClick = {
                    coroutineScope.launch {
                        viewModel.logout()
                        onLogout(Screens.Login.route, false)
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        contentDescription = context.getString(R.string.list_screen_back)
                    )
                }
            },
        )
    },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                coroutineScope.launch {
                    navigator(Screens.Form.route)
                }
            }) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = context.getString(R.string.list_screen_new)
                )
            }
        }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedTextField(
                    value = filterText.value,
                    onValueChange = { filterText.value = it },
                    label = { Text(context.getString(R.string.list_screen_search)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    content = {
                        items(filteredList.size) { index ->
                            val service = filteredList[index]
                            ServiceItem(
                                name = service.name,
                                location = service.location,
                                rating = service.rating,
                                category = service.category,
                                imageURL = service.imageURL,
                                onPress = { navigator("${Screens.Form.route}?id=${service.id}") }
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    },
                )
            }
        }
    }
}