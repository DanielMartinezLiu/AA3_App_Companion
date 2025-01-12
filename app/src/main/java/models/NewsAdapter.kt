package models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.enti.app_companion.R

class NewsAdapter(private val _news: List<NewsModel>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_layout_new_manager, parent, false);

        return NewsViewHolder(view);
    }

    override fun onBindViewHolder(holder: NewsAdapter.NewsViewHolder, position: Int) {
        val news = _news[position];
        holder.bind(news);
    }

    override fun getItemCount(): Int {
        return _news.size;
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private val newsTitle : TextView = itemView.findViewById(R.id.news_title);
        private val newsImage : ImageView = itemView.findViewById(R.id.news_image);

        fun bind(newsModel: NewsModel)
        {
            newsTitle.text = newsModel.title;
            newsImage.setImageResource(newsModel.imageResId);
        }
    }
}