package com.note.noteapp;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class NoteDetailActivity extends AppCompatActivity {
private EditText etTitle, etStory;
private String title,story;
private MenuItem saveMenuItem,editMenuItem;
private boolean isNew;
private Note note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        etTitle = findViewById(R.id.et_title);
        etStory = findViewById(R.id.et_story);
        Bundle bundle =  getIntent().getExtras();
        if(bundle != null)
        {
            isNew = bundle.getBoolean("New");
            note = bundle.getParcelable(Util.NOTE);
            if(note != null)
            {
                title = note.getTitle(); // bundle.getString("Name");
                story =  note.getStory(); //bundle.getString("Story");
                etTitle.setText(title);
                etStory.setText(story);
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail,menu);
        saveMenuItem = menu.findItem(R.id.save);
        editMenuItem = menu.findItem(R.id.edit);
        if(isNew)
        {
            saveMenuItem.setVisible(true);
            editMenuItem.setVisible(false);
            etTitle.setEnabled(true);
            etStory.setEnabled(true);
        }
        else
        {
            saveMenuItem.setVisible(false);
            editMenuItem.setVisible(true);
            etTitle.setEnabled(false);
            etStory.setEnabled(false);

        }
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.save:
                onSaveClick();
                return true;
            case R.id.edit:
                onEditClick();
                return true;
            case R.id.undo:
                onUndoClick();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }


    private void onSaveClick()
    {
        try
        {
            title = etTitle.getText().toString();
            story = etStory.getText().toString();
            if(title.isEmpty() && story.isEmpty())
            {
                Toast.makeText(this,"required field",Toast.LENGTH_LONG).show();
            }
            else
            {
                saveMenuItem.setVisible(false);
                editMenuItem.setVisible(true);
                etTitle.setEnabled(false);
                etStory.setEnabled(false);
                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS");
                String date = sdf.format(new Date());
                String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                if(isNew)
                {
                    note = new Note();
                    note.setTitle(title);
                    note.setStory(story);
                    note.setLastUpdatedDate(mydate);
                    NoteDb.insertNote(this,note);
                }
                else
                {
                    note.setTitle(title);
                    note.setStory(story);
                    note.setLastUpdatedDate(mydate);
                    NoteDb.updateNote(this,note);
                }
            }
        }

        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }
    private void onEditClick()
    {
        saveMenuItem.setVisible(true);
        editMenuItem.setVisible(false);
        etTitle.setEnabled(true);
        etStory.setEnabled(true);
    }
    private void onUndoClick()
    {
        if(isNew)
        {
            etTitle.setText("");
            etStory.setText("");
        }
        else
        {
            etTitle.setText(title);
            etStory.setText(story);
        }
    }
}
