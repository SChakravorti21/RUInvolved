package com.example.shoumyo.ruinvolved;

import com.example.shoumyo.ruinvolved.models.ChatMessage;
import com.google.firebase.FirebaseApp;

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

    private MessagesAdapter messagesAdapter;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);


        // temp
        long clubId = 1;
        String userName = "john";


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

                reference.child(Long.toString(clubId)).push()
                        .setValue(new ChatMessage(msg, userName, clubId));

                input.setText("");
            }
        });

        displayChatMessages(clubId);

    }


    /**
     * Displays all chat messages in list view, for a given clubID
     * @param clubId
     */
    private void displayChatMessages(long clubId) {

        final ListView listView = findViewById(R.id.list_of_messages);

        final ArrayList<ChatMessage> messages = new ArrayList<>();

        // go to the child node with the name of club ID
        // who's children are all the messages
        reference.child(Long.toString(clubId)).addListenerForSingleValueEvent(new ValueEventListener() {
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
