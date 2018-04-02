package codechamp.flashcard.service;

import android.content.Context;
import codechamp.flashcard.database.DbSetResource;
import codechamp.flashcard.infrastructure.Mode;
import codechamp.flashcard.model.SetEntity;

import java.util.List;

public class SetEntityService implements IService<SetEntity> {
    private DbSetResource _dbResource;

    public SetEntityService(Context context) {
        _dbResource = new DbSetResource(context);
    }

    @Override
    public SetEntity create(SetEntity item, Mode mode) {
        if(mode == Mode.OFFLINE){
            return _dbResource.create(item);
        }
        return null;
    }

    @Override
    public List<SetEntity> getAll(Mode mode) {
        if(mode == Mode.OFFLINE) {
            return _dbResource.getAll();
        }
        return null;
    }

    public List<SetEntity> getAll(String folderId, Mode mode){
        if(mode == Mode.OFFLINE){
            return _dbResource.getAll(folderId);
        }
        return null;
    }

    @Override
    public int remove(String itemId, Mode mode) {
        if(mode == Mode.OFFLINE)
            return _dbResource.remove(itemId);
        return 0;
    }

    @Override
    public long getItemsCount(Mode mode) {
        if(mode == Mode.OFFLINE) {
            return _dbResource.getItemCount();
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
    public void openConnection(Mode mode) {
        if(mode == Mode.OFFLINE) {
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
    public List<SetEntity> getItemsByName(String searchText, Mode mode) {
        if(mode == Mode.OFFLINE){
            return _dbResource.getByName(searchText);
        }
        return null;
    }

    @Override
    public SetEntity getById(String id, Mode mode) {
        if(mode == Mode.OFFLINE){
            return _dbResource.get(id);
        }
        return null;
    }
}
