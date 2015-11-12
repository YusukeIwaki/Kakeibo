package org.yi01.kakeibo.fragment;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

abstract class AbstractInputDataFragment extends AbstractFragment {
    protected static final String PARAM_KEY_CATEGORY = "category";

    protected void requestFocus(final EditText editor) {
        editor.requestFocus();
        editor.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);

                v.removeOnLayoutChangeListener(this);
            }
        });
    }

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
}
