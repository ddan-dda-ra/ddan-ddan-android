package com.ddanddan.ddanddan.presentation.widget

import android.content.Context
import android.util.Log
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.glance.state.PreferencesGlanceStateDefinition
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WidgetManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun updateWidgetCalories(calories: Int) {
        try {
            val glanceIds = GlanceAppWidgetManager(context).getGlanceIds(DDanWidget::class.java)

            glanceIds.forEach { glanceId ->
                updateAppWidgetState(
                    context = context,
                    definition = PreferencesGlanceStateDefinition,
                    glanceId = glanceId
                ) { prefs ->
                    prefs.toMutablePreferences().apply {
                        this[DDanWidgetPreferences.CALORIES_VALUE] = calories
                    }
                }
            }

            DDanWidget().updateAll(context)
        } catch (e: Exception) {
            Log.e("WidgetManager", "위젯 업데이트 실패", e)
        }
    }

    suspend fun updateWidgetPet(petType: String, petLevel: Int) {
        try {
            val glanceIds = GlanceAppWidgetManager(context).getGlanceIds(DDanWidget::class.java)

            glanceIds.forEach { glanceId ->
                updateAppWidgetState(
                    context = context,
                    definition = PreferencesGlanceStateDefinition,
                    glanceId = glanceId
                ) { prefs ->
                    prefs.toMutablePreferences().apply {
                        this[DDanWidgetPreferences.PET_TYPE_VALUE] = petType
                        this[DDanWidgetPreferences.PET_LEVEL_VALUE] = petLevel
                    }
                }
            }

            DDanWidget().updateAll(context)
        } catch (e: Exception) {
            Log.e("WidgetManager", "위젯 업데이트 실패", e)
        }
    }
}