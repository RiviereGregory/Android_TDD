package gri.riverjach.punchline.app

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import gri.riverjach.punchline.MainActivity
import gri.riverjach.punchline.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Splash Screen with the app icon and name at the center, this is also the launch screen and
 * opens up in fullscreen mode. Once launched it waits for 2 seconds after which it opens the
 * MainActivity
 */
class SplashActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    makeFullScreen()

    setContentView(ActivitySplashBinding.inflate(layoutInflater).root)

    // Using a coroutine to delay loading the MainActivity
    lifecycleScope.launch {
      delay(2000)
      // Start activity
      startActivity(Intent(this@SplashActivity, MainActivity::class.java))

      // Animate the loading of new activity
      overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

      // Close this activity
      finish()

    }
  }

  private fun makeFullScreen() {
    // Remove Title
    requestWindowFeature(Window.FEATURE_NO_TITLE)

    // Make Fullscreen
    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN)

    // Hide the toolbar
    supportActionBar?.hide()
  }
}
