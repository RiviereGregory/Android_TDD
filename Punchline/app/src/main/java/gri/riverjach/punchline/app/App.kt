package gri.riverjach.punchline.app

import android.app.Application
import gri.riverjach.punchline.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class App : Application(){
  override fun onCreate() {
    super.onCreate()
    if (GlobalContext.getOrNull() == null) {
      startKoin {
        // declare Android context
        androidContext(this@App)
        // declare used modules
        modules(appModule)
      }
    }
  }
}