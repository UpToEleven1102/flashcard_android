package codechamp.flashcard;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.*;
import codechamp.flashcard.adapter.CardRecyclerViewAdapter;
import codechamp.flashcard.infrastructure.KeyHolder;
import codechamp.flashcard.infrastructure.Mode;
import codechamp.flashcard.infrastructure.SettingValues;
import codechamp.flashcard.model.CardEntity;
import codechamp.flashcard.model.FolderEntity;
import codechamp.flashcard.model.SetEntity;
import codechamp.flashcard.service.CardEntityService;
import codechamp.flashcard.service.IService;
import codechamp.flashcard.service.SetEntityService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddCardSetActivity extends AppCompatActivity {

    private static final String TAG = "_addCards";
    private final String _SET_ID = UUID.randomUUID().toString();

    private FolderEntity _folderEntity;
    private Dialog mDialog;
    private List<CardEntity> _cardList;
    private Mode _mode;

    private IService<CardEntity> _cardService;
    private IService<SetEntity> _setService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        _folderEntity = getIntent().getExtras().getParcelable(KeyHolder.groupKey);
//        _mode = getIntent().getExtras().get(KeyHolder.onlineModeKey)== Mode.ONLINE? Mode.ONLINE: Mode.OFFLINE;
        _mode = SettingValues.mode;
        _cardService = new CardEntityService(this);
        _setService = new SetEntityService(this);

        TextView tvFolderName = (TextView) findViewById(R.id.lb_folder);
        tvFolderName.append(" " + _folderEntity.getFolderName());

        final EditText txtCardSetName = (EditText) findViewById(R.id.card_set_name);
        final EditText txtCardSetDescription = (EditText) findViewById(R.id.card_set_description);

        Button btSaveCardSet = (Button) findViewById(R.id.btSaveCardSet);

        btSaveCardSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cardSetName = txtCardSetName.getText().toString().trim();
                String cardSetDescription = txtCardSetDescription.getText().toString().trim();
                if (cardSetName.equals("") || cardSetDescription.equals("")) {
                    Toast.makeText(AddCardSetActivity.this, getResources().getText(R.string.error_field_required), Toast.LENGTH_LONG).show();
                } else {
                    SetEntity newSet = new SetEntity(_SET_ID, cardSetName, cardSetDescription, _folderEntity.getId());
                    _setService.create(newSet,_mode);
                    for (CardEntity card:
                            _cardList) {
                        _cardService.create(card, _mode);
                    }
                    finish();
                }
            }
        });

        _cardList = new ArrayList<>();
        Button btNewCard = (Button) findViewById(R.id.btNewCard);
        btNewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewCardDialog();
            }
        });
        mDialog = new Dialog(this);

        RecyclerView cardListView = (RecyclerView)findViewById(R.id.cards_holder);
        CardRecyclerViewAdapter adapter = new CardRecyclerViewAdapter(this, _cardList);

        cardListView.setAdapter(adapter);
    }

    public void showNewCardDialog() {
        mDialog.setContentView(R.layout.dialog_add_new_card);
        mDialog.show();
        final EditText txtQuestion = mDialog.findViewById(R.id.text_question);
        final EditText txtAnswer = mDialog.findViewById(R.id.text_answer);

        Button btAddCard = mDialog.findViewById(R.id.bt_save_card);

        btAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question = txtQuestion.getText().toString().trim();
                String answer = txtAnswer.getText().toString().trim();

                if(question.equals("")||answer.equals("")) {
                    Toast.makeText(AddCardSetActivity.this, getResources().getText(R.string.error_field_required), Toast.LENGTH_SHORT).show();
                } else {
                    _cardList.add(new CardEntity(question, answer, _SET_ID));
                    mDialog.dismiss();
                }
            }
        });

        ImageButton btDismiss = mDialog.findViewById(R.id.bt_dismiss);
        btDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
    }

    public void cancel(View view) {
        finish();
    }
}
