package org.yi01.kakeibo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.yi01.kakeibo.R;

public class InputDetailFragment extends AbstractFragment implements AbstractFragment.OnSubmitListener {

    public InputDetailFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_input_detail, container, false);

        setOnSubmitListener(
                (EditText) root.findViewById(R.id.editor_category)
                , (Button) root.findViewById(R.id.btn_next)
                , this
        );

        requestFocus((EditText) root.findViewById(R.id.editor_category));

        return root;
    }

    @Override
    public void onSubmit(CharSequence text) {
        String category = text.toString();
        if(!TextUtils.isEmpty(category)) {
            gotoNextFragment(category);
        }
    }

    private void gotoNextFragment(String category) {
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_KEY_CATEGORY, category);

        Fragment f = new InputValueFragment();
        f.setArguments(bundle);

        getFragmentManager().beginTransaction()
                .replace(R.id.activity_main_container, f)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}
