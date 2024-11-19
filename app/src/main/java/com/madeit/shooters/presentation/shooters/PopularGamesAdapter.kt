package com.madeit.shooters.presentation.shooters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.madeit.shooters.data.model.GameItem
import com.madeit.shooters.databinding.ItemAllGamesBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class PopularGamesAdapter : RecyclerView.Adapter<PopularGamesAdapter.PopularGamesViewHolder>() {

    private var gameList = listOf<GameItem>()

    var onItemClick: (game: GameItem) -> Unit = {}

    fun updateGamesList(newGameList: List<GameItem>) {
        val gameCallBack = GameCallBack(gameList, newGameList)
        val diffResult = DiffUtil.calculateDiff(gameCallBack)
        gameList = newGameList
        diffResult.dispatchUpdatesTo(this)
        Log.d("recyclerview","ADAPTER UODATED")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularGamesViewHolder {
        return PopularGamesViewHolder(
            ItemAllGamesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PopularGamesViewHolder, position: Int) {
        holder.bind(game = gameList[position])
    }

    override fun getItemCount(): Int = gameList.size

    class GameCallBack(
        private val oldList: List<GameItem>,
        private val newList: List<GameItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oList = oldList[oldItemPosition]
            val nList = newList[newItemPosition]
            return oList.id == nList.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oList = oldList[oldItemPosition]
            val nList = newList[newItemPosition]
            return oList == nList
        }
    }

    inner class PopularGamesViewHolder(private val binding: ItemAllGamesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(game: GameItem) = with(binding) {
            tvTitle.text = game.title
            tvGenre.text = game.genre
            val originalFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val targetFormat = SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH)

            Glide.with(itemImage)
                .load(game.thumbnail)
                .into(itemImage)

            try {
                val date = game.releaseDate.let { originalFormat.parse(it) }
                tvReleaseDate.text = date?.let { targetFormat.format(it) }
            } catch (e: ParseException) {
                tvReleaseDate.text = game.releaseDate
            }

            root.setOnClickListener {
                onItemClick.invoke(game)
            }
        }
    }

}