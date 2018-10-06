package com.example.atharva.chatroomtest;

import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private FirebaseListAdapter<ChatMessage> adapter;
    RelativeLayout activity_main;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        activity_main = findViewById(R.id.activity_main);

        Button sendbtn = findViewById(R.id.sendbtn);

        FirebaseApp.initializeApp(this);

        reference = FirebaseDatabase.getInstance().getReference();

        reference.push().child("test").push().setValue("hihi");

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText input = findViewById(R.id.input);
                String msg = input.getText().toString();

                Log.d("doritos", "message :" + msg);
                reference.push()  //automatically generates new key with push
                        .setValue(new ChatMessage(msg, "user1", 10)
                        );

                input.setText("");
            }


        });


        displayChatMessages();

    }


    private void displayChatMessages() {

        final long clubId = 1;


        final ListView messageList = findViewById(R.id.list_of_messages);


        Query query = reference;


        final ArrayList<ChatMessage> messagesForClub = new ArrayList<>();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> dataSnapshotIterator = dataSnapshot.getChildren().iterator();

                while(dataSnapshotIterator.hasNext()) {
                    DataSnapshot child = dataSnapshotIterator.next();
                    ChatMessage msg = child.getValue(ChatMessage.class);

                    if(msg.getClubId() == clubId)
                        messagesForClub.add(msg);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        int x = 2;


        /*
        FirebaseListOptions<ChatMessage> options =
                new FirebaseListOptions.Builder<ChatMessage>()
                        .setQuery(query, ChatMessage.class)
                        .setLayout(R.layout.message)
                        .build();

        adapter = new FirebaseListAdapter<ChatMessage>(options) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                TextView messageText = v.findViewById(R.id.message_text);
                TextView messageUser = v.findViewById(R.id.message_user);
                TextView messageTime = v.findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        messageList.setAdapter(adapter);

        */


        //adapter.startListening();

    }

    @Override
    protected void onStop() {
        if (adapter != null)
            adapter.stopListening();
        super.onStop();
    }



    void addTestData() {

        ChatMessage msg1 = new ChatMessage("msg1", "user1", 1);
        reference.push().setValue(msg1);

        ChatMessage msg2 = new ChatMessage("msg2", "user1", 1);
        reference.push().setValue(msg2);

        ChatMessage msg3 = new ChatMessage("msg3", "user1", 2);
        reference.push().setValue(msg3);

        ChatMessage msg4 = new ChatMessage("msg4", "user1", 2);
        reference.push().setValue(msg4);

        ChatMessage msg5 = new ChatMessage("msg5", "user1", 3);
        reference.push().setValue(msg5);

    }

}
