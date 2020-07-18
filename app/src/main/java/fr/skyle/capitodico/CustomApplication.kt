package fr.skyle.capitodico

import android.app.Application
import androidx.preference.PreferenceManager
import fr.skyle.capitodico.di.Modules
import fr.skyle.capitodico.utils.JsonUtils
import fr.skyle.peoplover.log.FirebaseCrashlyticsTree
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Created by Openium on 20/03/2018.
 */

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CustomApplication)
            modules(
                listOf(
                    Modules.configModule,
                    Modules.serviceModule,
                    Modules.viewModelModule
                )
            )
        }

        // Init this for later use
        JsonUtils.init(applicationContext)

        initTimber()
        saveActualBuildCode()
    }

    // --- Init methods
    // ---------------------------------------------------

    fun initTimber() {
        Timber.plant(FirebaseCrashlyticsTree(get()))
    }

    private fun saveActualBuildCode() {
        PreferenceManager.getDefaultSharedPreferences(applicationContext).apply {
            val currentBuildVersion = BuildConfig.VERSION_CODE
            val oldBuildVersion = getInt(KEY_BUILD_CODE, 0)

            Timber.i("[UPDATE] Old build code : $oldBuildVersion | Current build code: $currentBuildVersion")
            edit().putInt(KEY_BUILD_CODE, currentBuildVersion).apply()
        }
    }

    // --- Other methods
    // ---------------------------------------------------

    companion object {
        const val KEY_BUILD_CODE = "KEY_BUILD_CODE"
    }
}