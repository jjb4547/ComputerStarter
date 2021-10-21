package com.example.computerstarter;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.computerstarter.databinding.ActivityRealLoginBinding;

public class real_login extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityRealLoginBinding binding;
    Button bRegisterBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRealLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

//        bRegisterBtn = findViewById(bNewAccount);
//
//        bRegisterBtn.setOnClickListener(new onCreateView().OnClickListener){
//            @Override
//            public void onClick(View v)
//        };

//        NavController navController = Navigation.findNavController(real_login.this, R.id.nav_host_fragment_content_real_login);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//
//        binding.fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show());
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_real_login);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
