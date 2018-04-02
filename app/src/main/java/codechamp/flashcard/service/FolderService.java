package codechamp.flashcard.service;

import android.content.Context;
import codechamp.flashcard.database.DBFolderResource;
import codechamp.flashcard.infrastructure.Mode;
import codechamp.flashcard.model.FolderEntity;

import java.util.List;

public class FolderService implements IService<FolderEntity>{
    public DBFolderResource _folderResource;

    public FolderService(Context context){
        this._folderResource = new DBFolderResource(context);
    };

    public FolderEntity create(FolderEntity folderEntity, Mode mode){
        if(mode == Mode.OFFLINE)
            return _folderResource.create(folderEntity);
        else
            return null;
    }

    public List<FolderEntity> getAll(Mode mode) {
        if(mode == Mode.OFFLINE)
            return _folderResource.getAll();
        else
            return null;
    }

    @Override
    public List<FolderEntity> getAll(String itemId, Mode mode) {
        return null;
    }

    public long getItemsCount(Mode mode) {
        if(mode == Mode.OFFLINE)
            return _folderResource.getItemCount();
        else
            return 0;
    }

    public void openConnection(Mode mode) {
        if(mode == Mode.OFFLINE) {
            _folderResource.open();
        }
    }

    public void closeConnection(Mode mode) {
        if(mode == Mode.OFFLINE) {
            _folderResource.close();
        }
    }

    @Override
    public int remove(String itemId, Mode mode) {
        if(mode == Mode.OFFLINE){
            return _folderResource.remove(itemId);
        }
        return 0;
    }

    @Override
    public void beginTransaction() {
        _folderResource.beginTransaction();
    }

    @Override
    public void saveTransaction() {
        _folderResource.executeTransaction();
    }

    @Override
    public void endTransaction() {
        _folderResource.endTransaction();
    }

    @Override
    public List<FolderEntity> getItemsByName(String searchText, Mode mode) {
        return null;
    }

    @Override
    public FolderEntity getById(String id, Mode mode) {
        if(mode == Mode.OFFLINE){
            return _folderResource.get(id);
        }
        return null;
    }
}
