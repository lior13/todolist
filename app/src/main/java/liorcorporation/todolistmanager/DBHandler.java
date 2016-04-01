package liorcorporation.todolistmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by lior.
 */
public class DBHandler extends SQLiteOpenHelper{
    private static final String TABLE_EVENTS = "events";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String DATE = "Date";
    private static final String DATABASE_NAME = "DB";
    private static final int DATABASE_VERSION = 1;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "(" + KEY_ID +
                " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + DATE + " TEXT" + ")";
        db.execSQL(CREATE_EVENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        // Create tables again
        onCreate(db);
    }

    // Adding new event
    public void addEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, event.getTitle()); // event Name
        values.put(DATE, event.getDate().toString()); // event Phone Number

        // Inserting Row
        db.insert(TABLE_EVENTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single event
    public Event getEvent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENTS, new String[] { KEY_ID,
                        KEY_NAME, DATE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            String dtStart = cursor.getString(2);
            SimpleDateFormat format = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.ENGLISH);
            try {
                Date date = format.parse(dtStart);
                return new Event(cursor.getString(1), date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // return event
        }
        return null;
    }

    // Getting All Events
    public List<Event> getAllEvents() {
        List<Event> eventList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EVENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(Integer.parseInt(cursor.getString(0)));
                event.setTitle(cursor.getString(1));
                String dtStart = cursor.getString(2);
                SimpleDateFormat format = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.ENGLISH);
                try {
                    Date date = format.parse(dtStart);
                    event.setDate(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // Adding event to list
                eventList.add(event);
            } while (cursor.moveToNext());
        }
        // return event list
        cursor.close();
        return eventList;
    }

    // Getting events Count
    public int getEventsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EVENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }
    // Updating single event
    public int updateEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, event.getTitle());
        values.put(DATE, event.getDate().toString());

        // updating row
        return db.update(TABLE_EVENTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(event.getId())});
    }

    // Deleting single event
    public void deleteEvent(Event event)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS, KEY_ID + " = ?",
                new String[]{String.valueOf(event.getId())});
        db.close();
        Event.removedId(event.getId());
    }

    public void deleteAllEvents()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_EVENTS);
        Event.clear();
        db.close();
    }
}