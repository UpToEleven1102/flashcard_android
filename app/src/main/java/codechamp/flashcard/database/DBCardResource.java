package codechamp.flashcard.database;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import codechamp.flashcard.infrastructure.Mode;
import codechamp.flashcard.model.CardEntity;

import java.util.ArrayList;
import java.util.List;

public class DBCardResource implements IDBResource<CardEntity>{
    private SQLiteDatabase _db;
    private SQLiteOpenHelper _dbHelper;

    public DBCardResource(Context context) {
        _dbHelper = new DBHelper(context);
        _db = _dbHelper.getWritableDatabase();
    }

    public CardEntity create(CardEntity card) {
        _db.insert(ItemsTable.CARD_TABLE_NAME, null, card.toValues());
        return card;
    }

    @Override
    public CardEntity get(String id) {
        return null;
    }

    @Override
    public List<CardEntity> getByName(String name) {
        return null;
    }

    public List<CardEntity> getAll() {
        List<CardEntity> cards = new ArrayList<CardEntity>();
        Cursor cursor = _db.query(ItemsTable.CARD_TABLE_NAME, ItemsTable.CARD_TABLE_COLUMNS, null,
                null, null, null, null);

        toList(cursor, cards);

        return cards;
    }

    public List<CardEntity> getAll(String setId) {
        List<CardEntity> cards = new ArrayList<CardEntity>();
        Cursor cursor = _db.query(ItemsTable.CARD_TABLE_NAME, ItemsTable.CARD_TABLE_COLUMNS,
                ItemsTable.CARD_COLUMN_SET_ID + "=?",new String[]{setId}, null, null, null);
        toList(cursor, cards);
        return cards;
    }

    @Override
    public long getItemCount() {
        return DatabaseUtils.queryNumEntries(_db, ItemsTable.CARD_TABLE_NAME);
    }

    public void toList(Cursor cursor, List<CardEntity> cards){
        while(cursor.moveToNext()){
            CardEntity card = new CardEntity();
            card.setId(cursor.getString(cursor.getColumnIndex(ItemsTable.CARD_COLUMN_ID)));
            card.setQuestion(cursor.getString(cursor.getColumnIndex(ItemsTable.CARD_COLUMN_QUESTION)));
            card.setAnswer(cursor.getString(cursor.getColumnIndex(ItemsTable.CARD_COLUMN_ANSWER)));
            card.setSetId(cursor.getString(cursor.getColumnIndex(ItemsTable.CARD_COLUMN_SET_ID)));
            cards.add(card);
        }
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
    public int remove(String id) {
        return _db.delete(ItemsTable.CARD_TABLE_NAME, ItemsTable.CARD_COLUMN_ID + "=?", new String[]{id});
    }

    public void open() {
        _db = _dbHelper.getWritableDatabase();
    }

    public void close() {
        _db.close();
    }
}
