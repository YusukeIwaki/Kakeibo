package org.yi01.kakeibo.fragment;

import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

abstract class AbstractFragment extends Fragment {
    protected static final String PARAM_KEY_CATEGORY = "category";


    protected void setOnSubmitListener(final EditText editor, final Button btn, final OnSubmitListener listener) {
        editor.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    listener.onSubmit(editor.getText());
                    return true;
                }
                return false;
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSubmit(editor.getText());
            }
        });
    }

    interface OnSubmitListener{
        void onSubmit(CharSequence text);
    }


    protected void finish(){
        if(getFragmentManager().getBackStackEntryCount()==0){
            getActivity().finish();
        }
        else {
            getFragmentManager().popBackStack();
        }
    }
}
