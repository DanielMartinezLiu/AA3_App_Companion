package models
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.enti.app_companion.R

class ChatUserAdapter (private val _chats: List<String>) : RecyclerView.Adapter<ChatUserAdapter.ChatsUserViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatUserAdapter.ChatsUserViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_layout_user_chat_manager, parent, false);
        return ChatsUserViewHolder(view);
    }

    override fun onBindViewHolder(holder: ChatUserAdapter.ChatsUserViewHolder, position: Int)
    {
        val chats = _chats[position];
        holder.bind(chats);
    }

    override fun getItemCount(): Int
    {
        return _chats.size;
    }

    class ChatsUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private val newText : TextView = itemView.findViewById(R.id.text);

        fun bind(chatModel: String)
        {
            newText.text = chatModel;
        }
    }
}