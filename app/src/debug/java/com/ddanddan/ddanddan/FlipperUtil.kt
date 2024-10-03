package com.ddanddan.ddanddan

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import okhttp3.Interceptor

object FlipperUtil {
    private val networkFlipperPlugin = NetworkFlipperPlugin()
    val flipperInterceptor: Interceptor
        get() = FlipperOkhttpInterceptor(networkFlipperPlugin)

    fun init(app: Application) {
        try {

            SoLoader.init(app, false)
            if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(app)) {
                val client = AndroidFlipperClient.getInstance(app)
                client.addPlugin(InspectorFlipperPlugin(app, DescriptorMapping.withDefaults()))
                client.addPlugin(networkFlipperPlugin)
                client.addPlugin(DatabasesFlipperPlugin(app))
                client.addPlugin(SharedPreferencesFlipperPlugin(app, "USER_DATA"))
                client.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}