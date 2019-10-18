package com.example.beteasier.mainFragments;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.beteasier.R;
import com.example.beteasier.activities.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AccountFragment extends Fragment {
    private Button btnLogout;
    private TextView textViewName;
    private EditText editTextUpdateName;
    private TextView textViewSurname;
    private EditText editTextUpdateSurname;
    private TextView textViewCountry;
    private EditText editTextUpdateCoutry;
    private TextView textViewEmail;
    private Button btnUpdateInfo;

    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference dbRef = db.collection("Users").document(mUser.getUid());

    private static final String TAG = "AccountFragment";

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loadUserData();
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        btnLogout = view.findViewById(R.id.buttonLogOut);
        textViewName = view.findViewById(R.id.text_view_Name);
        editTextUpdateName = view.findViewById(R.id.editTextUpdateName);
        textViewSurname = view.findViewById(R.id.text_view_Surname);
        editTextUpdateSurname = view.findViewById(R.id.editTextUpdateSurname);
        textViewCountry = view.findViewById(R.id.text_view_Country);
        editTextUpdateCoutry = view.findViewById(R.id.editTextUpdateCountry);
        textViewEmail = view.findViewById(R.id.text_view_Email);
        btnUpdateInfo = view.findViewById(R.id.buttonUpdateInfo);


        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editTextUpdateName.getText().toString().equals(""))
                    dbRef.update("name", editTextUpdateName.getText().toString());
                if(!editTextUpdateSurname.getText().toString().equals(""))
                    dbRef.update("surname", editTextUpdateSurname.getText().toString());
                if(!editTextUpdateCoutry.getText().toString().equals(""))
                    dbRef.update("country", editTextUpdateCoutry.getText().toString());

                loadUserData();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(getActivity(), MainActivity.class);
                startActivity(intToMain);
            }
        });
    }

    private void loadUserData() {
        dbRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("name");
                            textViewName.setText(name);
                            String surname = documentSnapshot.getString("surname");
                            textViewSurname.setText(surname);
                            String country = documentSnapshot.getString("country");
                            textViewCountry.setText(country);
                            String email = documentSnapshot.getString("email");
                            textViewEmail.setText(email);
                        } else {
                            Toast.makeText(getActivity(), "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }

}
