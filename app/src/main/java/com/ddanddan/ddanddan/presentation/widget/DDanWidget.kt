package com.ddanddan.ddanddan.presentation.widget

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.Action
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.layout.wrapContentSize
import androidx.glance.text.FontFamily
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.ddanddan.ddanddan.R
import com.ddanddan.ddanddan.presentation.MainActivity
import com.ddanddan.ddanddan.presentation.widget.DDanWidgetPreferences.CALORIES_VALUE
import com.ddanddan.ddanddan.presentation.widget.DDanWidgetPreferences.PET_LEVEL_VALUE
import com.ddanddan.ddanddan.presentation.widget.DDanWidgetPreferences.PET_TYPE_VALUE
import com.ddanddan.ddanddan.util.toAnimal
import com.ddanddan.domain.enums.PetTypeEnum

object DDanWidgetPreferences {
    val CALORIES_VALUE = intPreferencesKey("CALORIES_VALUE")
    val PET_TYPE_VALUE = stringPreferencesKey("PET_TYPE_VALUE")
    val PET_LEVEL_VALUE = intPreferencesKey("PET_LEVEL_VALUE")
}

class DDanWidget : GlanceAppWidget() {

    override suspend fun provideGlance (context: Context, id: GlanceId) {
        provideContent {
//            val caloriesValue = prefs.getInt("CALORIES_VALUE", 0)
            DdanWidgetLayout()
        }
    }

    fun createLaunchMainActivityAction(context: Context): Action {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        return actionStartActivity(intent)
    }

    @Composable
    fun DdanWidgetLayout() {
        val caloriesValue = currentState(key = CALORIES_VALUE) ?: 0
        val petTypeValue = currentState(key = PET_TYPE_VALUE) ?: "CAT"
        val petLevelValue = currentState(key = PET_LEVEL_VALUE) ?: 1

        Box(
            modifier = GlanceModifier
                .fillMaxWidth()
                .height(190.dp)
                .padding(12.dp)
                .cornerRadius(24.dp)
                .background(ColorProvider(Color(0xFF111111)))
                .clickable(onClick = createLaunchMainActivityAction(LocalContext.current)),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = GlanceModifier.fillMaxSize()
            ) {
                Row(
                    modifier = GlanceModifier
                        .wrapContentSize()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .cornerRadius(999.dp)
                        .background(ColorProvider(Color(0xFF212121))),
                    verticalAlignment = Alignment.Vertical.Bottom
                ) {
                    Text(
                        text = caloriesValue.toString(),
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = ColorProvider(Color(0xFFF5F5F5))
                        ),
                    )
                    Spacer(modifier = GlanceModifier.height(2.dp))
                    Text(
                        text = "kcal",
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = ColorProvider(Color(0xFFC9C9C9))
                        ),
                    )
                }
                Image(
                    modifier = GlanceModifier.defaultWeight(),
                    provider = ImageProvider(
                        resId = PetTypeEnum.valueOf(petTypeValue).toAnimal(petLevelValue)
                    ),
                    contentDescription = null
                )
                Spacer(modifier = GlanceModifier.height(5.dp))

                Row(
                    modifier = GlanceModifier
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = GlanceModifier
                            .defaultWeight()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .cornerRadius(999.dp)
                            .background(ColorProvider(Color(0xFF212121)))
                    ) {
                        Image(
                            modifier = GlanceModifier.fillMaxWidth(),
                            provider = ImageProvider(
                                resId = R.drawable.ic_action_apple
                            ),
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = GlanceModifier.width(6.dp))
                    Box(
                        modifier = GlanceModifier
                            .defaultWeight()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .cornerRadius(999.dp)
                            .background(ColorProvider(Color(0xFF212121)))
                    ) {
                        Image(
                            modifier = GlanceModifier.fillMaxWidth(),
                            provider = ImageProvider(
                                resId = R.drawable.ic_action_star
                            ),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}