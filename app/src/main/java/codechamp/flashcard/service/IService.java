package codechamp.flashcard.service;

import codechamp.flashcard.infrastructure.Mode;
import codechamp.flashcard.model.SetEntity;

import java.util.List;

public interface IService<T> {
    public T create(T item,Mode mode);
    public List<T> getAll(Mode mode);
    public List<T> getAll(String itemId, Mode mode);
    public int remove(String itemId, Mode mode);
    public long getItemsCount(Mode mode);
    public void beginTransaction();
    public void saveTransaction();
    public void endTransaction();
    public void openConnection(Mode mode);
    public void closeConnection(Mode mode);
    public List<T> getItemsByName(String searchText, Mode mode);
    public T getById(String id, Mode mode);
}
