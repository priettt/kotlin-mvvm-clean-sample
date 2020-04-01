package com.globant.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.globant.domain.entities.MarvelCharacter
import com.globant.utils.Data
import com.globant.utils.Status
import com.globant.viewmodels.CharacterViewModel
import com.globant.myapplication.R
import com.globant.utils.Event
import com.globant.utils.MINUS_ONE
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<CharacterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.mainState.observe(::getLifecycle, ::updateUI)

        buttonSearchRemote.setOnClickListener { onSearchRemoteClicked() }
        buttonSearchLocal.setOnClickListener { onSearchLocalClicked() }
    }

    private fun updateUI(characterData: Event<Data<MarvelCharacter>>) {
        // in this case, we need to use peekContent because we use this several times to update the UI
        // in case that we will only use the characterData one time we have to use getContentIfNotHandled
        when (characterData.peekContent().responseType) {
            Status.ERROR -> {
                hideProgress()
                characterData.peekContent().error?.message?.let { showMessage(it) }
                textViewDetails.text = getString(R.string.no_character)
            }
            Status.LOADING -> {
                showProgress()
            }
            Status.SUCCESSFUL -> {
                hideProgress()
                characterData.peekContent().data?.let { setCharacter(it) }
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
        val id = if (characterID.text.toString().isNotEmpty()) {
            characterID.text.toString().toInt()
        } else {
            MINUS_ONE
        }
        viewModel.onSearchRemoteClicked(id)
    }
    private fun onSearchLocalClicked() {
        val id = if (characterID.text.toString().isNotEmpty()) {
            characterID.text.toString().toInt()
        } else {
            MINUS_ONE
        }
        viewModel.onSearchLocalClicked(id)
    }
}
