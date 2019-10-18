package com.example.beteasier.matchesThings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.beteasier.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;


public class MatchAdapter extends FirestoreRecyclerAdapter<Match, MatchAdapter.NoteHolder> {
    private OnItemClickListener listener;

    public MatchAdapter(@NonNull FirestoreRecyclerOptions<Match> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Match model) {
        holder.textViewName1.setText(model.getTeamName1());
        holder.textViewName2.setText(model.getTeamName2());
        holder.textViewDate.setText(model.getDate());
        holder.textViewTime.setText(model.getTime());
        holder.textViewRate1.setText(model.getRate1());
        holder.textViewRate2.setText(model.getRate2());

    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_match,
                parent, false);
        return new NoteHolder(v);
    }

    public class NoteHolder extends RecyclerView.ViewHolder{
        TextView textViewName1;
        TextView textViewName2;
        TextView textViewDate;
        TextView textViewTime;
        TextView textViewRate1;
        TextView textViewRate2;
        Button buttonBet;

        public NoteHolder(View itemView) {
            super(itemView);
            textViewName1 = itemView.findViewById(R.id.text_view_team_name1);
            textViewName2 = itemView.findViewById(R.id.text_view_team_name2);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            textViewTime = itemView.findViewById(R.id.text_view_time);
            textViewRate1 = itemView.findViewById(R.id.text_view_rate1);
            textViewRate2 = itemView.findViewById(R.id.text_view_rate2);
            buttonBet = itemView.findViewById(R.id.buttonBet);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}