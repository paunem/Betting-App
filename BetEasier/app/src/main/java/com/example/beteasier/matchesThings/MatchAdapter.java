package com.example.beteasier.matchesThings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.beteasier.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MatchAdapter extends FirestoreRecyclerAdapter<Match, MatchAdapter.NoteHolder> {
    private OnItemClickListener listener;
    private int expandedPosition = -1;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MatchAdapter(@NonNull FirestoreRecyclerOptions<Match> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, final int position, @NonNull Match model) {
        holder.textViewName1.setText(model.getTeamName1());
        holder.textViewName2.setText(model.getTeamName2());
        holder.textViewDate.setText(model.getDate());
        holder.textViewTime.setText(model.getTime());
        holder.textViewRate1.setText(String.format(Locale.getDefault(),"%.2f", model.getRate1()));
        holder.textViewRate2.setText(String.format(Locale.getDefault(), "%.2f", model.getRate2()));

        final boolean isExpanded = position==expandedPosition;
        holder.expandArea.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandedPosition = isExpanded ? -1:position;
                notifyItemChanged(position);
            }
        });
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
        EditText editTextAmount;
        Button buttonBet1;
        Button buttonBet2;
        ConstraintLayout expandArea;

        public NoteHolder(View itemView) {
            super(itemView);
            textViewName1 = itemView.findViewById(R.id.text_view_team_name1);
            textViewName2 = itemView.findViewById(R.id.text_view_team_name2);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            textViewTime = itemView.findViewById(R.id.text_view_time);
            textViewRate1 = itemView.findViewById(R.id.text_view_rate1);
            textViewRate2 = itemView.findViewById(R.id.text_view_rate2);
            editTextAmount = itemView.findViewById(R.id.editTextAmount);
            buttonBet1 = itemView.findViewById(R.id.buttonBet1);
            buttonBet2 = itemView.findViewById(R.id.buttonBet2);
            expandArea = itemView.findViewById(R.id.expandArea);

            buttonBet1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        //listener.onItemClick(getSnapshots().getSnapshot(position), position);

                        Match match = getSnapshots().getSnapshot(position).toObject(Match.class);

                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
                        String dateNow = df.format(Calendar.getInstance().getTime());
                        String timeNow = tf.format(Calendar.getInstance().getTime());

                        if(!editTextAmount.getText().toString().equals("")){
                            String amount = editTextAmount.getText().toString();
                            Bet bet = new Bet(match.getTeamName1(), match.getTeamName2(), dateNow, timeNow, match.getRate1(), match.getRate2(), amount, match.getCategory(), FirebaseAuth.getInstance().getCurrentUser().getUid());
                            db.collection("Bets").add(bet);
                            editTextAmount.setText("");
                            editTextAmount.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        }
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