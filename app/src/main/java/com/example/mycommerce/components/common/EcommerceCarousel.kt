package com.example.mycommerce.components.common

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycommerce.data.frDatabase.eCommerceItemsList
import kotlinx.coroutines.delay

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier = Modifier) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(pageCount) {
            IndicatorDots(isSelected = it == currentPage, modifier = modifier)
        }
    }
}

@Composable
fun IndicatorDots(isSelected: Boolean, modifier: Modifier) {
    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 10.dp, label = "")
    Box(
        modifier = modifier
            .padding(2.dp)
            .size(size.value)
            .clip(CircleShape)
            .background(if (isSelected) Color(0xff373737) else Color(0xA8373737))
    )
}

@Preview
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppImageSlider(modifier: Modifier = Modifier) {

    // Select any 5 items from the list randomly
    val randomList = eCommerceItemsList.shuffled().take(5)

    val pagerState = rememberPagerState(pageCount = { randomList.size })

    LaunchedEffect(Unit) {
        while (true) {
            delay(2000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.scrollToPage(nextPage)
        }
    }


    HorizontalPager(
        state = pagerState, modifier

    ) { currentPage ->
        val item = randomList[currentPage]

        Box(modifier = Modifier) {

            Card(
                Modifier.fillMaxSize(), elevation = CardDefaults.cardElevation(8.dp)
            ) {
                CommonImage(
                    url = item.imageUrl,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.itemName, fontSize = 16.sp, fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "$${item.itemPrice}", fontSize = 16.sp, fontWeight = FontWeight.Bold
                    )
                }
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = "Rating: ${item.itemRating}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        PageIndicator(
            pageCount = randomList.size, currentPage = pagerState.currentPage,
        )
    }
}