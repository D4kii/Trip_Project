package br.senai.sp.jandira.trip

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.trip.components.BottomShape
import br.senai.sp.jandira.trip.components.TopShape
import br.senai.sp.jandira.trip.repository.userRepository
import br.senai.sp.jandira.trip.ui.theme.TripTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TripTheme {
                // A surface container using the 'background' color from the theme
                LoginScreen()
            }
        }
    }
}

@Composable
fun LoginScreen() {

    val context = LocalContext.current
    
    var emailState by remember {
        mutableStateOf("")
    }
    var passwordState by remember {
        mutableStateOf("")
    }

    var passwordVisibilityState by remember {
        mutableStateOf(false)
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TopShape()
            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Text(text = stringResource(id = R.string.login),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(207,6,240)
                )
                Text(text = stringResource(id = R.string.please_signin),
                    fontSize = 14.sp,
                    color = Color(160, 156, 156)
                )
            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                horizontalAlignment = Alignment.End) {
                OutlinedTextField(
                    value = emailState, 
                    onValueChange = {
                                    emailState = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    label = {
                        Text(text = stringResource(id = R.string.email))
                    },
                    leadingIcon = {
                        Icon(painter = painterResource(id = R.drawable.email_24),
                            contentDescription = "",
                            tint = Color(207,6,240)
                        )
                    }
                )
                OutlinedTextField(
                    value = passwordState, 
                    onValueChange = {
                                    passwordState = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 31.dp),
                   visualTransformation =  if (!passwordVisibilityState) PasswordVisualTransformation() else VisualTransformation.None,
                    shape = RoundedCornerShape(16.dp),
                    label = {
                        Text(
                            text = stringResource(id = R.string.password)
                        )
                    },
                    leadingIcon = {
                        Icon(painter = painterResource(id = R.drawable.lock_24),
                            contentDescription = "",
                            tint = Color(207,6,240)
                        )
                    },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordVisibilityState = !passwordVisibilityState
                        }
                    ) {
                        Icon(imageVector = if(passwordVisibilityState)
                            Icons.Default.VisibilityOff
                        else
                            Icons.Default.Visibility,
                            contentDescription = null)
                    }
                }
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                                  authenticate(emailState,
                                      passwordState,
                                      context)
                        },
                        colors = ButtonDefaults.buttonColors(Color(207,6,240)),
                        shape = RoundedCornerShape(16.dp)
                    ) {

                            Text(
                                text = stringResource(id = R.string.signin).uppercase(),
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_forward_24),
                                contentDescription = "",
                                tint = Color(255, 255, 255)
                            )

                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.dont_account),
                        color = Color(160, 156, 156))
                    Spacer(modifier = Modifier.width(3.dp))
                   TextButton(
                       onClick = {
                            var openSignUp = Intent(context, SignUpActivity::class.java)
                           context.startActivity(openSignUp)
                       }
                   ) {
                       Text(
                           text = stringResource(id = R.string.signup),
                           fontWeight = FontWeight.Bold,
                           color = Color(207, 6, 240)
                       )
                   }
                }

            }
            Row(
                modifier = Modifier.fillMaxWidth()
                ,
                horizontalArrangement = Arrangement.Start,

                ) {
                BottomShape()
            }
        }
    }
    }

fun authenticate(email: String, password: String, context: Context) {
    val userRepository = userRepository(context)
    val user = userRepository.authenticate(
        email,
        password
    )

    if (user != null){
        val openHomeActivity = Intent(context, HomeActivity::class.java)
        context.startActivity(openHomeActivity)
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    TripTheme {
        LoginScreen()
    }
}