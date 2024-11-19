package com.madeit.shooters.presentation.shooters

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.madeit.shooters.data.model.GameItem
import com.madeit.shooters.presentation.WelcomeActivity.Companion.PLAYER_NAME
import com.madeit.shooters.databinding.ActivityShooterGamesBinding
import com.madeit.shooters.presentation.DetailsActivity
import kotlinx.coroutines.launch

class ShooterGamesActivity : AppCompatActivity() {
    private val binding by lazy { ActivityShooterGamesBinding.inflate(layoutInflater) }
    private lateinit var curPlayerName: String
    private val popularGamesAdapter = PopularGamesAdapter()
    private val gameViewModel by viewModels<GamesViewModel>()
    private lateinit var currentGame: GameItem

    companion object {
        const val curGameId = "com.madeit.shooters.presentation.activities.idOfGame"
        const val curGameName = "com.madeit.shooters.presentation.activities.nameOfGame"
        const val curGameGenre = "com.madeit.shooters.presentation.activities.GenreOfGame"
        const val curReleaseDate = "com.madeit.shooters.presentation.activities.releaseDateOfGame"
        const val curGameImage = "com.madeit.shooters.presentation.activities.imageOfGame"
        const val curGameDescription = "com.madeit.shooters.presentation.activities.descrOfGame"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getPlayerNameFromIntent()
        initRecyclerView()
        userSearchInput()
        setCollectors()
        onItemClickListener()
    }

    private fun setCollectors() {
        lifecycleScope.launch {
            gameViewModel.loading.collect { isLoading ->
                Log.d("Repository", "loading works ")
                binding.progressBarView.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
            }
        }
        lifecycleScope.launch {
            gameViewModel.allGamesFlow.collect {
                Log.d("activity", "$it")
                popularGamesAdapter.updateGamesList(it)
            }
        }
        lifecycleScope.launch {
            gameViewModel.filteredGamesFlow.collect { filteredGames ->
                popularGamesAdapter.updateGamesList(filteredGames)
            }
        }
        lifecycleScope.launch {
            gameViewModel.searchQuery.collect { query ->
                binding.tvTopFreeGames.visibility = if (query.isBlank()) View.VISIBLE else View.GONE
            }
        }
    }

    private fun userSearchInput(){
        binding.btnSearch.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                gameViewModel.updateSearchQuery(text?.toString() ?: "")
            }
            override fun afterTextChanged(text: Editable?) {}
        } )
    }

    private fun getPlayerNameFromIntent() {
        val intent = intent
        curPlayerName = intent.getStringExtra(PLAYER_NAME)!!
        binding.tvPlayerName.text = curPlayerName
    }

    private fun initRecyclerView() {
        binding.recyclerViewPopular.apply {
            adapter = popularGamesAdapter
            layoutManager = LinearLayoutManager(
                this@ShooterGamesActivity,
                LinearLayoutManager.VERTICAL, false
            )
        }
    }

    private fun onItemClickListener() {
        popularGamesAdapter.onItemClick = {game->
            currentGame = game
            val intent = Intent(this@ShooterGamesActivity, DetailsActivity::class.java)
            intent.putExtra(curGameId, currentGame.id)
            intent.putExtra(curGameName, currentGame.title)
            intent.putExtra(curReleaseDate, currentGame.releaseDate)
            intent.putExtra(curGameGenre, currentGame.genre)
            intent.putExtra(curGameDescription, currentGame.shortDescription)
            intent.putExtra(curGameImage, currentGame.thumbnail)
            startActivity(intent)
        }
    }


}