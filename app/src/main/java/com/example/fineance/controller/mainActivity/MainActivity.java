package com.example.fineance.controller.mainActivity;

import static com.example.fineance.model.PerformNetworkRequest.categoriesObservable;
import static com.example.fineance.model.PerformNetworkRequest.depensesObservable;
import static com.example.fineance.model.PerformNetworkRequest.getCategories;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.fineance.R;
import com.example.fineance.controller.categoryActivity.CategorieMenuFragment;
import com.example.fineance.model.Depense;
import com.example.fineance.model.DepenseUtilities;
import com.example.fineance.model.PerformNetworkRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {
    private static final String PREVISION_FRAGMENT_TAG = "prevision";
    BottomNavigationView bottomNav;

    @SuppressLint("NonConstantResourceId")
    List<Depense> depenseArrayList = new ArrayList<>();
    String isOn = "home";
    String savedIsOn;

    CategorieMenuFragment categorie = new CategorieMenuFragment();
    PrevisionFragment previsions = new PrevisionFragment();
    HomeFragment home = new HomeFragment();

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        depensesObservable.addObserver(this);
        categoriesObservable.addObserver(this);
        PerformNetworkRequest.getDepenses();
        getCategories();
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        this.setContentView(R.layout.activity_main);

        bottomNav = this.findViewById(R.id.bot_nav_bar);
        this.getSupportFragmentManager().beginTransaction().replace(R.id.main_container, home).commit();
        bottomNav.setSelectedItemId(R.id.nav_home);

        if (savedInstanceState != null) {
            previsions = (PrevisionFragment)
                    getSupportFragmentManager().findFragmentByTag(PREVISION_FRAGMENT_TAG);
        }


        bottomNav.setOnItemSelectedListener(item -> {

            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    isOn = "home";
                    fragment = home;
                    break;
                case R.id.nav_categories:
                    isOn = "categories";
                    fragment = categorie;
                    break;
                case R.id.nav_prevision:
                    isOn = "prevision";
                    fragment = previsions;
                    if (!fragment.isInLayout()) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_container, previsions, PREVISION_FRAGMENT_TAG)
                                .commit();
                    }
                    System.out.println(previsions.moisActuel);
                    break;
            }
            if (fragment != null&&!(fragment instanceof  PrevisionFragment)) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
            }
            return true;
        });
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        isOn = savedInstanceState.getString(savedIsOn);
        if (isOn.equals("prevision")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, previsions,PREVISION_FRAGMENT_TAG).commit();
        } else if (isOn.equals("categories")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, categorie).commit();
        }
    }


    @Override
    public void update(Observable observable, Object o) {
        try {
            categorie.updateList((List) o);
            depenseArrayList = (List<Depense>) o;
            home.updateTotal(DepenseUtilities.getMontantTotal(depenseArrayList));
        } catch (Exception e) {
           e.printStackTrace();
        }

    }

}