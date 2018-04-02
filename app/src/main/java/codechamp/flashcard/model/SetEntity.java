package codechamp.flashcard.model;

import android.content.ContentValues;
import codechamp.flashcard.database.ItemsTable;

import java.util.UUID;

public class SetEntity {
    private String id;
    private String setName;
    private String description;
    private String folderId;

    public SetEntity() { }

    public SetEntity(String setName, String description) {
        this.id = UUID.randomUUID().toString();
        this.setName = setName;
        this.description = description;
    }

    public SetEntity(String id, String setName, String description, String folderId) {
        if (id == null) {
            this.id = UUID.randomUUID().toString();
        } else {
            this.id = id;
        }
        this.setName = setName;
        this.description = description;
        this.folderId = folderId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public ContentValues toValues() {
        ContentValues values = new ContentValues();
        values.put(ItemsTable.CARD_SET_COLUMN_ID, this.id);
        values.put(ItemsTable.CARD_SET_COLUMN_NAME, this.setName);
        values.put(ItemsTable.CARD_SET_COLUMN_DESCRIPTION, this.description);
        values.put(ItemsTable.CARD_SET_COLUMN_GROUP_ID, this.folderId);
        return values;
    }

    @Override
    public String toString() {
        return "CardSet{" +
                "id='" + id + '\'' +
                ", setName='" + setName + '\'' +
                ", description='" + description + '\'' +
                ", folderId='" + folderId + '\'' +
                '}';
    }
}
