package com.example.oskartestapp.ui.signin


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.oskartestapp.R
import com.example.oskartestapp.ui.components.LargeSpacer
import com.example.oskartestapp.ui.components.ProgressDialog
import com.example.oskartestapp.ui.components.SmallSpacer
import com.example.oskartestapp.ui.signup.State

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignInScreen(
    state: State,
    changeEmail: (String) -> Unit,
    changePassword: (String) -> Unit,
    signIn: () -> Unit,
    goToSignUp: () -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    if (state.isLoading)
        ProgressDialog()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0),
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        }
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            ElevatedCard {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp)) {
                    Text(
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                        text = stringResource(R.string.sign_in),
                        style = MaterialTheme.typography.titleMedium
                    )
                    LargeSpacer()
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.email,
                        onValueChange = changeEmail,
                        label = {
                            Text(text = stringResource(R.string.email))
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                    SmallSpacer()
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.password,
                        onValueChange = changePassword,
                        label = {
                            Text(text = stringResource(R.string.password))
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            val image = if (passwordVisible)
                                R.drawable.eye
                            else
                                R.drawable.hide_eye

                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(painter = painterResource(id = image), null)
                            }
                        }
                    )
                    SmallSpacer()
                    Button(modifier = Modifier.fillMaxWidth(), onClick = signIn) {
                        Text(text = stringResource(R.string.sign_in))
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stringResource(R.string.don_t_have_an_account_txt))
                        SmallSpacer()
                        TextButton(onClick = goToSignUp) {
                            Text(text = stringResource(R.string.sign_up))
                        }
                    }

                }
            }
        }

    }
}
