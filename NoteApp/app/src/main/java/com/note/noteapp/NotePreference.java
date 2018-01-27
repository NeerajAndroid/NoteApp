package com.note.noteapp;

import android.content.Context;

/**
 * Created by Neeraj on 23-01-2018.
 */

public class NotePreference  {

    private final static String PREFS_NAME = "NotePreference";


    private final static String IS_FAVOURITE_SELECTED ="IS_FAVOURITE_SELECTED";
    private final static String IS_HEARTED_SELECTED   ="IS_HEARTED_SELECTED";
    private final static String IS_POEM_SELECTED  ="IS_HEARTED_SELECTED";
    private final static String IS_STORY_SELECTED   ="IS_HEARTED_SELECTED";

    /*for storing boolean value*/
    private static synchronized void setProperty(final Context context, final String name, final boolean value)
    {
        if(context != null)
        {
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().putBoolean(name, value).commit();
        }

    }

    private static synchronized boolean getProperty(final Context context, final String name, final boolean defaultValue)
    {
        try
        {
            return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getBoolean(name, defaultValue);
        }
        catch (final ClassCastException e)
        {
            setProperty(context, name, defaultValue);
            return defaultValue;
        }catch(Exception ex){
            if(BuildConfig.DEBUG){
                System.out.println(ex.toString());
            }
            return defaultValue;
        }
    }

    public static boolean isFavouriteSelected(Context context)
    {
        boolean isDBCreated = getProperty(context, IS_FAVOURITE_SELECTED, false);
        return isDBCreated;
    }

    public static void setIsFavouriteSelected (Context context, boolean isDBCreated)
    {
        setProperty(context,IS_FAVOURITE_SELECTED,isDBCreated);
    }

    public static boolean isHeartedSelected(Context context)
    {
        boolean isDBCreated = getProperty(context, IS_HEARTED_SELECTED, false);
        return isDBCreated;
    }

    public static void setIsHeartedSelected (Context context, boolean isDBCreated)
    {
        setProperty(context,IS_HEARTED_SELECTED,isDBCreated);
    }

    public static boolean isPoemSelected(Context context)
    {
        boolean isDBCreated = getProperty(context, IS_POEM_SELECTED, false);
        return isDBCreated;
    }

    public static void setIsPoemSelected (Context context, boolean isDBCreated)
    {
        setProperty(context,IS_POEM_SELECTED,isDBCreated);
    }

    public static boolean isStorySelected(Context context)
    {
        boolean isDBCreated = getProperty(context, IS_STORY_SELECTED, false);
        return isDBCreated;
    }

    public static void setIsStorySelected (Context context, boolean isDBCreated)
    {
        setProperty(context,IS_STORY_SELECTED,isDBCreated);
    }

    public static void clearPreference(Context context)
    {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().clear().commit();
    }

}
