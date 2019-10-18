package com.example.beteasier.mainFragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.beteasier.R;
import com.example.beteasier.categoriesFragmnets.BasketballFragment;
import com.example.beteasier.categoriesFragmnets.FootballFragment;
import com.example.beteasier.categoriesFragmnets.TennisFragment;
import com.example.beteasier.matchesThings.GameCategory;
import com.example.beteasier.matchesThings.GameCategoryAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class MatchesFragment extends Fragment implements GameCategoryAdapter.OnNoteListener {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference dbRef = db.collection("Game Categories");

    private GameCategoryAdapter adapter;

    public MatchesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_matches, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView(view);
    }

    private void setUpRecyclerView(View view) {
        Query query = dbRef.orderBy("gameCount", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<GameCategory> options = new FirestoreRecyclerOptions.Builder<GameCategory>()
                .setQuery(query, GameCategory.class)
                .build();

        adapter = new GameCategoryAdapter(options, this);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_matches);
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

    @Override
    public void OnNoteClick(int position) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        switch(position){
            case 0:
                transaction.replace(R.id.main, new BasketballFragment());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case 1:
                transaction.replace(R.id.main, new FootballFragment());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case 2:
                transaction.replace(R.id.main, new TennisFragment());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }

    }
}
