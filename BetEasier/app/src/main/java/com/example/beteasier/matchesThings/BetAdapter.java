package com.example.beteasier.matchesThings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.beteasier.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Locale;


public class BetAdapter extends FirestoreRecyclerAdapter<Bet, BetAdapter.NoteHolder> {
    private OnItemClickListener listener;

    public BetAdapter(@NonNull FirestoreRecyclerOptions<Bet> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Bet model) {
        holder.textViewName1.setText(model.getTeamName1());
        holder.textViewName2.setText(model.getTeamName2());
        holder.textViewBetCategory.setText(model.getCategory());
        holder.textViewBetDate.setText(model.getDate());
        holder.textViewBetTime.setText(model.getTime());
        holder.textViewRate1.setText(String.format(Locale.getDefault(),"%.2f", model.getRate1()));
        holder.textViewRate2.setText(String.format(Locale.getDefault(),"%.2f", model.getRate2()));
        holder.textViewAmount.setText(model.getAmount());
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_bet,
                parent, false);
        return new NoteHolder(v);
    }

    public class NoteHolder extends RecyclerView.ViewHolder{
        TextView textViewName1;
        TextView textViewName2;
        TextView textViewBetCategory;
        TextView textViewBetInfo;
        TextView textViewBetDate;
        TextView textViewBetTime;
        TextView textViewRate1;
        TextView textViewRate2;
        TextView textViewAmount;
        TextView textViewEuro;

        public NoteHolder(View itemView) {
            super(itemView);
            textViewName1 = itemView.findViewById(R.id.text_view_team_name1);
            textViewName2 = itemView.findViewById(R.id.text_view_team_name2);
            textViewBetCategory = itemView.findViewById(R.id.text_view_betCategory);
            textViewBetInfo = itemView.findViewById(R.id.text_view_betInfo);
            textViewBetDate = itemView.findViewById(R.id.text_view_betDate);
            textViewBetTime = itemView.findViewById(R.id.text_view_betTime);
            textViewRate1 = itemView.findViewById(R.id.text_view_rate1);
            textViewRate2 = itemView.findViewById(R.id.text_view_rate2);
            textViewAmount = itemView.findViewById(R.id.text_view_betAmount);
            textViewEuro = itemView.findViewById(R.id.text_view_euro);
        }


    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}