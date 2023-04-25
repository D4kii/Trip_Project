package br.senai.sp.jandira.trip

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.trip.components.BottomShape
import br.senai.sp.jandira.trip.components.TopShape
import br.senai.sp.jandira.trip.model.User
import br.senai.sp.jandira.trip.repository.userRepository
import br.senai.sp.jandira.trip.ui.theme.TripTheme
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TripTheme {
                    SignUpsScreen(name = "")
            }
        }
    }
}
@Composable
fun SignUpsScreen(name: String) {

    var userNameState by remember {
        mutableStateOf("")
    }
    var userPhoneState by remember  {
        mutableStateOf("")
    }
    var emailState by remember  {
        mutableStateOf("")
    }
    var passwordState by remember  {
        mutableStateOf("")
    }
    var over18State by remember {
        mutableStateOf(false)
    }
    var expanded by remember {
        mutableStateOf(false)
    }

    var context = LocalContext.current
    
    //Obter foto da galeria de imagens
    var photoUri by remember {
        mutableStateOf<Uri?>(null)
    }
    
    //Criar o objeto que abrirá a galeria e retornará a Uri
    //a Uri da imagem selecionada
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){
        photoUri = it
    }

    var painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(photoUri).build()
    )



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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .weight(weight = 0.1f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.signup),
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(240, 7, 240)
                    )
                    Text(
                        text = stringResource(id = R.string.create_new_account),
                        fontSize = 20.sp,
                        color = Color(160, 156, 156)
                    )

                    Box(
                        contentAlignment = Alignment
                            .BottomEnd,
                        modifier = Modifier.size(100.dp)

                    ) {
                        Card(
                            modifier = Modifier
                                .size(100.dp)
                                .align(alignment = Alignment.TopEnd),
                            shape = CircleShape,
                            border = BorderStroke(
                                width = 1.dp,
                                brush = Brush.horizontalGradient(colors = listOf(Color.Magenta, Color.Transparent))
                            )


                        ) {
                            Image(painter = painter,
                                contentDescription = null,
                            modifier = Modifier
                                .size(16.dp),
                            contentScale = ContentScale.Crop)
                        }
                        Card(
                            modifier = Modifier
                                .size(50.dp)
                                .align(alignment = Alignment.BottomStart)
                                .offset(x = 50.dp),
                            shape = CircleShape,

                            ) {


                            Image(
                                painter = painterResource(id = R.drawable.camera),
                                contentDescription = null,
                                modifier = Modifier
                                    .align(alignment = Alignment.BottomEnd)
                                    .offset(
                                        x = 0.dp,
                                        y = 0.dp
                                    )
                                    .size(24.dp)
                                    .padding(10.dp)
                                    .clickable {
                                        launcher.launch("image/*")
                                    }
                            )

                        }
                    }
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    OutlinedTextField(value = userNameState,
                        onValueChange = {
                                        userNameState = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        label = {
                            Text(text = stringResource(id = R.string.username))
                        },
                        leadingIcon = {
                            Icon(painter = painterResource(id = R.drawable.person_24),
                                contentDescription = "",
                                tint = Color(207,6,240)
                            )
                        }
                    )
                    OutlinedTextField(
                        value = userPhoneState,
                        onValueChange = {
                            userPhoneState = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        label = {
                            Text(text = stringResource(id = R.string.phone))
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        leadingIcon = {
                            Icon(painter = painterResource(id = R.drawable.phone_android_24),
                                contentDescription = "",
                                tint = Color(207,6,240)
                            )
                        }
                    )
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
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        visualTransformation = PasswordVisualTransformation(),
                        label = {
                            Text(text = stringResource(id = R.string.password))
                        },
                        leadingIcon = {
                            Icon(painter = painterResource(id = R.drawable.lock_24),
                                contentDescription = "",
                                tint = Color(207,6,240)
                            )
                        }
                    )


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = over18State,
                            onCheckedChange = {
                                checked ->
                                over18State = checked
                            }
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = stringResource(id = R.string.over_eighteen)
                        )
                    }
                    Spacer(modifier = Modifier.height(23.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        onClick = {
                            userSave(
                                context,
                                emailState,
                                userNameState,
                                userPhoneState,
                                passwordState,
                                over18State
                            )
                        },
                        colors = ButtonDefaults.buttonColors(Color(207,6,240)),
                        shape = RoundedCornerShape(16.dp)
                    ) {

                        Text(
                            text = stringResource(id = R.string.create_account).uppercase(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )


                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = stringResource(id = R.string.have_account),
                            color = Color(160, 156, 156)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = stringResource(id = R.string.signin),
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

fun userSave(
    context: Context,
    email: String,
    userName: String,
    phone: String,
    password: String,
    isOver: Boolean
) {
    val userRepository = userRepository(context)

    // Recuperando no banco um usuário que tenha
    //o email informado
    var user = userRepository.findUserByEmail(email)

    //Se user for de null, gravamos o novo
    //usuário, senão, avisamos que o usuário já existe.

    if (user == null){
        val newUser = User(
            userName = userName,
            phone = phone,
            email = email,
            password = password,
            isOver18 = isOver
        )
        val id = userRepository.save(newUser)
        Toast.makeText(
            context,
            "User created #$id",
            Toast.LENGTH_LONG
        ).show()
    }else{
        Toast.makeText(
            context,
            "User already exists!!",
            Toast.LENGTH_SHORT
        ).show()
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview2() {
    TripTheme {
        SignUpsScreen(name = "")
    }
}