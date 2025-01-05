package models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.enti.app_companion.R

class ChatAdapter (private val _chats: List<ChatModel>, private val onItemClick : (ChatModel) -> Unit) : RecyclerView.Adapter<ChatAdapter.ChatsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ChatsViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_layout_chat_manager, parent, false);
        return ChatsViewHolder(view, onItemClick);
    }

    override fun onBindViewHolder(holder: ChatAdapter.ChatsViewHolder, position: Int)
    {
        val chats = _chats[position];
        holder.bind(chats);
    }

    override fun getItemCount(): Int
    {
        return _chats.size;
    }

    class ChatsViewHolder(itemView: View, private val onItemClick: (ChatModel) -> Unit) : RecyclerView.ViewHolder(itemView)
    {
        private val chatImage : ImageView = itemView.findViewById(R.id.chat_image);
        private val username : TextView = itemView.findViewById(R.id.username);
        private val newText : TextView = itemView.findViewById(R.id.new_text);

        fun bind(chatModel: ChatModel)
        {
            chatImage.setImageResource(chatModel.imageResId);
            username.text = chatModel.username;
            newText.text = chatModel.newText;

            itemView.setOnClickListener {
                onItemClick(chatModel)
            }
        }
    }
}