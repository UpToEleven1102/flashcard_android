package codechamp.flashcard.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import codechamp.flashcard.database.ItemsTable;
import java.util.UUID;

public class FolderEntity implements Parcelable {
    private String id;
    private String folderName;

    public FolderEntity() {
    }

    public FolderEntity(String folderName) {
        this.id = UUID.randomUUID().toString();
        this.folderName = folderName;
    }

    public FolderEntity(String id, String folderName) {
        if (id == null) {
            this.id = UUID.randomUUID().toString();
        } else {
            this.id = id;
        }
        this.folderName = folderName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public ContentValues toValues() {
        ContentValues values = new ContentValues();
        values.put(ItemsTable.GROUP_COLUMN_ID, this.id);
        values.put(ItemsTable.GROUP_COLUMN_NAME, this.folderName);
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FolderEntity)) return false;

        FolderEntity that = (FolderEntity) o;

        if (!id.equals(that.id)) return false;
        return folderName.equals(that.folderName);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + folderName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id='" + id + '\'' +
                ", folderName='" + folderName + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.folderName);
    }

    protected FolderEntity(Parcel in) {
        this.id = in.readString();
        this.folderName = in.readString();
    }

    public static final Parcelable.Creator<FolderEntity> CREATOR = new Parcelable.Creator<FolderEntity>() {
        @Override
        public FolderEntity createFromParcel(Parcel source) {
            return new FolderEntity(source);
        }

        @Override
        public FolderEntity[] newArray(int size) {
            return new FolderEntity[size];
        }
    };
}
