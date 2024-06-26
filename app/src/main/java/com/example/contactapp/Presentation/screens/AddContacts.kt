package com.example.contactapp.Presentation.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.contactapp.Navigation.Routes
import com.example.contactapp.Presentation.ContactState
import com.example.contactapp.utils.compressImage
import java.io.InputStream

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun AddContacts(
    state: ContactState,
    navController: NavController,
    onEvent: () -> Unit,

    ) {
    var bitmapImage: Bitmap? = null

    if (state.image != null)
        bitmapImage = BitmapFactory.decodeByteArray(state.image.value, 0, state.image.value.size)

    val context = LocalContext.current
// For image pick from our localDataBase
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent())
    { uri: Uri? ->

        if (uri != null) {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val bytes = inputStream?.readBytes()
            if (bytes != null) {
                state.image.value = bytes


                val compressedImage = compressImage(bytes)
                if (compressedImage.size > 1024 * 1024) { // 1MB
                    Toast.makeText(context, "Image size is too large. Please choose a smaller image.", Toast.LENGTH_SHORT).show()
                } else {
                    state.image.value = compressedImage

                    Toast.makeText(context, "Image added", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Add Contact")},
           navigationIcon = {
               IconButton(onClick = { /*TODO*/ }) {
                   Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu Icon" )
               }
           }
        ) }) {
        Column(modifier = Modifier
            .padding(it)
            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Spacer(modifier = Modifier.height(16.dp))

            Card(modifier = Modifier
                .clickable { launcher.launch("image/*") }
                .size(120.dp)
                .clip(shape = CircleShape)
                   ) {
                if (bitmapImage != null) {
                    Image(
                        bitmap = bitmapImage.asImageBitmap(),
                        contentDescription = "Contact Image",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(
                                shape = CircleShape
                            )
                            .clickable {
                                launcher.launch("image/*")
                            },
                        contentScale = ContentScale.FillBounds
                    )
                }
                else {

                   Image(imageVector = Icons.Default.AddCircle, contentDescription = null,
                        modifier = Modifier

                            .size(50.dp)
                            .clip(shape = CircleShape)
                            .clickable {
                                launcher.launch("image/*")
                            }

                    )

                }

            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Add picture")

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.name.value,
                onValueChange = { state.name.value = it },
                label = {Text(text = "Name")},
                leadingIcon = { Icon(imageVector = Icons.Filled.AccountCircle, contentDescription =null )},
                shape = RoundedCornerShape(8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = state.number.value,
                onValueChange = { state.number.value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = {Text(text = "Mobile Number")},
                leadingIcon = { Icon(imageVector = Icons.Filled.Call, contentDescription =null )},
                shape = RoundedCornerShape(8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = state.email.value,
                onValueChange = { state.email.value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                label = {Text(text = "Email")},
                leadingIcon = { Icon(imageVector = Icons.Filled.Email, contentDescription =null )},
                shape = RoundedCornerShape(8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
          Row( Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween)
          {
              CustomButton(btnTex = "Cancel", color = androidx.compose.ui.graphics.Color.Gray) {

                  state.id.value = 0
                  state.name.value = ""
                  state.number.value = ""
                  state.email.value = ""
                  state.dateOfCreation.value = 0
                  state.image.value = ByteArray(0)


              }
              Button(onClick = { onEvent.invoke()
                  navController.navigate(Routes.AllContact.route)

              }) {
                  Text(text = "save")


              }
//              CustomButton(btnTex = "Save", color = androidx.compose.ui.graphics.Color.Yellow) {
//                  onEvent.invoke()
//                  dialogShow = false
//              }
          }

        }
    }

}
@Composable
fun CustomButton(

    btnTex: String,
    color: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit
) {

    Button(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .height(42.dp)
            .width(120.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        )
    )
    {
        Text(text = btnTex)
    }

}