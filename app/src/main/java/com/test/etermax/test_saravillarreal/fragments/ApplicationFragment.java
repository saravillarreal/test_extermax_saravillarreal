package com.test.etermax.test_saravillarreal.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.test.etermax.test_saravillarreal.activities.BaseActivity;
import com.test.etermax.test_saravillarreal.global.TestGlobalValues;

import java.io.Serializable;

/**
 * Created by Eduardo Luttinger on 16/10/2015.
 * <p/>
 * <p>All Android Fragments Class should extends this Class, so treat the similar behaviors in one place</p>
 */
public abstract class ApplicationFragment extends Fragment implements Serializable {

    /**
     * trace id for LOG proposal
     */
    protected static final String TAG = TestGlobalValues.TRACE_ID;

    /**
     * Mandatory
     * override this method to set the fragment name, for fragment transactions proposals
     *
     * @return
     */
    abstract public String getMyTag();

    /**
     * Mandatory
     * <p/>
     * override this method to set the main layout of the fragment
     *
     * @return Ej: R.layout.fragment_container
     */
    abstract protected Integer getFragmentLayout();


    /**
     * No Mandatory
     * <p/>
     * override this method to perform an action before the activity onBackPressed native behavior
     *
     * @return True to avoid the native activity behavior, False to let it be.
     */
    abstract public Boolean onBackPressed();

    /**
     * Mandatory
     * <p/>
     * override this method to initialize all fragment view components
     */
    abstract protected void setViews(View view);

    /**
     * Mandatory
     * override this method to set all fragment view components events
     */
    abstract protected void setViewsEvents();

    /**
     * No Mandatory
     * <p/>
     * override this method to clear all fragment view components
     */
    abstract public void clearAllViewComponents();


    public interface AppFragmentListener {
        public void goToFragment(ApplicationFragment fragment, int title);
    }

    private AppFragmentListener mFragmentListener;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(Boolean.TRUE);
        setHasOptionsMenu(Boolean.TRUE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        mFragmentListener = getFragmentListener();
        setViews(view);
        setViewsEvents();
        return view;
    }

    protected abstract AppFragmentListener getFragmentListener();

    /**
     * show the activity progress dialog
     *
     * @param message
     */
    public void showProgressDialog(String message) {
        ((BaseActivity) getActivity()).showDialog();
    }

    /**
     * dismiss the activity progress dialog
     */
    public void hideProgressDialog() {
        try {
            ((BaseActivity) getActivity()).dismissDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Android Cache Storage Manager object reference
     *
     * @return SharedPreferenceUtil Singleton Instance
     */
    /*protected SharedPreferenceUtil getSharedPreferences() {
        return SharedPreferenceUtil.getInstance(getActivity());
    }*/
}
