package codechamp.flashcard.database;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import codechamp.flashcard.model.SetEntity;

import java.util.ArrayList;
import java.util.List;

public class DbSetResource implements IDBResource<SetEntity>{
    SQLiteOpenHelper _dbHelper ;
    SQLiteDatabase _db;

    public DbSetResource(Context context){
        _dbHelper = new DBHelper(context);
        _db = _dbHelper.getWritableDatabase();
    }

    public SetEntity create(SetEntity entity) {
        _db.insert(ItemsTable.CARD_SET_TABLE_NAME, null, entity.toValues());
        return entity;
    }

    @Override
    public SetEntity get(String id) {
        Cursor cursor = _db.query(true, ItemsTable.CARD_SET_TABLE_NAME, ItemsTable.CARD_SET_TABLE_COLUMNS,
                ItemsTable.CARD_SET_COLUMN_ID + "=?", new String[]{id}, null, null, null, null);
        SetEntity entity = null;
        while(cursor.moveToNext()){
            entity = new SetEntity(cursor.getString(cursor.getColumnIndex(ItemsTable.CARD_SET_COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(ItemsTable.CARD_SET_COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(ItemsTable.CARD_SET_COLUMN_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(ItemsTable.CARD_SET_COLUMN_GROUP_ID)));
        }
        return entity;
    }

    @Override
    public List<SetEntity> getByName(String name) {
        Cursor cursor = _db.query(ItemsTable.CARD_SET_TABLE_NAME, ItemsTable.CARD_SET_TABLE_COLUMNS,
                ItemsTable.CARD_SET_COLUMN_NAME + "=?", new String[]{name}, null, null, null);
        List<SetEntity> entities = new ArrayList<>();
        while(cursor.moveToNext()){
            entities.add(new SetEntity(cursor.getString(cursor.getColumnIndex(ItemsTable.CARD_SET_COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(ItemsTable.CARD_SET_COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(ItemsTable.CARD_SET_COLUMN_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(ItemsTable.CARD_SET_COLUMN_GROUP_ID))));
        }
        return entities;
    }

    public List<SetEntity> getAll() {
        List<SetEntity> sets = new ArrayList<>();
        Cursor cursor = _db.query(ItemsTable.CARD_SET_TABLE_NAME, ItemsTable.CARD_SET_TABLE_COLUMNS, null, null,
                null, null, null);
        while(cursor.moveToNext()){
            sets.add(new SetEntity(cursor.getString(cursor.getColumnIndex(ItemsTable.CARD_SET_COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(ItemsTable.CARD_SET_COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(ItemsTable.CARD_SET_COLUMN_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(ItemsTable.CARD_SET_COLUMN_GROUP_ID))));
        }
        return sets;
    }

    public List<SetEntity> getAll(String folderId){
        List<SetEntity> sets = new ArrayList<>();
        String[] args = {folderId};
        Cursor cursor = _db.query(ItemsTable.CARD_SET_TABLE_NAME, ItemsTable.CARD_SET_TABLE_COLUMNS,
                ItemsTable.CARD_SET_COLUMN_GROUP_ID + "=?", args,
                null, null, null);
        while(cursor.moveToNext()){
            sets.add(new SetEntity(cursor.getString(cursor.getColumnIndex(ItemsTable.CARD_SET_COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(ItemsTable.CARD_SET_COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(ItemsTable.CARD_SET_COLUMN_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(ItemsTable.CARD_SET_COLUMN_GROUP_ID))));
        }
        return sets;
    }

    @Override
    public int remove(String id) {
        return _db.delete(ItemsTable.CARD_SET_TABLE_NAME, ItemsTable.CARD_SET_COLUMN_ID + "=?", new String[]{id});
    }

    @Override
    public void beginTransaction() {
        _db.beginTransaction();
    }

    @Override
    public void executeTransaction() {
        _db.setTransactionSuccessful();
    }

    @Override
    public void endTransaction() {
        _db.endTransaction();
    }


    @Override
    public long getItemCount() {
        return DatabaseUtils.queryNumEntries(_db, ItemsTable.CARD_SET_TABLE_NAME);
    }

    public void open() {
        _db = _dbHelper.getWritableDatabase();
    }

    public void close() {
        _db.close();
    }
}
