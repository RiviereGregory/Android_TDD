package gri.riverjach.codingcompanionfinder

import android.app.Application
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.error.KoinAppAlreadyStartedException

class CodingCompanionFinder : Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            startKoin {
                androidContext(this@CodingCompanionFinder)
                modules(listOf(appModule, urlsModule))
            }
        } catch (koinAlreadyStartedException: KoinAppAlreadyStartedException) {
            Log.i(
                "CodingCompanionFinder",
                "KoinAppAlreadyStartedException, should only happen in tests"
            )
        }
    }

}
