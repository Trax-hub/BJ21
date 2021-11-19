package com.example.blackjack21;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.StackView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Stack;

public class testActivity extends AppCompatActivity {
    private Game game;
    private StackView stack;
    private Button add, remove;
    private CardStack cardStack;
    private ArrayList<StackItem> items = new ArrayList<StackItem>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        add = findViewById(R.id.buttonAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });

        remove = findViewById(R.id.buttonRemove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove();
            }
        });
        game = new Game();
        stack = findViewById(R.id.stackview);
        cardStack = new CardStack();
        items.add(new StackItem(cardStack.getRandomCard()));
        StackAdapter adapter = new StackAdapter(this, R.layout.stack_item, items);
        stack.setAdapter(adapter);
        stack.setEnabled(false);


    }

    private void add(){
        items.add(new StackItem(cardStack.getRandomCard()));
        StackAdapter adapter = new StackAdapter(this, R.layout.stack_item, items);
        stack.setAdapter(adapter);
        stack.setHorizontalScrollBarEnabled(true);
    }

    private void remove(){
        items.remove(items.get(items.size() - 1));
        StackAdapter adapter = new StackAdapter(this, R.layout.stack_item, items);
        stack.setAdapter(adapter);
        stack.setHorizontalScrollBarEnabled(true);
    }
}
