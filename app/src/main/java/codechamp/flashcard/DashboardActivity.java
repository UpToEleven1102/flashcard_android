package codechamp.flashcard;

import android.app.Dialog;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import codechamp.flashcard.adapter.FolderExpandableListViewAdapter;
import codechamp.flashcard.infrastructure.KeyHolder;
import codechamp.flashcard.infrastructure.Mode;
import codechamp.flashcard.model.CardEntity;
import codechamp.flashcard.model.FolderEntity;
import codechamp.flashcard.model.SetEntity;
import codechamp.flashcard.service.CardEntityService;
import codechamp.flashcard.service.FolderService;
import codechamp.flashcard.service.IService;
import codechamp.flashcard.service.SetEntityService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = "_dashboard";
    private Dialog mDialog;
    public Mode _mode;
    private IService<FolderEntity> _groupService;
    private IService<CardEntity> _cardService;
    private IService<SetEntity> _setService;

    private List<FolderEntity> _folderEntities;
    private List<CardEntity> _cardEntities;
    private List<SetEntity> _setEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        _mode = getIntent().getExtras().getBoolean(KeyHolder.onlineModeKey)? Mode.ONLINE: Mode.OFFLINE;

        mDialog = new Dialog(this);
        _groupService = new FolderService(this);
        _cardService = new CardEntityService(this);
        _setService = new SetEntityService(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.global, menu);
        return true;
    }


    public void loadExpandableListView(){
        _folderEntities = _groupService.getAll(_mode);

        Map<FolderEntity, List<SetEntity>> setCollections = new HashMap<>();
        for (FolderEntity folder: _folderEntities) {
            setCollections.put(folder, _setService.getAll(folder.getId(), _mode));
        }

        FolderExpandableListViewAdapter adapter = new FolderExpandableListViewAdapter(this, _folderEntities, setCollections);
        ExpandableListView expListFolder = (ExpandableListView) findViewById(R.id.exp_list_view_folders);

        expListFolder.setAdapter(adapter);
    }

    public void showDialog(){
        final Context _this = this;
        mDialog.setContentView(R.layout.dialog_add_new_set);
        ImageButton ibtDismiss = mDialog.findViewById(R.id.ibt_dismiss);
        ibtDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });

        final ViewGroup spinnerHolder = mDialog.findViewById(R.id.spinner_holder);
        final ViewGroup editTextHolder = mDialog.findViewById(R.id.edittext_holder);

        final ToggleButton tbtNewFolder = mDialog.findViewById(R.id.bt_toggle_new_folder);
        tbtNewFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tbtNewFolder.getText().toString().equals("ON")) {
                    editTextHolder.setVisibility(0);
                    spinnerHolder.setVisibility(8);
                } else {
                    editTextHolder.setVisibility(8);
                    spinnerHolder.setVisibility(0);
                }
            }
        });

        final List<String> groupCards = new ArrayList<>();
        for (FolderEntity group :
                _folderEntities) {
            groupCards.add(group.getFolderName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mDialog.getContext(),
                android.R.layout.simple_dropdown_item_1line, groupCards);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spinnerFolder = mDialog.findViewById(R.id.spinner_folder);
        spinnerFolder.setAdapter(adapter);

        final EditText txtNewFolder = mDialog.findViewById(R.id.text_folder);

        Button btAddCard = mDialog.findViewById(R.id.btSaveCardSet);
        btAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String folderName = null;
                if(tbtNewFolder.getText().toString().equals("ON")){
                    folderName = txtNewFolder.getText().toString().trim();
                    if (folderName.length() == 0){
                        Toast.makeText(_this, "Folder's name can't be empty", Toast.LENGTH_LONG).show();
                        return;
                    }
                } else {
                    folderName = spinnerFolder.getSelectedItem().toString().trim();
                }

                FolderEntity folderEntity = null;
                if(groupCards.contains(folderName)){
                    for (FolderEntity group :
                            _folderEntities) {
                        if(group.getFolderName().equals(folderName)){
                            folderEntity = group;
                            break;
                        }
                    }
                } else {
                    folderEntity = _groupService.create(new FolderEntity(folderName), _mode);
                    Toast.makeText(_this, "Added new Folder", Toast.LENGTH_LONG).show();
                }
                Intent intentNewCard = new Intent(_this, AddCardSetActivity.class);
                intentNewCard.putExtra(KeyHolder.groupKey, folderEntity);
                intentNewCard.putExtra(KeyHolder.onlineModeKey, _mode == Mode.ONLINE);
                startActivity(intentNewCard);
            }
        });

        mDialog.show();

    }

    @Override
    protected void onPause() {
        super.onPause();
        _groupService.closeConnection(_mode);
        _setService.closeConnection(_mode);
        _cardService.closeConnection(_mode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDialog.dismiss();
        _groupService.openConnection(_mode);
        _setService.openConnection(_mode);
        _cardService.openConnection(_mode);
        loadExpandableListView();
        loadAutoCompleteTextView();
    }

    public void loadAutoCompleteTextView() {
        List<String> sets = new ArrayList<>();
        for (SetEntity set :_setService.getAll(_mode)) {
            if(!sets.contains(set.getSetName().trim()))
            sets.add(set.getSetName().trim());
        }

        AutoCompleteTextView txtSearch = (AutoCompleteTextView) findViewById(R.id.txt_search);
        txtSearch.setAdapter(new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, sets));
    }

    public void filter(View view) {
        AutoCompleteTextView txtSearch = (AutoCompleteTextView) findViewById(R.id.txt_search);

        String searchText = txtSearch.getText().toString().trim();
        List<SetEntity> sets = _setService.getItemsByName(searchText, _mode);

        List<String> folderIds = new ArrayList<>();
        for (SetEntity set : sets) {
            if(!folderIds.contains(set.getFolderId())){
                folderIds.add(set.getFolderId());
            }
        }
        List<FolderEntity> folderEntities = new ArrayList<>();

        for (String folderId : folderIds) {
            folderEntities.add(_groupService.getById(folderId, _mode));
        }



        Map<FolderEntity, List<SetEntity>> setCollections = new HashMap<>();
        for (FolderEntity folder: folderEntities) {
            List<SetEntity> setEntities = new ArrayList<>();
            for (SetEntity entity : sets) {
                if(entity.getFolderId().equals(folder.getId()))
                    setEntities.add(entity);
            }
            setCollections.put(folder, setEntities);
        }

        FolderExpandableListViewAdapter adapter = new FolderExpandableListViewAdapter(this, folderEntities, setCollections);
        ExpandableListView expListFolder = (ExpandableListView) findViewById(R.id.exp_list_view_folders);

        expListFolder.setAdapter(adapter);
    }
}
