package models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.enti.app_companion.R

class MarvelAdapter(private val _marvel: List<MarvelCharacter>) : RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelAdapter.MarvelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_layout_api, parent, false);

        return MarvelViewHolder(view);
    }

    override fun onBindViewHolder(holder: MarvelAdapter.MarvelViewHolder, position: Int) {
        val marvel = _marvel[position]
        holder.bind(marvel)
    }

    override fun getItemCount(): Int {
        return _marvel.size;
    }

    class MarvelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private val characterName : TextView = itemView.findViewById(R.id.news_title);
        private val characterDescription : TextView = itemView.findViewById(R.id.news_title);
        private val characterImage : ImageView = itemView.findViewById(R.id.news_image);

        fun bind(marvelCharacter: MarvelCharacter)
        {
            characterName.text = marvelCharacter.name;
            characterDescription.text = marvelCharacter.description;
            characterImage.setImageResource(marvelCharacter.imageResId);
        }
    }
}