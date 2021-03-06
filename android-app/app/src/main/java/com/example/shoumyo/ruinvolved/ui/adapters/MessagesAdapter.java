package com.example.shoumyo.ruinvolved.ui.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shoumyo.ruinvolved.R;
import com.example.shoumyo.ruinvolved.models.ChatMessage;
import com.example.shoumyo.ruinvolved.utils.SharedPrefsUtils;

import java.util.ArrayList;

public class MessagesAdapter extends ArrayAdapter<ChatMessage> {

    private ArrayList<ChatMessage> msgs;
    Context context;


    public void setMessages(ArrayList<ChatMessage> data) {
        msgs = data;
    }

    @Override
    public int getCount() {
        if(msgs == null)
            return 0;
        return msgs.size();
    }

    public MessagesAdapter(ArrayList<ChatMessage> data, Context context) {

        super(context, R.layout.user_message, data);
        this.msgs = data;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ChatMessage msg = msgs.get(position);

        View v;
        String username = SharedPrefsUtils.getUsername(context);
        String messageUser = msg.getMessageUser();
        if(messageUser.equals(username)) {
            v = LayoutInflater.from(context).inflate(R.layout.user_message, null);
        }
        else {
            v = LayoutInflater.from(context).inflate(R.layout.other_message, null);
        }

        if(position % 2 == 0) {
            v.setBackgroundColor(Color.rgb(215, 219, 226));
        } else {
            v.setBackgroundColor(Color.WHITE);
        }

        TextView msgTxt = v.findViewById(R.id.message_text);
        msgTxt.setText(msg.getMessageText());

        TextView userTxt = v.findViewById(R.id.message_user);
        userTxt.setText(msg.getMessageUser());

        TextView timeTxt = v.findViewById(R.id.message_time);
        timeTxt.setText(DateFormat.format("MMM d (h:mm a)",
                msg.getMessageTime()));

        // admins are orange, users are blue
        if (msg.getMessageUser().contains("Admin")) {
            userTxt.setTextColor(Color.rgb(255, 127, 80));
        } else {
            userTxt.setTextColor(Color.BLUE);
        }

        return v;
    }
}
