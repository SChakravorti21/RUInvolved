package com.example.shoumyo.ruinvolved;

import com.example.shoumyo.ruinvolved.models.ChatMessage;
import com.google.firebase.FirebaseApp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.shoumyo.ruinvolved.utils.MessagesAdapter;
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

    private MessagesAdapter messagesAdapter;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        // get username and club from intent
        Intent intent = getIntent();
        String username = intent.getStringExtra(USERNAME_TAG);
        int clubId = intent.getIntExtra(CLUB_ID_TAG, -1);



        // initialize the firebase app
        FirebaseApp.initializeApp(this);

        // store a ref to the database
        reference = FirebaseDatabase.getInstance().getReference();

        Button sendBtn = findViewById(R.id.sendbtn);
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

        displayChatMessages(clubId);

    }


    /**
     * Displays all chat messages in list view, for a given clubID
     * @param clubId
     */
    private void displayChatMessages(int clubId) {

        final ListView listView = findViewById(R.id.list_of_messages);

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

                messagesAdapter = new MessagesAdapter(messages, ChatroomActivity.this);
                listView.setAdapter(messagesAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

    }


}
