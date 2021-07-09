package algonquin.cst2335.chizhangandroidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyOpenHelper extends SQLiteOpenHelper {

    public static final String name = "DatabaseFile";
    public static final int version = 1;
    public static final String TABLE_NAME = "Messages";
    public static final String col_message = "Message";//declare column name
    public static final String col_send_receive = "SendOrReceive";
    public static final String col_time_sent = "TimeSent";

    public MyOpenHelper( Context context ) {
        super(context, name, null, version);
        //that's all the constructor does
    }

    //sql creation statement:
    @Override       //sqLiteDatabaes interprets SQL commands
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE Table " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                //create your columns:
                + col_message + " TEXT,"
                + col_send_receive + "INTEGER,"
                + col_time_sent + " TEXT);"
        ); //run some SQL


    }//CREATE Table WORDS (_id INTEGER PRIMARY KEY AUTOINCREMENT, Password text, TimeSent text);

    @Override
    public void onUpgrade(SQLiteDatabase db, int currentVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME); //delete the table
        onCreate(db);//creating a new one
    }
}


