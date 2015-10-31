package org.yi01.kakeibo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import org.yi01.kakeibo.R;

import java.util.HashMap;
import java.util.Map;

public class CategorySelectionFragment extends AbstractFragment{

    public CategorySelectionFragment(){}

    public HashMap<Integer, String> LABEL = new HashMap<Integer, String>(){
        {
            put(R.id.btn_conveni,"コンビニ");
            put(R.id.btn_eatout,"外食");
            put(R.id.btn_fare,"交通費");
            put(R.id.btn_misc,"その他");
            put(R.id.btn_supermarket,"ライフ");
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_category_selection, container, false);

        for(Map.Entry<Integer,String> s: LABEL.entrySet()) {
            Button btn = (Button) root.findViewById(s.getKey());
            btn.setText(s.getValue());
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction()
                            .replace(R.id.activity_main_container, makeFragmentForId(v.getId()))
                            .addToBackStack(null)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                }
            });
        }

        root.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });

        return root;
    }

    private Fragment makeFragmentForId(int id) {
        // その他.
        if (id==R.id.btn_misc) return new InputDetailFragment();


        Bundle bundle = new Bundle();
        bundle.putString(PARAM_KEY_CATEGORY, LABEL.get(id));

        Fragment f = new InputValueFragment();
        f.setArguments(bundle);
        return f;
    }
}
