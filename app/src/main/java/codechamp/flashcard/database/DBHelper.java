package codechamp.flashcard.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static String DB_NAME = "code_champ_flash_card";
    public static int VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ItemsTable.CREATE_GROUP_TABLE);
        db.execSQL(ItemsTable.CREATE_SET_TABLE);
        db.execSQL(ItemsTable.CREATE_CARD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(ItemsTable.REMOVE_GROUP_TABLE);
        db.execSQL(ItemsTable.REMOVE_CARD_SET_TABLE);
        db.execSQL(ItemsTable.REMOVE_CARD_TABLE);
        db.execSQL(ItemsTable.CREATE_GROUP_TABLE);
        db.execSQL(ItemsTable.CREATE_SET_TABLE);
        db.execSQL(ItemsTable.CREATE_CARD_TABLE);
    }
}
