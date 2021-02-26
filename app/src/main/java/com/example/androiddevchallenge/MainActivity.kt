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

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.data.Puppy
import com.example.androiddevchallenge.data.puppies
import com.example.androiddevchallenge.ui.theme.PuppyAdoptionTheme
import com.example.androiddevchallenge.ui.theme.shapes

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            PuppyAdoptionTheme {
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        PuppyList(puppies = puppies, navController = navController)
                    }
                    composable(
                        "puppy/{puppyId}",
                        arguments = listOf(
                            navArgument("puppyId") {
                                type = NavType.IntType
                            }
                        )
                    ) { backStackEntry ->
                        PuppyDetail(id = backStackEntry.arguments?.getInt("puppyId") ?: 0)
                    }
                }
            }
        }
    }
}

@Composable
fun PuppyList(puppies: List<Puppy>, navController: NavController) {
    Scaffold {
        LazyColumn(modifier = Modifier.padding(10.dp)) {
            items(puppies.size) { index ->
                PuppyRow(puppies[index], navController)
            }
        }
    }
}

@Composable
fun PuppyRow(puppy: Puppy, navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(5.dp).clickable {
            navController.navigate("puppy/${puppy.id}")
        },
        shape = shapes.medium
    ) {
        Row {
            Image(
                bitmap = ImageBitmap.imageResource(id = puppy.photo),
                modifier = Modifier.size(90.dp),
                contentDescription = null
            )
            Column(
                modifier = Modifier.padding(top = 10.dp, start = 10.dp)
            ) {
                Text(puppy.name)
                Text(puppy.gender)
            }
        }
    }
}

@Composable
fun PuppyDetail(id: Int) {
    val puppy = puppies.find { it.id == id } ?: puppies[0]
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.TopCenter)) {
            Image(
                bitmap = ImageBitmap.imageResource(id = puppy.photo),
                modifier = Modifier.fillMaxWidth().aspectRatio(1.0f),
                contentDescription = null
            )
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = puppy.name,
                    fontSize = 21.sp,
                    modifier = Modifier.padding(vertical = 15.dp)
                )
                Text(text = puppy.desc)
            }
        }
        Button(
            onClick = {},
            modifier = Modifier.align(Alignment.BottomCenter).padding(10.dp)
        ) {
            Text(text = "Give a bone")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PuppyAdoptionTheme {
        PuppyDetail(10)
    }
}
