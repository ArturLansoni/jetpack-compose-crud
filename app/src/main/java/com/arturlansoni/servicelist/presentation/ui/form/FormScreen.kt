package com.arturlansoni.servicelist.presentation.ui.form

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.arturlansoni.servicelist.core.enums.ScreenStatus
import com.arturlansoni.servicelist.domain.entities.Service
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(
    id: String?,
    goBack: () -> Unit,
    viewModel: FormViewModel,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val name = remember { mutableStateOf("") }
    val location = remember { mutableStateOf("") }
    val category = remember { mutableStateOf("") }
    val imageURL = remember { mutableStateOf("") }
    val rating = remember { mutableStateOf(0.0) }
    val maxRating = 5

    val isValid = name.value.isNotEmpty() &&
            location.value.isNotEmpty() &&
            category.value.isNotEmpty()

    fun showError() {
        if (viewModel.state.value.errorMessage != null)
            Toast.makeText(
                context,
                viewModel.state.value.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
    }

    LaunchedEffect(Unit) {
        if (id?.isNotEmpty() == true) {
            viewModel.onLoad(id)

            name.value = viewModel.state.value.service?.name ?: ""
            location.value = viewModel.state.value.service?.location ?: ""
            category.value = viewModel.state.value.service?.category ?: ""
            imageURL.value = viewModel.state.value.service?.imageURL ?: ""
            rating.value = viewModel.state.value.service?.rating ?: 0.0
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text(context.getString(R.string.form_screen_title)) },
            actions = {
                if (id?.isNotEmpty() == true) {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            viewModel.onDelete(id)
                            if (viewModel.state.value.status is ScreenStatus.Success) goBack() else showError()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = context.getString(R.string.form_screen_delete)
                        )
                    }
                }
            },
            navigationIcon = {
                IconButton(onClick = goBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = context.getString(R.string.form_screen_back)
                    )
                }
            })
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(bottom = 32.dp, start = 32.dp, end = 32.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedTextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text(context.getString(R.string.form_screen_name)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = location.value,
                    onValueChange = { location.value = it },
                    label = { Text(context.getString(R.string.form_screen_location)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = category.value,
                    onValueChange = { category.value = it },
                    label = { Text(context.getString(R.string.form_screen_category)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = imageURL.value,
                    onValueChange = { imageURL.value = it },
                    label = { Text(context.getString(R.string.form_screen_image)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "${context.getString(R.string.form_screen_rating)}: ${rating.value.toInt()}/$maxRating")
                    Rating(
                        value = rating.value,
                        onValueChange = { newValue ->
                            rating.value = newValue
                        },
                        maxRating = maxRating
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        coroutineScope.launch {
                            if (id?.isNotEmpty() == true) {
                                viewModel.onUpdate(
                                    Service(
                                        id = id,
                                        imageURL = imageURL.value,
                                        category = category.value,
                                        rating = rating.value,
                                        location = location.value,
                                        name = name.value,
                                    )
                                )
                                if (viewModel.state.value.status is ScreenStatus.Success) goBack() else showError()
                            } else {
                                viewModel.onCreate(
                                    Service(
                                        imageURL = imageURL.value,
                                        category = category.value,
                                        rating = rating.value,
                                        location = location.value,
                                        name = name.value,
                                    )
                                )
                                if (viewModel.state.value.status is ScreenStatus.Success) goBack() else showError()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isValid
                ) {
                    Text(context.getString(R.string.form_screen_submit))
                }
            }
        }
    }
}
