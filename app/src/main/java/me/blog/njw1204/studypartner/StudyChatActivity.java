package me.blog.njw1204.studypartner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class StudyChatActivity extends AppCompatActivity {

    private ArrayList<ChatItem> items;
    private String CHAT_NAME;
    private String USER_ID;
    private String USER_NICK;

    private RecyclerView chat_list;
    private ChatRecyclerAdapter adapter;
    private EditText chat_edit;
    private Button chat_send;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_chat);

        Intent intent = getIntent();
        CHAT_NAME = intent.getStringExtra("chatname");
        USER_ID = intent.getStringExtra("userid");
        USER_NICK = intent.getStringExtra("nick");

        items = new ArrayList<>();
        chat_list = findViewById(R.id.recyclerview_chat);
        adapter = new ChatRecyclerAdapter(items, USER_ID);
        chat_list.setAdapter(adapter);
        chat_list.setLayoutManager(new LinearLayoutManager(this));

        LinearLayoutManager layoutManager = new LinearLayoutManager(StudyChatActivity.this);
        layoutManager.setStackFromEnd(true);
        chat_list.setLayoutManager(layoutManager);

        chat_edit = findViewById(R.id.editText_send);
        chat_send = findViewById(R.id.button_send);
        // pd = CUtils.showProgress(this, "로딩 중...", false);

        openChat(CHAT_NAME);

        chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chat_edit.getText().toString().equals("")) return;
                ChatDTO chat = new ChatDTO(USER_ID, USER_NICK, chat_edit.getText().toString());
                databaseReference.child("chat").child(CHAT_NAME).push().setValue(chat);
                chat_edit.setText("");
            }
        });
    }

    private void addMessage(DataSnapshot dataSnapshot) {
        ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
        items.add(new ChatItem(chatDTO.getUserId(), chatDTO.getUserNick(), chatDTO.getMessage()));
        chat_list.setAdapter(adapter);
    }

    private void openChat(String chatName) {
        databaseReference.child("chat").child(chatName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                addMessage(dataSnapshot);
                findViewById(R.id.textview_not_chat).setVisibility(View.GONE);
                pd = null;
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
