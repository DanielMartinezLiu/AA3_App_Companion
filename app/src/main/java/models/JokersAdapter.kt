package models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.enti.app_companion.R

class JokersAdapter(private val _jokers: List<JokersModel>) : RecyclerView.Adapter<JokersAdapter.JokersViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokersAdapter.JokersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_layout_new_manager, parent, false);

        return JokersViewHolder(view);
    }

    override fun onBindViewHolder(holder: JokersAdapter.JokersViewHolder, position: Int) {
        val jokers = _jokers[position];
        holder.bind(jokers);
    }

    override fun getItemCount(): Int {
        return _jokers.size;
    }

    class JokersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private val newsTitle : TextView = itemView.findViewById(R.id.news_title);
        private val newsImage : ImageView = itemView.findViewById(R.id.news_image);

        fun bind(jokersModel: JokersModel)
        {
            newsTitle.text = jokersModel.title;
            newsImage.setImageResource(jokersModel.imageResId);
        }
    }
}