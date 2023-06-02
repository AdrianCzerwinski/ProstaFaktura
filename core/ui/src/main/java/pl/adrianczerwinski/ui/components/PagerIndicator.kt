package pl.adrianczerwinski.ui.components

import androidx.compose.animation.core.EaseInOutBounce
import androidx.compose.animation.core.InternalAnimationApi
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(InternalAnimationApi::class)
@Composable
fun PagerIndicator(
    currentPage: Int,
    pageCount: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = MaterialTheme.colorScheme.onBackground,
    inactiveColor: Color = defaultIndicatorInactiveColor,
    indicatorSize: Dp = 6.dp,
    indicatorSpacing: Dp = indicatorSize,
    indicatorShape: Shape = CircleShape
) {
    val transition = updateTransition(currentPage, label = null)
    val indicatorOffset by transition.animateFloat(
        label = "Indicator Transition Offset",
        transitionSpec = {
            tween(durationMillis = 200, easing = EaseInOutBounce)
        }
    ) { page ->
        page * (indicatorSize + indicatorSpacing).value
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(indicatorSpacing)
        ) {
            repeat(pageCount) { pageIndex ->
                val color = if (pageIndex == currentPage && transition.isSeeking) activeColor else inactiveColor
                Surface(
                    modifier = Modifier
                        .size(indicatorSize)
                        .clip(indicatorShape),
                    color = color
                ) {}
            }
        }

        Box(
            Modifier
                .offset(x = indicatorOffset.dp)
                .size(indicatorSize)
                .clip(indicatorShape)
                .background(activeColor)
        )
    }
}

private val defaultIndicatorInactiveColor = Color(0xFFB6B6B6)
