/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.model.Dog

const val SELECTED_DOG = "selected_dog"
const val SELECTED_INDEX = "selected_index"
const val ADOPTED = "adopted"

/**
 * Dog Detail
 * @author Q yang
 * @since 2021/02/25
 */
class DogDetailActivity : BaseActivity() {

    private lateinit var selectedDog: Dog
    private var selectedPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarToLight()
        val dog = intent.getParcelableExtra<Dog>(SELECTED_DOG)
        selectedPosition = intent.getIntExtra(SELECTED_INDEX, 0)
        if (dog == null) {
            Toast.makeText(this, "Error in data", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        selectedDog = dog
        setContent {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = selectedDog.name
                            )
                        },
                        backgroundColor = Color.Transparent, elevation = 0.dp,
                        navigationIcon = {
                            IconButton(onClick = { navigateBack() }) {
                                val backIcon: Painter = painterResource(R.drawable.ic_back)
                                Icon(painter = backIcon, contentDescription = "ic_back")
                            }
                        }
                    )
                }
            ) {
                DisplayDogDetail(dog = selectedDog)
            }
        }
    }

    override fun onBackPressed() {
        navigateBack()
    }

    private fun navigateBack() {
        val intent = Intent()
        intent.putExtra(SELECTED_INDEX, selectedPosition)
        intent.putExtra(ADOPTED, selectedDog.adopted)
        setResult(RESULT_OK, intent)
        finish()
    }
}

var showConfirmDialog by mutableStateOf(false)

@Composable
fun DisplayDogDetail(dog: Dog) {
    val stateDog by remember { mutableStateOf(dog) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(
            modifier = Modifier.requiredHeight(26.dp)
        )
        DogAvatar(
            avatar = stateDog.avatar,
            name = stateDog.name
        )
        Spacer(
            modifier = Modifier.requiredHeight(26.dp)
        )
        DogIntroduction(
            introduction = stateDog.introduction
        )
        Spacer(
            modifier = Modifier.requiredHeight(26.dp)
        )
        AdoptButton(
            adopted = stateDog.adopted
        )
        Spacer(
            modifier = Modifier.requiredHeight(26.dp)
        )
    }
    if (showConfirmDialog) {
        AdoptConfirmDialog(dog = stateDog)
    }
}

@Composable
fun DogAvatar(avatar: String, name: String) {
    val imageIdentity = DogApp.context.resources.getIdentifier(
        avatar, "drawable",
        DogApp.context.packageName
    )
    val image: Painter = painterResource(imageIdentity)
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = image,
            contentDescription = name,
            modifier = Modifier
                .requiredSize(300.dp)
                .clip(shape = RoundedCornerShape(5.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun DogIntroduction(introduction: String) {
    Text(
        text = introduction,
        fontSize = 18.sp,
        style = MaterialTheme.typography.body1
    )
}

@Composable
fun AdoptButton(adopted: Boolean) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 0.dp, 10.dp, 0.dp),
        onClick = { showConfirmDialog = true },
        enabled = !adopted
    ) {
        Text(text = if (adopted) "Adopted" else "Adopt")
    }
}

@Composable
fun AdoptConfirmDialog(dog: Dog) {
    AlertDialog(
        onDismissRequest = {
            showConfirmDialog = false
        },
        text = {
            Text(
                text = "Have you decided to adopt this dog?",
                style = MaterialTheme.typography.body1
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    showConfirmDialog = false
                    dog.adopted = true
                }
            ) {
                Text(
                    text = "Adopt"
                )
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    showConfirmDialog = false
                }
            ) {
                Text(
                    text = "No"
                )
            }
        }
    )
}
