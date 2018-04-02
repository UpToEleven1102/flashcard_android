package codechamp.flashcard.database;

public class ItemsTable {
    public static String GROUP_TABLE_NAME = "groups";
    public static String GROUP_COLUMN_ID = "id";
    public static String GROUP_COLUMN_NAME = "groupName";
    public static String[] GROUP_TABLE_COLUMNS = {GROUP_COLUMN_ID, GROUP_COLUMN_NAME};

    public static String CARD_SET_TABLE_NAME = "sets";
    public static String CARD_SET_COLUMN_ID = "id";
    public static String CARD_SET_COLUMN_NAME = "setName";
    public static String CARD_SET_COLUMN_DESCRIPTION = "description";
    public static String CARD_SET_COLUMN_GROUP_ID = "groupId";
    public static String[] CARD_SET_TABLE_COLUMNS = {CARD_SET_COLUMN_ID, CARD_SET_COLUMN_NAME,
            CARD_SET_COLUMN_DESCRIPTION, CARD_SET_COLUMN_GROUP_ID};

    public static String CARD_TABLE_NAME = "cards";
    public static String CARD_COLUMN_ID = "id";
    public static String CARD_COLUMN_QUESTION = "question";
    public static String CARD_COLUMN_ANSWER = "answer";
    public static String CARD_COLUMN_SET_ID = "setId";
    public static String[] CARD_TABLE_COLUMNS = {CARD_COLUMN_ID, CARD_COLUMN_QUESTION, CARD_COLUMN_ANSWER, CARD_COLUMN_SET_ID};

    public static String CREATE_GROUP_TABLE = "CREATE TABLE " + GROUP_TABLE_NAME +
            "(" + GROUP_COLUMN_ID + " TEXT PRIMARY KEY, " +
            GROUP_COLUMN_NAME + " TEXT )";
    public static String REMOVE_GROUP_TABLE = "DROP " + GROUP_TABLE_NAME;

    public static String CREATE_SET_TABLE = "CREATE TABLE " + CARD_SET_TABLE_NAME +
            "(" + CARD_SET_COLUMN_ID + " TEXT PRIMARY KEY, " +
            CARD_SET_COLUMN_NAME + " TEXT, " +
            CARD_SET_COLUMN_DESCRIPTION + " TEXT, " +
            CARD_SET_COLUMN_GROUP_ID + " TEXT )";
    public static String REMOVE_CARD_SET_TABLE = "DROP " + CARD_SET_TABLE_NAME;

    public static String CREATE_CARD_TABLE = "CREATE TABLE " + CARD_TABLE_NAME +
            "(" + CARD_COLUMN_ID + " TEXT PRIMARY KEY, " +
            CARD_COLUMN_QUESTION + " TEXT, " +
            CARD_COLUMN_ANSWER + " TEXT, " +
            CARD_COLUMN_SET_ID + " TEXT)";
    public static String REMOVE_CARD_TABLE = "DROP " + CARD_TABLE_NAME;

}
