package com.example.beteasier.matchesThings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.beteasier.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;


public class GameCategoryAdapter extends FirestoreRecyclerAdapter<GameCategory, GameCategoryAdapter.NoteHolder> {
    private OnNoteListener mOnNoteListener;

    private static final String TAG = "GameCategoryAdapter";

    public GameCategoryAdapter(@NonNull FirestoreRecyclerOptions<GameCategory> options, OnNoteListener onNoteListener) {
        super(options);
        this.mOnNoteListener = onNoteListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull GameCategory model) {
        holder.textViewGameTitle.setText(model.getGameTitle());
        holder.textViewGameCount.setText(String.valueOf(model.getGameCount()));
        Picasso.get().load(model.getImageUrl()).into(holder.imageViewGameImage);
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_category,
                parent, false);
        return new NoteHolder(v, mOnNoteListener);
    }

    public class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textViewGameTitle;
        TextView textViewGameCount;
        ImageView imageViewGameImage;
        OnNoteListener onNoteListener;

        public NoteHolder(View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            textViewGameTitle = itemView.findViewById(R.id.text_view_game_title);
            textViewGameCount = itemView.findViewById(R.id.text_view_game_count);
            imageViewGameImage =itemView.findViewById(R.id.game_image);

            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.OnNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void OnNoteClick(int position);
    }
}