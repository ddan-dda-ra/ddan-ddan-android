package com.ddanddan.ddanddan.presentation.friends.fireworks

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ddanddan.ddanddan.R
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun FireworkEffect(
    modifier: Modifier = Modifier,
    onFinish: () -> Unit = {}
) {
    var particles by remember { mutableStateOf<List<Particle>>(emptyList()) }
    var particleIdCounter by remember { mutableStateOf(0) }
    var launchStartTime by remember { mutableStateOf(0L) }

    // 2초 동안 연속으로 불꽃 생성
    LaunchedEffect(Unit) {
        launchStartTime = System.currentTimeMillis()
        val launchDuration = 2000L // 2초

        while (System.currentTimeMillis() - launchStartTime < launchDuration) {
            val currentTime = System.currentTimeMillis()
            val newParticles = List(2) { // 한 번에 생성할 파티클
                val angleInDegrees = Random.nextFloat() * 60f - 30f // -60 ~ +60도
                val angleInRadians = Math.toRadians(angleInDegrees.toDouble()).toFloat()

                Particle(
                    id = particleIdCounter++,
                    angle = angleInRadians,
                    velocity = 500f + Random.nextFloat() * 300f,
                    rotation = Random.nextFloat() * 40f - 20f,
                    size = 0.7f + Random.nextFloat() * 0.6f,
                    startTime = currentTime,
                    launchStartTime = launchStartTime
                )
            }
            particles = particles + newParticles
            kotlinx.coroutines.delay(250) // 0.1초마다 생성
        }
        onFinish()
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        // 애니메이션 중인 파티클들
        particles.forEach { particle ->
            AnimatedParticle(
                particle = particle,
                onAnimationEnd = {
                    particles = particles.filter { it.id != particle.id }
                }
            )
        }
    }}

@Composable
private fun AnimatedParticle(
    particle: Particle,
    onAnimationEnd: () -> Unit
) {
    val totalDuration = 2000L // 버튼 누른 후 전체 2초

    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(particle.id) {
        // 버튼을 누른 시점부터 2초 후에 모든 불꽃이 사라지도록
        while (progress < 1f) {
            val elapsedFromLaunch = System.currentTimeMillis() - particle.launchStartTime
            progress = (elapsedFromLaunch.toFloat() / totalDuration).coerceIn(0f, 1f)
            kotlinx.coroutines.delay(16)
        }
        onAnimationEnd()
    }

    // 각 파티클이 생성된 시점부터의 경과 시간
    val elapsedFromCreation = (System.currentTimeMillis() - particle.startTime).coerceAtLeast(0L)
    val creationProgress = (elapsedFromCreation.toFloat() / totalDuration).coerceIn(0f, 1f)

    val offsetX = sin(particle.angle) * particle.velocity * creationProgress
    val heightScale = 0.5f // 절반 높이만
    val offsetY = -cos(particle.angle) * particle.velocity * creationProgress * heightScale


    val alpha = (1f - progress).coerceAtLeast(0f)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.BottomCenter)
            .offset(y = (-30).dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_flame),
            contentDescription = null,
            modifier = Modifier
                .size((48 * particle.size).dp)
                .graphicsLayer {
                    translationX = offsetX
                    translationY = offsetY
                    rotationZ = particle.rotation
                }
                .alpha(alpha)
        )
    }
}

data class Particle(
    val id: Int,
    val angle: Float,
    val velocity: Float,
    val rotation: Float,
    val size: Float,
    val startTime: Long,
    val launchStartTime: Long // 버튼을 누른 시점
)