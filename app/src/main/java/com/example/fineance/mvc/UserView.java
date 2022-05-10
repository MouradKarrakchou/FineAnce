package com.example.fineance.mvc;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fineance.R;
import com.example.fineance.model.User;

import java.util.Observable;
import java.util.Observer;

public class UserView implements Observer {
    private UserController controller;
    private final LinearLayout layout;
    private final Context context;


    public UserView(Context context, LinearLayout layout) {
        this.context=context;
        this.layout=layout;
    }

    public void setController(UserController controller) {
        this.controller = controller;
        this.controller.setListenersView();
    }

    @Override
    public void update(Observable observable, Object o) {
        Log.d("DEBUG",o+"");
        User user = (User) o;
        TextView name = layout.findViewById(R.id.user_name);
//        TextView seuil = layout.findViewById(R.id.user_seuil);
        name.setText(user.getName());
//        seuil.setText(user.getSeuil());
    }
}
