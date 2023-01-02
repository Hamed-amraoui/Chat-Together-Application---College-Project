package com.example.chattogetherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MessagesActivity extends AppCompatActivity {

    private  MessageAdapter messageAdapter;

    private RecyclerView recyclerMsgs;
    private ProgressBar progressMsgs;
    private TextView txtChattingwith;
    private EditText edtMsgInput;
    private ImageView imgToolbar,imgSend;

    private ArrayList<Message> messages;

    String UsernameOfRoommate,EmailOfRoommate,ChatRoomid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        UsernameOfRoommate = getIntent().getStringExtra("username_of_roommate");
        EmailOfRoommate = getIntent().getStringExtra("email_of_roommate");

        recyclerMsgs = findViewById(R.id.recyclerMsgs);
        progressMsgs = findViewById(R.id.ProgressMsgs);
        txtChattingwith = findViewById(R.id.txtChatting);
        edtMsgInput = findViewById(R.id.edtText);
        imgToolbar = findViewById(R.id.img_toolbar);
        imgSend = findViewById(R.id.imgSendMsg);


        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("messages/"+ChatRoomid).push().setValue(new Message(FirebaseAuth.getInstance().getCurrentUser().getEmail(),EmailOfRoommate,edtMsgInput.getText().toString()));
                edtMsgInput.setText("");
            }
        });

        txtChattingwith.setText(UsernameOfRoommate);

        messageAdapter = new MessageAdapter(messages,getIntent().getStringExtra("my_img"),getIntent().getStringExtra("pic_of_roommate"),MessagesActivity.this);
            //recyclerMsgs.setLayoutManager(new LinearLayoutManager(this));
            recyclerMsgs.setAdapter(messageAdapter);


        Glide.with(MessagesActivity.this).load(getIntent().getStringExtra("pic_of_roommate")).placeholder(R.drawable.account_img).error(R.drawable.account_img).into(imgToolbar);

        messages = new ArrayList<Message>();

        setUpChatRoom();
    }

    private void setUpChatRoom(){
        FirebaseDatabase.getInstance().getReference("user/"+ FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String myUsername = Objects.requireNonNull(snapshot.getValue(User.class)).getUsername();
                if(UsernameOfRoommate.compareTo(myUsername)>0){
                    ChatRoomid = myUsername + UsernameOfRoommate ;
                }else if(UsernameOfRoommate.compareTo(myUsername)==0){
                    ChatRoomid = myUsername + UsernameOfRoommate ;
                }else {
                    ChatRoomid = UsernameOfRoommate + myUsername ;
                }
                try {
                    attachMessageListener(ChatRoomid);
                }catch (Exception e){
                    System.out.println(e);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void attachMessageListener(String ChatRoomid){
        FirebaseDatabase.getInstance().getReference("messages/"+ ChatRoomid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    messages.add(dataSnapshot.getValue(Message.class));
                }
                messageAdapter.notifyDataSetChanged();
                recyclerMsgs.scrollToPosition(messages.size()-1);
                recyclerMsgs.setVisibility(View.VISIBLE);
                progressMsgs.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}