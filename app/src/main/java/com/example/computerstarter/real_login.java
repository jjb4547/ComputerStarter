package com.example.computerstarter;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.computerstarter.databinding.ActivityRealLoginBinding;

public class real_login extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    Button bRegisterBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_login);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setSupportActionBar(binding.toolbar);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            this.finish();
            return true;
        }else
            return super.onOptionsItemSelected(item);
    }
}
