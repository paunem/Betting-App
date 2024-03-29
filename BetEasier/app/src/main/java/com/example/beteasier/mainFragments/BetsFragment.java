package com.example.beteasier.mainFragments;


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
import com.example.beteasier.matchesThings.BetAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class BetsFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference dbRef = db.collection("Bets");

    private BetAdapter adapter;


    public BetsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView(view);
    }

    private void setUpRecyclerView(View view) {
        Query query = dbRef.whereEqualTo("userId", FirebaseAuth.getInstance().getCurrentUser().getUid()).orderBy("date", Query.Direction.ASCENDING).orderBy("time", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Bet> options = new FirestoreRecyclerOptions.Builder<Bet>()
                .setQuery(query, Bet.class)
                .build();

        adapter = new BetAdapter(options);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_bets);
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
