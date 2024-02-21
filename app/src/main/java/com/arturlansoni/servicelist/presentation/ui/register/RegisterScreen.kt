package com.arturlansoni.servicelist.presentation.ui.register

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.arturlansoni.servicelist.R
import com.arturlansoni.servicelist.core.enums.ScreenStatus
import com.arturlansoni.servicelist.presentation.navigation.Screens
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navigator: (String) -> Unit,
    goBack: () -> Unit,
    viewModel: RegisterViewModel,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }

    val isValid = email.value.isNotEmpty() && password.value.isNotEmpty()

    fun showError() {
        if (viewModel.state.value.errorMessage != null)
            Toast.makeText(
                context,
                viewModel.state.value.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
    }

    Scaffold(topBar = { TopAppBar(title = { Text(context.getString(R.string.register_screen_title)) }) }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    context.getString(R.string.register_screen_message),
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 1.2.em,
                )
                Spacer(modifier = Modifier.height(48.dp))
                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = { Text(context.getString(R.string.register_screen_email)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = { Text(context.getString(R.string.register_screen_password)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val icon =
                            if (passwordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                            Icon(icon, contentDescription = context.getString(R.string.register_screen_password_toggle))
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(48.dp))
                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.onSubmit(email.value, password.value)
                            if (viewModel.state.value.status is ScreenStatus.Success) navigator(Screens.List.route) else showError()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isValid
                ) {
                    Text(context.getString(R.string.register_screen_submit))
                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { goBack() },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.primary,
                    )
                ) {
                    Text(context.getString(R.string.register_screen_login))
                }
            }
        }
    }
}
