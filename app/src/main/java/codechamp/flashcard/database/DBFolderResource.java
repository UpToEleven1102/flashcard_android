package codechamp.flashcard.database;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import codechamp.flashcard.model.FolderEntity;

import java.util.ArrayList;
import java.util.List;

public class DBFolderResource implements IDBResource<FolderEntity>{
    private SQLiteDatabase _db;
    private SQLiteOpenHelper _dbHelper;
    private Context _context;
    public DBFolderResource(Context context){
        this._context = context;
        _dbHelper = new DBHelper(context);
        _db = _dbHelper.getWritableDatabase();
        seed();
    }

    public FolderEntity create(FolderEntity folderEntity) {
        _db.insert(ItemsTable.GROUP_TABLE_NAME, null, folderEntity.toValues());
        return folderEntity;
    }

    @Override
    public FolderEntity get(String id) {
        FolderEntity entity = new FolderEntity();
        Cursor cursor = _db.query(true, ItemsTable.GROUP_TABLE_NAME, ItemsTable.GROUP_TABLE_COLUMNS,
                ItemsTable.GROUP_COLUMN_ID + "=?", new String[]{id}, null, null,
                null, null);
        while(cursor.moveToNext()) {
            entity.setId(cursor.getString(cursor.getColumnIndex(ItemsTable.GROUP_COLUMN_ID)));
            entity.setFolderName(cursor.getString(cursor.getColumnIndex(ItemsTable.GROUP_COLUMN_NAME)));
        }
        return entity;
    }

    @Override
    public List<FolderEntity> getByName(String name) {
        return null;
    }

    public long getItemCount() {
        return DatabaseUtils.queryNumEntries(_db,ItemsTable.GROUP_TABLE_NAME);
    }

    public List<FolderEntity> getAll(){
        List<FolderEntity> folderEntities = new ArrayList<>();
        Cursor cursor = _db.query(ItemsTable.GROUP_TABLE_NAME, ItemsTable.GROUP_TABLE_COLUMNS,
                null, null, null,null, null);
        while(cursor.moveToNext()){
            FolderEntity folderEntity = new FolderEntity();
            folderEntity.setId(cursor.getString(cursor.getColumnIndex(ItemsTable.GROUP_COLUMN_ID)));
            folderEntity.setFolderName(cursor.getString(cursor.getColumnIndex(ItemsTable.GROUP_COLUMN_NAME)));
            folderEntities.add(folderEntity);
        }
        return folderEntities;
    }

    @Override
    public List<FolderEntity> getAll(String st) {
        return null;
    }


    public void seed() {
        if(getItemCount()==0) {
            try {
                _db.beginTransaction();
                this.create(new FolderEntity("Group 1"));
                this.create(new FolderEntity("Folder 2"));
                this.create(new FolderEntity("Folder 3"));
                this.create(new FolderEntity("Folder 4"));
                _db.setTransactionSuccessful();
                _db.endTransaction();
                Toast.makeText(_context, "Added data", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                _db.endTransaction();
            }
        }
    }

    @Override
    public int remove(String id) {
        return _db.delete(ItemsTable.GROUP_TABLE_NAME, ItemsTable.CARD_SET_COLUMN_ID + "=?", new String[]{id});
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

    public void open() {
        _db = _dbHelper.getWritableDatabase();
    }

    public void close() {
        _db.close();
    }
}
