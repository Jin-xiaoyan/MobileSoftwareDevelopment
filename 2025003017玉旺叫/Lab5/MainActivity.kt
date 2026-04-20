package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ArtSpaceApp()
            }
        }
    }
}


@Composable
fun ArtSpaceApp() {
    var currentArtwork by remember { mutableIntStateOf(1) }

    // when表达式：对应你3张图片资源
    val imageResource = when (currentArtwork) {
        1 -> R.drawable.artwork_1
        2 -> R.drawable.artwork_2
        else -> R.drawable.artwork_3
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ArtworkWall(imageResource)

        Spacer(modifier = Modifier.size(16.dp))

        ArtworkDescriptor(currentArtwork)

        Spacer(modifier = Modifier.size(24.dp))

        DisplayController(
            onPrevious = {
                currentArtwork = when (currentArtwork) {
                    1 -> 3
                    else -> currentArtwork - 1
                }
            },
            onNext = {
                currentArtwork = when (currentArtwork) {
                    3 -> 1
                    else -> currentArtwork + 1
                }
            }
        )
    }
}


@Composable
fun ArtworkWall(imageRes: Int) {
    Surface(
        modifier = Modifier
            .size(width = 320.dp, height = 400.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun ArtworkDescriptor(artworkId: Int) {
    val (title, artist, year) = when (artworkId) {
        1 -> Triple("星月夜", "文森特·梵高", "1889")
        2 -> Triple("丰收的普罗旺斯田野", "文森特·梵高", "1888")
        else -> Triple("树下行人", "文森特·梵高", "1889")
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "$artist ($year)",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}



@Composable
fun DisplayController(onPrevious: () -> Unit, onNext: () -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        Button(onClick = onPrevious) {
            Text("Previous")
        }
        Button(onClick = onNext) {
            Text("Next")
        }
    }
}