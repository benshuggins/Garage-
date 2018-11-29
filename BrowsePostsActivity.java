package com.test.ben.hyperproject;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.test.robert.hypergaragesale.R;

import java.util.ArrayList;

import static com.test.robert.hypergaragesale.R.id.fabBrowse;


public class BrowsePostsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private PostsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_posts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.browse_toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.posts_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
       


        PostsDbHelper mDbHelper = new PostsDbHelper(this);
        db = mDbHelper.getReadableDatabase();

        mAdapter = new PostsAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);



        mRecyclerView.addOnItemTouchListener(
                new InterfaceListener(getApplicationContext(), new InterfaceListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        Intent postDetailsIntent=new Intent(getApplicationContext(),PostDetails.class);
                        postDetailsIntent.putExtra("image",getDataSet().get(position).getmImage());
                        postDetailsIntent.putExtra("title",getDataSet().get(position).getmTitle());
                        postDetailsIntent.putExtra("price",getDataSet().get(position).getmPrice());
                        postDetailsIntent.putExtra("desc",getDataSet().get(position).getmDesc());

                        if(getDataSet().get(position).getmLocation()!=null)
                        {
                            postDetailsIntent.putExtra("location",getDataSet().get(position).getmLocation());
                            postDetailsIntent.putExtra("latitude",getDataSet().get(position).getmLatitude());
                            postDetailsIntent.putExtra("longitude",getDataSet().get(position).getmLongitude());
                        }
                        startActivity(postDetailsIntent);

                    }
                })
        );

        FloatingActionButton fab = (FloatingActionButton) findViewById(fabBrowse);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });






    }
        @Override
        protected void onResume () {
            super.onResume();
            mAdapter.notifyDataSetChanged();
        }


        private ArrayList<BrowsePosts> getDataSet () {

            String[] projection = {
                    Posts.PostEntry.COLUMN_TITLE,
                    Posts.PostEntry.COLUMN_PRICE,
                    Posts.PostEntry.COLUMN_IMAGE,
                    Posts.PostEntry.COLUMN_DESCRIPTION,
                    Posts.PostEntry.COLUMN_LATITUDE,
                    Posts.PostEntry.COLUMN_LONGITUDE,
                    Posts.PostEntry.COLUMN_LOCATION,

            };


            String sortOrder =
                    Posts.PostEntry.COLUMN_PRICE + " DESC";

            Cursor cursor = db.query(
                    Posts.PostEntry.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    sortOrder
            );


            ArrayList<BrowsePosts> browsePosts = new ArrayList<BrowsePosts>();
            if (cursor.moveToFirst()) {
                do {
                    browsePosts.add(new BrowsePosts(
                            cursor.getString(cursor.getColumnIndex(Posts.PostEntry.COLUMN_TITLE)),
                            cursor.getString(cursor.getColumnIndex(Posts.PostEntry.COLUMN_PRICE)),
                            cursor.getString(cursor.getColumnIndex(Posts.PostEntry.COLUMN_IMAGE)),
                            cursor.getString(cursor.getColumnIndex(Posts.PostEntry.COLUMN_DESCRIPTION)),
                            cursor.getString(cursor.getColumnIndex(Posts.PostEntry.COLUMN_LATITUDE)),
                            cursor.getString(cursor.getColumnIndex(Posts.PostEntry.COLUMN_LONGITUDE)),
                            cursor.getString(cursor.getColumnIndex(Posts.PostEntry.COLUMN_LOCATION))
                            ));
                } while (cursor.moveToNext());
            }

            return browsePosts;
        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setIconifiedByDefault(false);

       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {

               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {

               mAdapter.getFilter().filter(newText);

               return true;
           }
       });


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

}
