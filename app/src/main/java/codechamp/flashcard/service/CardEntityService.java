package codechamp.flashcard.service;

import android.content.Context;
import codechamp.flashcard.database.DBCardResource;
import codechamp.flashcard.infrastructure.Mode;
import codechamp.flashcard.model.CardEntity;

import java.util.List;

public class CardEntityService implements IService<CardEntity>{
    private DBCardResource _dbResource;
    private Context _context;

    public CardEntityService(Context context) {
        this._context = context;
        _dbResource = new DBCardResource(context);
    }

    @Override
    public CardEntity create(CardEntity item, Mode mode) {
        if(mode == Mode.OFFLINE){
            return _dbResource.create(item);
        }
        return null;
    }

    @Override
    public List<CardEntity> getAll(Mode mode) {
        if(mode == Mode.OFFLINE) {
            return _dbResource.getAll();
        }
        return null;
    }

    public List<CardEntity> getAll(String setId, Mode mode) {
        if(mode == Mode.OFFLINE){
            return _dbResource.getAll(setId);
        }
        return null;
    }

    @Override
    public long getItemsCount(Mode mode) {
        if(mode == Mode.OFFLINE) {
            return _dbResource.getItemCount();
        }
            return 0;
    }

    @Override
    public void openConnection(Mode mode) {
        if(mode == Mode.OFFLINE){
            _dbResource.open();
        }
    }

    @Override
    public void closeConnection(Mode mode) {
        if(mode == Mode.OFFLINE) {
            _dbResource.close();
        }
    }

    @Override
    public int remove(String itemId, Mode mode) {
        if(mode == Mode.OFFLINE){
            return _dbResource.remove(itemId);
        }
        return 0;
    }

    @Override
    public void beginTransaction() {
        _dbResource.beginTransaction();
    }

    @Override
    public void saveTransaction() {
        _dbResource.executeTransaction();
    }

    @Override
    public void endTransaction() {
        _dbResource.endTransaction();
    }

    @Override
    public List<CardEntity> getItemsByName(String searchText, Mode mode) {
        return null;
    }

    @Override
    public CardEntity getById(String id, Mode mode) {
        return null;
    }
}
