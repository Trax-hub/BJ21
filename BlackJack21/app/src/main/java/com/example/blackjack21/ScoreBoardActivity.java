package com.example.blackjack21;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ScoreBoardActivity extends AppCompatActivity {

    private ListView listView;
    private FirebaseDatabase db;
    private ArrayList<User> users;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(this, PortalActivity.class));
            finish();
        }

        db = FirebaseDatabase.getInstance("https://black-jack-21-ede5c-default-rtdb.europe-west1.firebasedatabase.app");
        users = new ArrayList<>();
        listView = findViewById(R.id.scoreBoard);

        getData();
    }

    private void getData(){

        db.getReference("Users")
                .orderByChild("balance")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 0;
                            for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                                users.add(new User(dataSnapshot.child("pseudo").getValue().toString(), dataSnapshot.child("mail").getValue().toString(), dataSnapshot.child("uid").getValue().toString(), (Double) dataSnapshot.child("balance").getValue(), (Long) dataSnapshot.child("rewardTime").getValue()));
                                i++;
                                if(i > 9){
                                    break;
                                }
                            }
                            display(users);
                        }
                    }
                });
    }

    private void display(ArrayList<User> usersArrayList){
        ScoreBoardAdapter scoreBoardAdapter = new ScoreBoardAdapter(ScoreBoardActivity.this, usersArrayList);
        listView.setAdapter(scoreBoardAdapter);
    }
}
