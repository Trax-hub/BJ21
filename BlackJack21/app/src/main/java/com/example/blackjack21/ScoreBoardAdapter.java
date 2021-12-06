package com.example.blackjack21;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ScoreBoardAdapter  extends ArrayAdapter<User> {

    public ScoreBoardAdapter(Context context, ArrayList<User> usersArrayList){
        super(context, R.layout.item, R.id.pseudo,  usersArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = super.getView(position, convertView, parent);

        User user = getItem(position);

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        TextView pseudo = view.findViewById(R.id.pseudo);
        TextView balance = view.findViewById(R.id.balance);
        TextView rank = view.findViewById(R.id.rank);
        rank.setText(Integer.toString(position + 1));
        pseudo.setText(user.getPseudo());
        balance.setText(user.getBalance().toString());


        return view;
    }

}
