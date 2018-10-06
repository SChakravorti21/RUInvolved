package com.example.shoumyo.ruinvolved.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shoumyo.ruinvolved.R;
import com.example.shoumyo.ruinvolved.models.ChatMessage;

import java.util.ArrayList;

public class MessagesAdapter extends ArrayAdapter<ChatMessage> {


    private ArrayList<ChatMessage> msgs;
    Context context;

    public MessagesAdapter(ArrayList<ChatMessage> data, Context context) {

        super(context, R.layout.message, data);
        this.msgs = data;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if(v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.message, null);
        }

        ChatMessage msg = msgs.get(position);

        TextView msgTxt = v.findViewById(R.id.message_text);
        msgTxt.setText(msg.getMessageText());

        TextView userTxt = v.findViewById(R.id.message_user);
        userTxt.setText(msg.getMessageUser());

        TextView timeTxt = v.findViewById(R.id.message_time);
        timeTxt.setText(msg.getMessageTime() + "");

        return v;
    }
}
