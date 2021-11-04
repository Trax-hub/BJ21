package com.example.blackjack21;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CardAdapter extends ArrayAdapter<Card> {
    public CardAdapter(Context context, ArrayList<Card> cards){
        super(context, R.layout.card_layout, R.id.card,  cards);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = super.getView(position, convertView, parent);

        Card card = getItem(position);

        if (view == null){
            view = LayoutInflater.from(this.getContext()).inflate(R.layout.card_layout, parent, false);
        }

        ImageView imageView = view.findViewById(R.id.card);
        String res = card.getRes();
        imageView.setImageResource(new MainActivity().getResources().getIdentifier(res, "drawable", "com.example.blackjack21"));

        return view;
    }


}
