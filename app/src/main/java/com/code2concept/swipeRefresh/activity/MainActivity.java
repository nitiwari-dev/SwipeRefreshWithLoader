package com.code2concept.swipeRefresh.activity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListView;

import com.code2concept.swipeRefresh.R;
import com.code2concept.swipeRefresh.adapter.SwipeAdapter;

public class MainActivity extends FragmentActivity implements  LoaderManager.LoaderCallbacks<Cursor>  ,SwipeRefreshLayout.OnRefreshListener {

    private static final int LOADER_ID = 100;
    private ListView videoList;
    private SwipeRefreshLayout swipeContainer;
    private Context context;
    private SwipeAdapter swipeListAdapter;
    private String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context         = MainActivity.this;
        videoList       = (ListView) findViewById(R.id.video_listView);
        swipeContainer  = (SwipeRefreshLayout) findViewById(R.id.swipe_container);


        swipeContainer.setColorSchemeResources(
                R.color.swiperedcolor,
                R.color.swipegreencolor,
                R.color.swipeyellowcolor,
                R.color.swipebluecolor);


        swipeListAdapter = new SwipeAdapter(context , null  , 0);
        videoList.setAdapter(swipeListAdapter);


        swipeContainer.setOnRefreshListener(this);

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Log.d(TAG , "||onCreateLoader called||");
        return new CursorLoader(context ,MediaStore.Video.Media.EXTERNAL_CONTENT_URI ,null , null , null , MediaStore.Video.Media.TITLE + " collate nocase ");
    }


    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        if (swipeListAdapter != null && cursorLoader != null){

            Log.d(TAG , "||onLoadFinished called||");

            swipeListAdapter.swapCursor(cursor);

            swipeContainer.post(new Runnable() {
                @Override
                public void run() {
                    swipeContainer.setRefreshing(false);
                }
            });
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        if (swipeListAdapter != null) {
            Log.d(TAG , "||onLoaderReset called||");
            swipeListAdapter.swapCursor(null);
        }
    }


    /**
     * Whenever swipe refresh starts we get callback here.Here is the logic for that.
     */
    @Override
    public void onRefresh() {
        Log.d(TAG , "||onRefresh called||");
        getSupportLoaderManager().restartLoader(LOADER_ID , null ,this);
    }
}
