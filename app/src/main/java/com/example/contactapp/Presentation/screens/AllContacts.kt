package com.example.contactapp.Presentation.screens


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.contactapp.Navigation.Routes
import com.example.contactapp.Presentation.ContacsViewModel
import com.example.contactapp.Presentation.ContactState
import com.example.contactapp.utils.ProfileImage
import com.example.contacts.data.Contact


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AllContacts(
    state: ContactState,
    viewModel: ContacsViewModel,
    navController: NavHostController

) {

    var isSortByName by rememberSaveable { mutableStateOf(false)}

    Scaffold(
        containerColor = Color.White,

        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        
                       Image(imageVector = Icons.Default.ArrowDropDown, contentDescription =null ,
                            modifier = Modifier.clickable {
                                isSortByName = !isSortByName
                                viewModel.changeSorting()
                            }
                        )
                        Text(
                            text = "All Contacts",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(

                onClick = {
                navController.navigate(Routes.AddContact.route)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "null",  modifier = Modifier.size(32.dp))
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            items(state.contact)
            {
              Contectcard(data = it, state = state, viewModel = viewModel)

               }
            }
        }
    }



@Composable
fun Contectcard(
    state: ContactState,
    data: Contact,
   viewModel: ContacsViewModel,
//    launcher : ManagedActivityResultLauncher<String, Uri?>,
//    onEvent: () -> Unit
) {

    var bitmapImage: Bitmap? = null

    if (data.image != null)
        bitmapImage = BitmapFactory.decodeByteArray(data.image, 0, data.image.size)


    val context = LocalContext.current
    var dialogShow by rememberSaveable { mutableStateOf(false) }
    var dropDownShow by rememberSaveable { mutableStateOf(false) }
    Card(

        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(
                width = 2.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(10.dp)
            )
            .shadow(
                elevation = 5.dp,
                spotColor = Color.Black,
                shape = RoundedCornerShape(10.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (bitmapImage != null) {
                Image(
                    bitmap = bitmapImage.asImageBitmap(),
                    contentDescription = "Contact Image",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(
                            shape = CircleShape
                        ),
                    contentScale = ContentScale.Crop
                )
            } else {
               ProfileImage(data.name!!)

            }

            Column( modifier = Modifier.padding(start = 16.dp)) {
                Text(text = data.name!!, fontWeight = FontWeight.ExtraBold, color = Color.Black)
                Text(text = data.number!!, fontWeight = FontWeight.W500, fontSize = 15.sp, color = Color.LightGray)
                Text(text = data.email!!)
            }
            Column(
                modifier = Modifier
                    .padding(start = 22.dp, end = 8.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                Column(
                ) {

                    Image(imageVector = Icons.Filled.MoreVert, contentDescription =null,
                        modifier = Modifier.clickable {
                            dropDownShow = true
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    DropdownMenu(
                        modifier = Modifier.background(color = Color.White),
                        expanded = dropDownShow, onDismissRequest = {
                            dropDownShow = false
                        }) {

                        DropdownMenuItem(
                            text = {

                                Column(
                                    modifier = Modifier.padding(4.dp)

                                ) {

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.clickable {
                                            state.id.value = data.id
                                            state.name.value = data.name!!
                                            state.email.value = data.email!!
                                            state.number.value = data.number!!
                                            state.image.value = data.image!!
                                            dropDownShow = false

                                        }
                                    ) {
                                        Image(imageVector = Icons.Filled.Update, contentDescription =null,
                                            modifier = Modifier.size(16.dp)
                                        )

                                        Text(
                                            text = " Update ",
                                            style = TextStyle(
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Normal
                                            ),
                                            modifier = Modifier.padding(start = 8.dp)
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.clickable {

                                            state.id.value = data.id
                                            state.name.value = data.name!!
                                            state.email.value = data.email!!
                                            state.number.value = data.number!!
                                            state.image.value = data.image!!
                                            viewModel.deleteContacts()
                                            Toast.makeText(
                                                context,
                                                "Contact Deleted Successfully",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                        }
                                    ) {

                                            Image(imageVector = Icons.Filled.Delete, contentDescription =null,

                                                modifier = Modifier.size(16.dp)
                                        )

                                        Text(
                                            text = " Delete ",
                                            style = TextStyle(
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Normal
                                            ),
                                            modifier = Modifier.padding(start = 8.dp)
                                        )
                                    }
                                }
                            },
                            onClick = {

                            }
                        )
                    }
                }
                Image(imageVector = Icons.Default.Call, contentDescription = null, modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse("tel:${data.number}")
                    context.startActivity(intent) })

                Image(imageVector = Icons.Default.MailOutline, contentDescription = null, modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("smsto:${data.number}")
                    context.startActivity(intent) })

//                Image(imageVector = Icons.Default.Whatsapp, contentDescription = null, modifier = Modifier.clickable {
//                    val uri = Uri.parse("smsto:${data.number}")
//                    val intent = Intent(Intent.ACTION_SENDTO,uri)
//                    intent.setPackage("com.whatsapp")
//                    context.startActivity(intent) })

            }

        }
    }

}