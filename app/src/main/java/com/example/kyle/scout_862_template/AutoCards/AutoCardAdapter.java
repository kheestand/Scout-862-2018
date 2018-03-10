package com.example.kyle.scout_862_template.AutoCards;

import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.kyle.scout_862_template.R;
import com.example.kyle.scout_862_template.Scout862.MatchDatabase;

import java.security.PublicKey;
import java.util.ArrayList;

/**
 * Created by kyle on 3/9/18.
 */

public class AutoCardAdapter extends RecyclerView.Adapter<AutoCardAdapter.AutoCardHolder>{

    private ArrayList<AutoCard> collection;

    public class AutoCardHolder extends RecyclerView.ViewHolder {

        TextView cardName;
        CheckBox scaleCB;
        CheckBox switchCB;
        MatchDatabase matchDatabase;
        int scaleX;
        int switchX;

        public AutoCardHolder(View view){
            super(view);
            cardName = (TextView) view.findViewById(R.id.cube_number);
            scaleCB = (CheckBox) view.findViewById(R.id.cube_scale);
            switchCB = (CheckBox) view.findViewById(R.id.cube_switch);
            scaleCB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (scaleCB.isChecked()) {
                        matchDatabase.add(1, scaleX);
                    }
                    else {
                        matchDatabase.add(0, scaleX);
                    }
                }
            });

            switchCB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(switchCB.isChecked()) {
                        matchDatabase.add(1, switchX);
                    }
                    else {
                        matchDatabase.add(0, switchX);
                    }
                }
            });
        }

        public void readBoxes() {
            if (matchDatabase.getInt(scaleX) == 1) {
                scaleCB.setChecked(true);
            } else {
                scaleCB.setChecked(false);
            }

            if (matchDatabase.getInt(switchX) == 1) {
                switchCB.setChecked(true);
            } else {
                switchCB.setChecked(false);
            }
        }
    }

    public AutoCardAdapter (ArrayList<AutoCard> dataset) {
        this.collection = dataset;
    }

    @Override
    public AutoCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.auto_card_cube, parent, false);
        AutoCardHolder card = new AutoCardHolder(view);
        return card;
    }

    @Override
    public void onBindViewHolder(AutoCardHolder holder, int position) {
        holder.cardName.setText(collection.get(position).getCardString());
        holder.scaleX = collection.get(position).getScaleX();
        holder.switchX = collection.get(position).getSwitchX();
        holder.matchDatabase = collection.get(position).getMatchDatabase();
        holder.readBoxes();
    }

    @Override
    public int getItemCount() {
        return collection.size();
    }

}
