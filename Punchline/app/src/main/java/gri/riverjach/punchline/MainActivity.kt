package gri.riverjach.punchline

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import gri.riverjach.punchline.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.state.observe(this, { uiModel ->
            render(uiModel)
        })
        viewModel.getJoke()
        binding.buttonNewJoke.setOnClickListener {
            viewModel.getJoke()
        }
    }

    private fun render(uiModel: UiModel) {
        when (uiModel) {
            is UiModel.Success -> showJoke(uiModel.joke)
            is UiModel.Error -> showError(uiModel.error)
        }
    }

    private fun showJoke(joke: Joke) {
        binding.textJoke.text = joke.joke
    }

    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }
}

