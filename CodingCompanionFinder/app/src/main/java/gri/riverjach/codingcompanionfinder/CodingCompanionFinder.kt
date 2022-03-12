package gri.riverjach.codingcompanionfinder

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CodingCompanionFinder : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CodingCompanionFinder)
            modules(listOf(appModule, urlsModule))
        }
    }
}
