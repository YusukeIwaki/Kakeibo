package org.yi01.kakeibo.fragment;

import android.support.v4.app.Fragment;

abstract class AbstractFragment extends Fragment {
    protected void finish(){
        if(getFragmentManager().getBackStackEntryCount()==0){
            getActivity().finish();
        }
        else {
            getFragmentManager().popBackStack();
        }
    }
}
