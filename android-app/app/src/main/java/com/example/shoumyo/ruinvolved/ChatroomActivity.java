package com.example.shoumyo.ruinvolved;

import com.example.shoumyo.ruinvolved.models.ChatMessage;
import com.google.firebase.FirebaseApp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.shoumyo.ruinvolved.ui.adapters.MessagesAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class ChatroomActivity extends AppCompatActivity {

    public static final String CLUB_ID_TAG = "club_id_tag";
    public static final String USERNAME_TAG = "username_tag";
    public static final String CLUB_NAME_TAG = "club_name_tag";


    private MessagesAdapter messagesAdapter;
    private DatabaseReference reference;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        listView = findViewById(R.id.list_of_messages);

        // create the adapter with messages from this club, then set it to listview
        messagesAdapter = new MessagesAdapter(null, ChatroomActivity.this);
        listView.setAdapter(messagesAdapter);


        // get username and club from intent
        Intent intent = getIntent();
        String username = intent.getStringExtra(USERNAME_TAG);
        int clubId = intent.getIntExtra(CLUB_ID_TAG, -1);
        String clubName = intent.getStringExtra(CLUB_NAME_TAG);

        getSupportActionBar().setTitle(clubName);


        // initialize the firebase app
        FirebaseApp.initializeApp(this);

        // store a ref to the database
        reference = FirebaseDatabase.getInstance().getReference();

        ImageButton sendBtn = findViewById(R.id.sendbtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = findViewById(R.id.input);

                String msg = input.getText().toString();

                reference.child(Integer.toString(clubId)).push()
                        .setValue(new ChatMessage(msg, username, clubId));

                input.setText("");

                displayChatMessages(clubId);
            }
        });


        // #logical
        reference.child(clubId + "").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                displayChatMessages(clubId);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        displayChatMessages(clubId);

    }


    /**
     * Displays all chat messages in list view, for a given clubID
     * @param clubId
     */
    private void displayChatMessages(int clubId) {



        final ArrayList<ChatMessage> messages = new ArrayList<>();

        // go to the child node with the name of club ID
        // who's children are all the messages
        reference.child(Integer.toString(clubId)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> dataSnapshotIterator = dataSnapshot.getChildren().iterator();

                // get all the messages under this club
                while(dataSnapshotIterator.hasNext()) {

                    DataSnapshot child = dataSnapshotIterator.next();
                    ChatMessage msg = child.getValue(ChatMessage.class);

                    messages.add(msg);
                }

                messagesAdapter.setMessages(messages);
                messagesAdapter.notifyDataSetChanged();

                // scroll to bottom
                listView.post(new Runnable() {
                    @Override
                    public void run() {
                        // Select the last row so it will scroll into view...
                        listView.setSelection(messagesAdapter.getCount() - 1);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

    }


}
