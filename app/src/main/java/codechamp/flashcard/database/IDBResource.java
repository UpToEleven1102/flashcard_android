package codechamp.flashcard.database;

import java.util.List;

public interface IDBResource<T> {
    public T create(T object);
    public T get(String id);
    public List<T> getByName(String name);
    public List<T> getAll();
    public List<T> getAll(String st);
    public int remove(String id);
    public void beginTransaction();
    public void executeTransaction();
    public void endTransaction();
    public long getItemCount();
    public void open();
    public void close();
}
