package org.yi01.kakeibo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.yi01.kakeibo.fragment.CategorySelectionFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState==null){
            onNewIntent(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent == null) {
            finish();
            return;
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_main_container, new CategorySelectionFragment())
                .commit();
    }
}
