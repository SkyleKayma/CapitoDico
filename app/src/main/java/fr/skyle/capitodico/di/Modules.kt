package fr.skyle.capitodico.di


import com.google.firebase.crashlytics.FirebaseCrashlytics
import fr.skyle.capitodico.BuildConfig
import fr.skyle.capitodico.event.ForegroundBackgroundListener
import fr.skyle.capitodico.utils.PreferencesUtils
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


/**
 * Created by Openium on 20/03/2018.
 */

object Modules {

    val configModule = module {
        single {
            FirebaseCrashlytics.getInstance().apply {
                setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
            }
        }
    }

    val serviceModule = module {
        single {
            ForegroundBackgroundListener(androidContext())
        }
    }

    val utilsModule = module {
        single {
            PreferencesUtils(androidContext())
        }
    }

    val viewModelModule = module {
//        viewModel {
//            ViewModelMain(get())
//        }
    }
}