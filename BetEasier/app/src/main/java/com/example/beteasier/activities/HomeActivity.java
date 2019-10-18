package com.example.beteasier.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.beteasier.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HomeActivity extends AppCompatActivity {

    private NavController navController;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private AppBarConfiguration appBarConfiguration;
    private BottomNavigationView bottomNavigationView;
    private TextView userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_home);

        init();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    private void init() {
        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        navigationView.inflateHeaderView(R.layout.drawer_header);
        View headerView = navigationView.getHeaderView(0);
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        navController = Navigation.findNavController(this,R.id.main);
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.home, R.id.matches, R.id.search, R.id.bets)
                        .setDrawerLayout(drawer).build();

        userEmail = headerView.findViewById(R.id.text_view_user_email);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            userEmail.setText(user.getEmail());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController,appBarConfiguration );
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
