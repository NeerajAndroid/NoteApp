package com.note.noteapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Neeraj on 19-01-2018.
 */

public class Note implements Parcelable
{
    private int noteId;
    private String title;
    private String story;
    private boolean isFavourite;
    private boolean isHearted;
    private String createdDate;
    private String lastUpdatedDate;
    public static final Parcelable.Creator CREATOR
            = new Creator() {
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public Note()
    {

    }
    public Note(Parcel parcel)
    {
      noteId = parcel.readInt();
      title = parcel.readString();
      story = parcel.readString();
      isFavourite = parcel.readByte() == 1 ? true : false;
      isHearted = parcel.readByte() == 1 ? true : false;
      createdDate = parcel.readString();
      lastUpdatedDate = parcel.readString();

    }
    public Note(int noteId,String title,String story)
    {
        this.noteId = noteId;
        this.title = title;
        this.story = story;
    }
   /* public Note(String title,String story,boolean isFavourite, boolean isHearted, Date createdDate, Date lastUpdatedDate )
    {
          this.title = title;
          this.story = story;
          this.isFavourite = isFavourite;
          this.isHearted = isHearted;
          this.createdDate = createdDate;
          this.lastUpdatedDate = lastUpdatedDate;
    }*/

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(noteId);
        dest.writeString(title);
        dest.writeString(story);
        dest.writeByte((byte)(isFavourite ? 1 : 0));
        dest.writeByte((byte)(isHearted ? 1 : 0));
        dest.writeString(createdDate);
        dest.writeString(lastUpdatedDate);

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public boolean isHearted() {
        return isHearted;
    }

    public void setHearted(boolean hearted) {
        isHearted = hearted;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
