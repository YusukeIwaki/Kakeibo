package org.yi01.kakeibo.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.yi01.kakeibo.R;
import org.yi01.kakeibo.db.Category;
import org.yi01.kakeibo.db.DatabaseHelper;
import org.yi01.kakeibo.db.Pay;

public class InputValueFragment extends AbstractInputDataFragment implements AbstractInputDataFragment.OnSubmitListener {

    private String mCategory;

    public InputValueFragment(){}


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle params = getArguments();
        if(params == null || !params.containsKey(PARAM_KEY_CATEGORY)) {
            finish();
            return;
        }
        String category = params.getString(PARAM_KEY_CATEGORY);
        if(TextUtils.isEmpty(category)) {
            finish();
            return;
        }

        mCategory = category;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_input_value, container, false);

        setOnSubmitListener(
                (EditText) root.findViewById(R.id.editor_value)
                , (Button) root.findViewById(R.id.btn_submit)
                , this
        );

        requestFocus((EditText) root.findViewById(R.id.editor_value));

        return root;
    }

    @Override
    public void onSubmit(CharSequence text) {
        int value = (!TextUtils.isEmpty(text) && TextUtils.isDigitsOnly(text)) ?
            Integer.parseInt(text.toString(),10) : 0;

        if(value > 0) {
            submit(mCategory, value);
        }

    }

    private void submit(final String category, int value) {
        final Pay pay = new Pay();
        //pay.categoryIdはこの時点ではわからない
        pay.yen = value;
        pay.datetime = System.currentTimeMillis();

        DatabaseHelper.writeWithTransaction(getActivity(), new DatabaseHelper.DBCallback<Object>() {
            @Override
            public Object process(SQLiteDatabase db) {
                Category c = Category.getOrCreate(db, category);
                c.put(db);
                pay.categoryId = c.id;
                pay.put(db);

                gotoFirstView();

                return null;
            }
        });
    }

    private void gotoFirstView(){
        cleanupBackStack();
    }

    private void cleanupBackStack() {
        final FragmentManager fragmentManager = getFragmentManager();
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

}
