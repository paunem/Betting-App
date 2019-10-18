package com.example.beteasier.categoriesFragmnets;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.beteasier.R;
import com.example.beteasier.matchesThings.Bet;
import com.example.beteasier.matchesThings.Match;
import com.example.beteasier.matchesThings.MatchAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class BasketballFragment extends Fragment{

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference dbRef = db.collection("Basketball Matches");

    private MatchAdapter adapter;

    public BasketballFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basketball, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView(view);

        adapter.setOnItemClickListener(new MatchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Match match = documentSnapshot.toObject(Match.class);

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat tf = new SimpleDateFormat("HH:mm");
                String dateNow = df.format(Calendar.getInstance().getTime());
                String timeNow = tf.format(Calendar.getInstance().getTime());

                Bet bet = new Bet(match.getTeamName1(), match.getTeamName2(), dateNow, timeNow, match.getRate1(), match.getRate2(), match.getCategory(), FirebaseAuth.getInstance().getCurrentUser().getUid());

                db.collection("Bets").add(bet);
            }
        });
    }

    private void setUpRecyclerView(View view) {
        Query query = dbRef.orderBy("date", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Match> options = new FirestoreRecyclerOptions.Builder<Match>()
                .setQuery(query, Match.class)
                .build();

        adapter = new MatchAdapter(options);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_basketball);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
