package com.note.noteapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Neeraj on 19-01-2018.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>  {
List<Note> noteList;
Context context;
    public NoteAdapter(List<Note> noteList,Context context)
    {
        this.noteList = noteList;
        this.context = context;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent,
                false);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final NoteViewHolder holder, int position) {
        if(noteList != null && noteList.size() > 0)
        {
            final Note note = noteList.get(position);
            holder.tvTitle.setText(note.getTitle()+"");
            holder.tvGist.setText(note.getStory()+"");
            if(note.getLastUpdatedDate() != null )
            {
                Date date = new Date(note.getLastUpdatedDate());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                String mydate = java.text.DateFormat.getDateTimeInstance().format(calendar.getTime());
                holder.tvLastUpdatedDate.setText(mydate);
                // holder.ivHearted.setImageDrawable(note.isFavourite() ? R.drawable.heart : R.drawable.heart_outline);
            }

            holder.ivHearted.setImageResource(note.isFavourite() ? R.drawable.heart : R.drawable.heart_outline);
            holder.ivFav.setImageResource(note.isHearted() ? R.drawable.star : R.drawable.star_outline);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startNoteDetailActivity(note);
                }
            });
            holder.ivHearted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    note.setHearted(note.isHearted() ? false : true);
                    holder.ivHearted.setImageResource(note.isHearted() ? R.drawable.heart : R.drawable.heart_outline);
                    NoteDb.updateNote(context,note);

                }
            });
            holder.ivFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    note.setFavourite(note.isFavourite() ? false : true);
                    holder.ivFav.setImageResource(note.isFavourite() ? R.drawable.star : R.drawable.star_outline);
                    NoteDb.updateNote(context,note);
                }
            });
        }


    }

   /* @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case  R.id.iv_fav :

                break;
            case  R.id.iv_stare :
                break;
        }
    }*/

    private void startNoteDetailActivity(Note note)
    {
        Intent intent = new Intent(context, NoteDetailActivity.class);
        intent.putExtra(Util.NOTE,note);
        //intent.putExtra("Name",note.getTitle());
        //intent.putExtra("Story",note.getStory());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {

        return noteList != null ? noteList.size() : 0;
    }

    public void removeItem(int position) {
        noteList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }
    public void restoreItem(Note item, int position) {
        noteList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvTitle;
        private TextView tvGist;
        private TextView tvLastUpdatedDate;
        private ImageView ivHearted;
        private ImageView ivFav;
        public RelativeLayout viewBackground, viewForeground;
       public NoteViewHolder(View itemView)
       {
           super(itemView);
           tvTitle = itemView.findViewById(R.id.tv_title);
           tvGist = itemView.findViewById(R.id.tv_note_gist);
           tvLastUpdatedDate = itemView.findViewById(R.id.tv_last_updated_date);
           ivHearted = itemView.findViewById(R.id.iv_heart);
           ivFav = itemView.findViewById(R.id.iv_fav);
           viewBackground = itemView.findViewById(R.id.view_background);
           viewForeground = itemView.findViewById(R.id.view_foreground);
       }
    }

}
