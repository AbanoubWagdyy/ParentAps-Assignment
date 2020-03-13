package com.parentAps.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.parentAps.App
import dagger.android.AndroidInjection

object AppInjector {

    fun init(application: App) {
        DaggerAppComponent.builder().application(application)
            .build().inject(application)

        application
            .registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    handleActivity(activity)
                }

                override fun onActivityStarted(activity: Activity) {

                }

                override fun onActivityResumed(activity: Activity) {

                }

                override fun onActivityPaused(activity: Activity) {

                }

                override fun onActivityStopped(activity: Activity) {

                }

                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {

                }

                override fun onActivityDestroyed(activity: Activity) {

                }
            })
    }

    private fun handleActivity(activity: Activity) {
        if (activity is Injectable) {
            AndroidInjection.inject(activity)
        }
    }
}