package codechamp.flashcard.model;

import android.content.ContentValues;
import codechamp.flashcard.database.ItemsTable;

import java.util.UUID;

public class CardEntity {
    private String id;
    private String question;
    private String answer;
    private String setId;

    public CardEntity() {
    }

    public CardEntity(String id, String question, String answer, String setId) {
        if(id == null){
            this.id = UUID.randomUUID().toString();
        } else {
            this.id = id;
        }
        this.question = question;
        this.answer = answer;
        this.setId = setId;
    }

    public CardEntity(String question, String answer) {
        this.id = UUID.randomUUID().toString();
        this.question = question;
        this.answer = answer;
    }

    public CardEntity(String question, String answer, String setId) {
        this.id = UUID.randomUUID().toString();
        this.question = question;
        this.answer = answer;
        this.setId = setId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSetId() {
        return setId;
    }

    public void setSetId(String setId) {
        this.setId = setId;
    }

    public ContentValues toValues() {
        ContentValues values = new ContentValues();
        values.put(ItemsTable.CARD_COLUMN_ID, id);
        values.put(ItemsTable.CARD_COLUMN_QUESTION, question);
        values.put(ItemsTable.CARD_COLUMN_ANSWER, answer);
        values.put(ItemsTable.CARD_COLUMN_SET_ID, setId);
        return values;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id='" + id + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", setId='" + setId + '\'' +
                '}';
    }
}
