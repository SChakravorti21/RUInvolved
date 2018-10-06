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
import android.widget.Toast;

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

    static final int clubId = 1;

    private MessagesAdapter adapter;
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

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText input = findViewById(R.id.input);
                String msg = input.getText().toString();

                reference.child(clubId + "").push()
                        .setValue(new ChatMessage(msg, "user1", 1)
                        );

                input.setText("");
            }


        });


        displayChatMessages();

    }


    private void displayChatMessages() {

        final ListView messagesListView = findViewById(R.id.list_of_messages);


        final ArrayList<ChatMessage> messagesForClub = new ArrayList<>();

        reference.child(clubId + "").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> dataSnapshotIterator = dataSnapshot.getChildren().iterator();

                while(dataSnapshotIterator.hasNext()) {
                    DataSnapshot child = dataSnapshotIterator.next();
                    ChatMessage msg = child.getValue(ChatMessage.class);

                    Log.d("doritos", "added a msg");
                    messagesForClub.add(msg);
                }


                adapter = new MessagesAdapter(messagesForClub, MainActivity.this);
                messagesListView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
