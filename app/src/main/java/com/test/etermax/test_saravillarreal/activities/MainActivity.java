package com.test.etermax.test_saravillarreal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.test.etermax.test_saravillarreal.R;
import com.test.etermax.test_saravillarreal.adapters.PhotosAdapter;
import com.test.etermax.test_saravillarreal.fragments.MainFragment;
import com.test.etermax.test_saravillarreal.fragments.PhotosGridFragment;
import com.test.etermax.test_saravillarreal.interfaces.FragmentInterface;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements FragmentInterface {

    public final static String TAG = "MainActivity";
    FragmentTransaction transaction;
    Fragment fragment;


    private RecyclerView recyclerView;
    private PhotosAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Test Etermax");
                startActivity(Intent.createChooser(emailIntent, "Send email using:"));
            }
        });

       MainFragment mainFragment = new MainFragment();
        getFragment(mainFragment, null, true, MainFragment.TAG);
        /*setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        // recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        // Use the default animator
        // recyclerView.setItemAnimator(new DefaultItemAnimator());
        // you could add item decorators
        //	RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        //	recyclerView.addItemDecoration(itemDecoration);

        ArrayList<String> values = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            values.add("Test" + i);
        }

        // specify an adapter (see also next example)
        mAdapter = new PhotosAdapter(values);
        recyclerView.setAdapter(mAdapter);*/




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_grid_view) {
            PhotosGridFragment photosGridFragment = new PhotosGridFragment();
            getFragment(photosGridFragment, null, true, PhotosGridFragment.TAG);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getFragment(Fragment fragment, Object object, Boolean addToStack, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        this.fragment = fragment;
        if(!tag.isEmpty()){
            transaction.replace(R.id.container, fragment, tag);
        }else{
            transaction.replace(R.id.container, fragment);
        }
        if(addToStack){
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }
}
