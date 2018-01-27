package com.note.noteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Neeraj on 22-01-2018.
 */

public class NoteDb extends SQLiteOpenHelper {
    private static final String DB_NAME = "Note_Db";
    private static final  int DB_VERSION = 1;
    private static NoteDb noteDb;
    public NoteDb(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE `Note` (\n" +
                "\t`id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`title`\tTEXT,\n" +
                "\t`isFavourite`\tINTEGER,\n" +
                "\t`isHearted`\tINTEGER,\n" +
                "\t`story`\tTEXT,\n" +
                "\t`createdDate`\tTEXT,\n" +
                "\t`lastUpdatedDate`\tTEXT\n" +
                ")";
        db.execSQL(createTable);

    }

    public static NoteDb getInstance(Context context)
    {
        if(noteDb == null)
        {
            noteDb = new NoteDb(context);
        }
        return noteDb;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static boolean insertNote(Context context, Note note)
    {
        SQLiteDatabase db = NoteDb.getInstance(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",note.getTitle());
        values.put("isFavourite",note.isFavourite());
        values.put("isHearted",note.isHearted());
        values.put("story",note.getStory());
        values.put("createdDate",note.getCreatedDate() != null ? note.getCreatedDate().toString() :"");
        values.put("lastUpdatedDate",note.getLastUpdatedDate() != null ? note.getLastUpdatedDate().toString() : "");
       String [] ary = new String[]{"title","isFavourite","isHearted","story","createdDate","lastUpdatedDate"};
      long  l =   db.insert("Note",null,values);
      return l>0 ? true : false;
    }


    public static boolean updateNote(Context context, Note note)
    {
        SQLiteDatabase db = NoteDb.getInstance(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",note.getTitle());
        values.put("isFavourite",note.isFavourite());
        values.put("isHearted",note.isHearted());
        values.put("story",note.getStory());
        values.put("createdDate",note.getCreatedDate() != null ? note.getCreatedDate().toString() :"");
        values.put("lastUpdatedDate",note.getLastUpdatedDate() != null ? note.getLastUpdatedDate().toString() : "");
        int i = db.update("Note",values,"id = ?", new String[]{note.getNoteId()+""});
        return i>0 ? true : false;
    }

    /*public static Note getNoteById(Context context, int id)
    {
        SQLiteDatabase db = NoteDb.getInstance(context).getWritableDatabase();
        String [] columns = new String[]{"id","title","isFavourite","isHearted","story","createdDate","lastUpdatedDate"};
        Cursor findEntry = db.query("Note", columns, "id=?", new String[] { id+"" }, null, null, null);
        if(findEntry != null && findEntry.moveToFirst())
        {
            Note note =  new Note();
            note.setNoteId(findEntry.getInt(0));
            note.setTitle();
            Note note = new Note(cursor.getInt(0),cursor.getString(1),cursor.getString(4));
        }

    }*/

    public static boolean removeItem(Context context, Note note)
    {
        SQLiteDatabase db = NoteDb.getInstance(context).getWritableDatabase();
        int i = db.delete("Note","id = ?",new String[]{note.getNoteId()+""});
        return  i > 0 ? true : false;
    }
    public static ArrayList<Note> getNoteList(Context context)
    {
        ArrayList<Note> notes = new ArrayList<>();
       SQLiteDatabase db = NoteDb.getInstance(context).getWritableDatabase();
       Cursor cursor =  db.rawQuery("Select * from Note",null);
       return readNoteFromCursor(cursor);
    }

    public static ArrayList<Note> getFilteredNote(Context context)
    {
        SQLiteDatabase db = NoteDb.getInstance(context).getWritableDatabase();
        Cursor cursor = null;
        if (NotePreference.isFavouriteSelected(context))
        {
            int i = NotePreference.isFavouriteSelected(context)?1:0;
            cursor =  db.rawQuery("Select * FROM Note WHERE isFavourite = "+i ,null);
        }
        else if(NotePreference.isHeartedSelected(context))
        {
            int i = NotePreference.isHeartedSelected(context)?1:0;
            cursor =  db.rawQuery("Select * FROM Note WHERE isHearted = "+ i,null);
        }
        /*else  if (NotePreference.isPoemSelected(context))
        {
            cursor =  db.rawQuery("Select * FROM Note WHERE isFavourite = "+ NotePreference.isFavouriteSelected(context),null);
        }
        else if(NotePreference.isStorySelected(context))
        {
            cursor =  db.rawQuery("Select * FROM Note WHERE isFavourite = "+ NotePreference.isFavouriteSelected(context),null);
        }*/
        else
        {
            cursor =  db.rawQuery("Select * from Note",null);
        }

        //db.rawQuery("Select * FROM Note WHERE isPermanent = 1",null);
        return readNoteFromCursor(cursor);
    }
    private static ArrayList<Note> readNoteFromCursor(Cursor cursor)
    {
        ArrayList<Note> notes = new ArrayList<>();
        if(cursor != null && cursor.moveToFirst())
        {
            do
            {
                Note note = new Note();
                note.setNoteId(cursor.getInt(0));
                note.setTitle(cursor.getString(1));
                note.setFavourite(cursor.getInt(2)==1 ? true  : false);
                note.setHearted(cursor.getInt(3)==1 ? true  : false);
                note.setStory(cursor.getString(4));
                note.setLastUpdatedDate(cursor.getString(6));
                // Note note = new Note(cursor.getInt(0),cursor.getString(1),cursor.getString(4));
                notes.add(note);
            }while (cursor.moveToNext());
        }
        return notes;
    }
}
