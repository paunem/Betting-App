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
import com.example.beteasier.matchesThings.GameCategory;
import com.example.beteasier.matchesThings.GameCategoryAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MatchesFragment extends Fragment implements GameCategoryAdapter.OnNoteListener {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference dbRef = db.collection("Game Categories");
    private CollectionReference dbMatches = db.collection("Matches");

    private static final String TAG = "MatchesFragment";

    private GameCategoryAdapter adapter;
    List<String> categoryNames = new ArrayList<>();

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
        updateMatchStatus();
        updateCategoryCount();
        setUpRecyclerView(view);
    }

    private void setUpRecyclerView(View view) {
        Query query = dbRef.orderBy("gameCount", Query.Direction.DESCENDING).orderBy("gameTitle", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<GameCategory> options = new FirestoreRecyclerOptions.Builder<GameCategory>()
                .setQuery(query, GameCategory.class)
                .build();

        adapter = new GameCategoryAdapter(options, this);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_matches);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private void updateMatchStatus() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
        final String dateNow = df.format(Calendar.getInstance().getTime());
        final String timeNow = tf.format(Calendar.getInstance().getTime());

        dbMatches.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot document : task.getResult()) {
                    if(document.get("date").toString().compareTo(dateNow) <= 0 && document.get("time").toString().compareTo(timeNow) <= 0){
                        dbMatches.document(document.getId()).update("status", "0");
                    }
                }
            }
        });
    }

    private void updateCategoryCount() {
        getCategoryNames();
        for(final String name : categoryNames) {
            dbMatches.whereEqualTo("category", name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    final int count = task.getResult().size();
                    db.collection("Game Categories").whereEqualTo("gameTitle", name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot document : task.getResult()) {
                                db.collection("Game Categories").document(document.getId()).update("gameCount", count);
                            }
                        }
                    });
                }
            });
        }
    }

    private void getCategoryNames(){
        db.collection("Game Categories").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot document : task.getResult()) {
                    categoryNames.add(document.getString("gameTitle"));
                }
            }
        });
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

        String fragmentName = "com.example.beteasier.categoriesFragments." + adapter.getSnapshots().getSnapshot(position).getString("gameTitle") + "Fragment";
        try {
            Fragment fragment = (Fragment) Class.forName(fragmentName).newInstance();
            transaction.replace(R.id.main, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
    }
}
