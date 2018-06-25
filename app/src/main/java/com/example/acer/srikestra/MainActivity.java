package com.example.acer.srikestra;



import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemReselectedListener {

    private TextView mTextMessage;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemReselectedListener(this);


    }



    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
        displaySelectedScreen(item.getItemId());
    }

    private void displaySelectedScreen(int itemId) {
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.navigation_map:
               fragment = new Google_Map_Fragment();
                break;
            case R.id.navigation_place:
                fragment = new Fragment_Place();
                break;
            case R.id.navigation_todo:
                Intent intent=new Intent(MainActivity.this,ActivityMain.class);
                startActivity(intent);
                break;
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
           // ft.replace(R.id., fragment);
            ft.replace(R.id.fragment,fragment);
            ft.commit();
        }
    }
}