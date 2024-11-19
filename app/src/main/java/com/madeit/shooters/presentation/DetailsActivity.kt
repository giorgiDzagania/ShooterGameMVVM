package com.madeit.shooters.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.madeit.shooters.R
import com.madeit.shooters.databinding.ActivityDetailsBinding
import com.madeit.shooters.presentation.shooters.ShooterGamesActivity

class DetailsActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailsBinding.inflate(layoutInflater) }
    private lateinit var gameId: String
    private lateinit var gameName: String
    private lateinit var gameGenre: String
    private lateinit var gameReleaseDate: String
    private lateinit var gameDescription: String
    private lateinit var gameImage: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        showProgress(true)
        getCurrentGameInfo()
        displayGameInfo()
    }

    private fun getCurrentGameInfo() {
        val intent = intent
        gameId = intent.getStringExtra(ShooterGamesActivity.curGameId) ?: ""
        gameName = intent.getStringExtra(ShooterGamesActivity.curGameName) ?: ""
        gameGenre = intent.getStringExtra(ShooterGamesActivity.curGameGenre) ?: ""
        gameReleaseDate = intent.getStringExtra(ShooterGamesActivity.curReleaseDate) ?: ""
        gameDescription = intent.getStringExtra(ShooterGamesActivity.curGameDescription) ?: ""
        gameImage = intent.getStringExtra(ShooterGamesActivity.curGameImage) ?: ""
    }

    private fun displayGameInfo() {
        binding.tvGenre.text = gameGenre
        binding.tvDate.text = gameReleaseDate
        binding.tvDescription.text = gameDescription
        Glide.with(this)
            .load(gameImage)
            .into(binding.curGameImage)
        binding.collapsingToolbar.title = gameName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
        showProgress(false)
    }

    private fun showProgress(show: Boolean) {
        binding.linearProgressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

}