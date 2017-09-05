package com.example.yukinohara.accuweather.data.local;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.yukinohara.accuweather.data.model.Weather;

/**
 * Created by YukiNoHara on 4/19/2017.
 */

public class WeatherProvider extends ContentProvider {
    private WeatherDbHelper mOpenDbHelper;

    public static final int CODE_WEATHER = 100;
    public static final int CODE_WEATHER_WITH_ID = 101;

    public static final UriMatcher sUriMatcher = buildUriMatcher();

    private static final UriMatcher buildUriMatcher(){
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = WeatherContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority, WeatherContract.PATH_WEATHER, CODE_WEATHER);
        uriMatcher.addURI(authority, WeatherContract.PATH_WEATHER + "/#", CODE_WEATHER_WITH_ID);

        return uriMatcher;

    }

    @Override
    public boolean onCreate() {
        mOpenDbHelper = new WeatherDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        int match = sUriMatcher.match(uri);

        switch (match){
            case CODE_WEATHER:{
                cursor = mOpenDbHelper.getReadableDatabase().query(WeatherContract.WeatherEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }

            case CODE_WEATHER_WITH_ID:{
                String id = uri.getLastPathSegment();
                String[] selectionId = new String[]{id};
                cursor = mOpenDbHelper.getReadableDatabase().query(WeatherContract.WeatherEntry.TABLE_NAME,
                        projection,
                        WeatherContract.WeatherEntry._ID + "=?",
                        selectionId,
                        null,
                        null,
                        sortOrder);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mOpenDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
//        Log.e("INSERT CODE", match + "");

        Uri returnUri = null;
        switch (match){
            case CODE_WEATHER:{
                long id = db.insert(WeatherContract.WeatherEntry.TABLE_NAME, null, values);
                if (id > 0){
                    returnUri = ContentUris.withAppendedId(uri, id);
                }
                break;
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
//        Log.e("BULKINSERT CODE", match + "");

        switch (match){
            case CODE_WEATHER:{
                db.beginTransaction();
                int rowsInserted = 0;

                try{
                    for (ContentValues cv : values){
                        long id = db.insert(WeatherContract.WeatherEntry.TABLE_NAME, null, cv);
                        if (id != -1 ) rowsInserted++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (rowsInserted > 0){
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;
            }

            default:
                return super.bulkInsert(uri, values);
        }

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mOpenDbHelper.getWritableDatabase();
        int rowsDeleted = 0;

        if (selection == null){
            selection = "1";
        }

        int match = sUriMatcher.match(uri);
//        Log.e("DELETE CODE", match + "");
        switch (match){
            case CODE_WEATHER: {
                rowsDeleted = db.delete(WeatherContract.WeatherEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsDeleted > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
