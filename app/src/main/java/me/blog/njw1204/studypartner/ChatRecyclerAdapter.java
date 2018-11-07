package me.blog.njw1204.studypartner;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ChatItem> items;
    private String myId;

    private int NORMAL_MESSAGE = 1;
    private int MY_MESSAGE = 2;

    public ChatRecyclerAdapter(ArrayList<ChatItem> items, String myId) {
        this.items = items;
        this.myId = myId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        if (i == NORMAL_MESSAGE) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_message, viewGroup, false);
            return new ChatItemViewHolder(v);
        }
        else {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_message2, viewGroup, false);
            return new ChatItemViewHolder2(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder chatItemViewHolder, int i) {
        if (chatItemViewHolder.getItemViewType() == NORMAL_MESSAGE) {
            ChatItemViewHolder holder = (ChatItemViewHolder) chatItemViewHolder;
            holder.nick.setText(items.get(i).getNick());
            holder.message.setText(items.get(i).getMessage());
        }
        else {
            ChatItemViewHolder2 holder2 = (ChatItemViewHolder2) chatItemViewHolder;
            holder2.myMessage.setText(items.get(i).getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).getId().equals(myId)) {
            return MY_MESSAGE;
        }
        else {
            return NORMAL_MESSAGE;
        }
    }

    public class ChatItemViewHolder extends RecyclerView.ViewHolder {
        private TextView nick;
        private TextView message;

        public ChatItemViewHolder(@NonNull View itemView) {
            super(itemView);
            nick = itemView.findViewById(R.id.nick_chatmessage);
            message = itemView.findViewById(R.id.text_chatmessage);
        }
    }

    public class ChatItemViewHolder2 extends RecyclerView.ViewHolder {
        private TextView myMessage;

        public ChatItemViewHolder2(@NonNull View itemView) {
            super(itemView);
            myMessage = itemView.findViewById(R.id.text_chatmessage2);
        }
    }
}