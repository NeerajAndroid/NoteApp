package com.note.noteapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener , RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{
 private RecyclerView recyclerView;
 private MenuItem menuItem [] = new MenuItem[4];
 private  DrawerLayout drawer;
 private ArrayList<Note> noteList = new ArrayList<>();
 private NoteAdapter noteAdapter;
 private CoordinatorLayout coordinatorLayout ;
 private boolean isDeleted;
 private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycle_view);

         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                    menuItem[0].setChecked(NotePreference.isFavouriteSelected(MainActivity.this));
                }
            }
        });
        menuItem [0]= navigationView.getMenu().getItem(0);
        menuItem [1]= navigationView.getMenu().getItem(1);
        menuItem [2]= navigationView.getMenu().getItem(2);
        menuItem [3]= navigationView.getMenu().getItem(3);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoteDetailActivity.class);
                intent.putExtra("New",true);
               startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noteList = NoteDb.getNoteList(this);
        noteAdapter = new NoteAdapter(noteList,this);
        recyclerView.setAdapter(noteAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        NotePreference.clearPreference(this);
        if (id == R.id.hearted)
        {
            item.setChecked(item.isChecked());
            NotePreference.setIsHeartedSelected(this,!item.isChecked());
            // Handle the camera action

        }
        else if (id == R.id.favourite)
        {
            item.setChecked(item.isChecked());
            NotePreference.setIsFavouriteSelected(this,!item.isChecked());
        }
        else if (id == R.id.poem)
        {
            item.setChecked(item.isChecked());
            NotePreference.setIsPoemSelected(this,item.isChecked());
        }
        else if (id == R.id.story)
        {
            item.setChecked(item.isChecked());
            NotePreference.setIsStorySelected(this,item.isChecked());
        }
        /*else if(id == R.id.filter)
        {
            NotePreference.clearPreference(this);
        }*/
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void onApplyClick(View view)
    {
        if(drawer != null)
        {
            drawer.closeDrawer(Gravity.RIGHT);
        }
        noteList = NoteDb.getFilteredNote(this);
        recyclerView.setAdapter(new NoteAdapter(noteList,this));
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof NoteAdapter.NoteViewHolder)
        {
             isDeleted = true;
            // get the removed item name to display it in snack bar
            String name = noteList.get(viewHolder.getAdapterPosition()).getTitle();

            // backup of removed item for undo purpose
            final Note deletedItem = noteList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            noteAdapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar.make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    noteAdapter.restoreItem(deletedItem, deletedIndex);
                    isDeleted = false;
                }
            });

            snackbar.addCallback(new Snackbar.Callback() {

                @Override
                public void onDismissed(Snackbar snackbar, int event) {
                    //see Snackbar.Callback docs for event details
                    if(isDeleted)
                    {
                        NoteDb.removeItem(context,deletedItem);
                    }
                }

                @Override
                public void onShown(Snackbar snackbar) {

                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}


