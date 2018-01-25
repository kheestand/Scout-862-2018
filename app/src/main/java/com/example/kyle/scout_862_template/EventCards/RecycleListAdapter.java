package com.example.kyle.scout_862_template.EventCards;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kyle.scout_862_template.R;

import java.util.List;

import static java.lang.String.valueOf;

/**
 * Created by kyle on 9/7/17.
 */

public class RecycleListAdapter extends RecyclerView.Adapter<RecycleListAdapter.ListViewHolder> {

    List<ListClass> list;

    public RecycleListAdapter(List<ListClass> list) {
        this.list = list;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_info_box, parent, false);
        ListViewHolder listViewHolder = new ListViewHolder(view);
        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.eventName.setText(list.get(position).eventName);
        holder.eventStartDate.setText(list.get(position).eventDate);
        holder.eventType.setText(valueOf(list.get(position).eventCode));
        holder.eventStatus.setText(valueOf(list.get(position).status));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView eventName;
        TextView eventStartDate;
        TextView eventType;
        TextView eventStatus;

        ListViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            eventName = (TextView) itemView.findViewById(R.id.info_box_event_name);
            eventStartDate = (TextView) itemView.findViewById(R.id.info_box_event_date);
            eventType = (TextView) itemView.findViewById(R.id.info_box_event_type);
            eventStatus = (TextView) itemView.findViewById(R.id.event_box_status);
        }
    }
}
