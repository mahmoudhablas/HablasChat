package com.example.mahmoud.hablaschat;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HabalsMain extends AppCompatActivity {
    private String mDisplayName;
    private ListView mChatListView;
    private EditText mInputText;
    private ImageButton mSendButton;
    private DatabaseReference mDataReference;
    private ChatListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habals_main);
        setupDisplayName();
        mDataReference = FirebaseDatabase.getInstance().getReference();
        mInputText = (EditText) findViewById(R.id.messageInput);
        mSendButton = (ImageButton)findViewById(R.id.sendButton);
        mChatListView = (ListView)findViewById(R.id.chat_list_view);

        mInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                sendMessage();
                return true;
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

    }
    private void sendMessage()
    {
        String msg = mInputText.getText().toString();
        if (!msg.equals(""))
        {
            InstantMessage mMsg = new InstantMessage(msg,mDisplayName);
            mDataReference.child("messages").push().setValue(mMsg);
            mInputText.setText("");
        }

    }
    private void setupDisplayName()
    {
        SharedPreferences prefs = getSharedPreferences(RegisterActivity.CHAT_PREFS,MODE_PRIVATE);
        mDisplayName = prefs.getString(RegisterActivity.DISPLAY_NAME_KEY,null);
        if (mDisplayName==null) mDisplayName="Anonymous";

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter = new ChatListAdapter(this, mDataReference, mDisplayName);
        mChatListView.setAdapter(mAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.cleanup();
    }
}
