package com.globant.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.globant.di.useCasesModule
import com.globant.di.viewModelsModule
import com.globant.domain.entities.MarvelCharacter
import com.globant.ui.utils.Data
import com.globant.ui.utils.Status
import com.globant.ui.viewmodels.CharacterViewModel
import com.globant.myapplication.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<CharacterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.mainState.observe(::getLifecycle, ::updateUI)

        buttonSearchRemote.setOnClickListener { onSearchRemoteClicked() }
        buttonSearchLocal.setOnClickListener { onSearchLocalClicked() }
    }

    private fun updateUI(characterData: Data<MarvelCharacter>) {
        when (characterData.responseType) {
            Status.ERROR -> {
                //Error handling
                hideProgress()
                characterData.error?.message?.let { showMessage(it) }
                characterData.data?.let { setCharacter(it) }
            }
            Status.LOADING -> {
                //Progress
                showProgress()
            }
            Status.SUCCESSFUL -> {
                // On Successful response
                hideProgress()
                characterData.data?.let { setCharacter(it) }
            }
        }
    }

    private fun showProgress() {
        progress.visibility = View.VISIBLE
        textViewDetails.visibility = View.GONE
    }

    private fun hideProgress() {
        progress.visibility = View.GONE
        textViewDetails.visibility = View.VISIBLE
    }

    private fun setCharacter(character: MarvelCharacter) {
        textViewDetails.text = character.description
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun onSearchRemoteClicked() {
        viewModel.onSearchRemoteClicked(characterID.text.toString().toInt())
    }
    private fun onSearchLocalClicked() {
        viewModel.onSearchLocalClicked(characterID.text.toString().toInt())
    }
}
