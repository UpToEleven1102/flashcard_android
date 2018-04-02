package codechamp.flashcard.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.*;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import codechamp.flashcard.DashboardActivity;
import codechamp.flashcard.R;
import codechamp.flashcard.infrastructure.SettingValues;
import codechamp.flashcard.model.CardEntity;
import codechamp.flashcard.model.FolderEntity;
import codechamp.flashcard.model.SetEntity;
import codechamp.flashcard.service.CardEntityService;
import codechamp.flashcard.service.SetEntityService;

import java.util.List;
import java.util.Map;

public class FolderExpandableListViewAdapter extends BaseExpandableListAdapter {
    private final String TAG = "remove_item";
    private Context _context;
    private List<FolderEntity> _folders;
    private Map<FolderEntity, List<SetEntity>> _setCollections;
    private Dialog _dialog;

    public FolderExpandableListViewAdapter(Context context, List<FolderEntity> folders,
                                           Map<FolderEntity, List<SetEntity>> setCollections) {
        _context = context;
        _folders = folders;
        _setCollections = setCollections;
        _dialog = new Dialog(context);
        _dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    @Override
    public int getGroupCount() {
        return _folders.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return _setCollections.get(_folders.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return _folders.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return _setCollections.get(_folders.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(_context);
            view = inflater.inflate(android.R.layout.simple_expandable_list_item_1, viewGroup, false);
        }

        TextView text1 = view.findViewById(android.R.id.text1);
        text1.setTextSize(17);
        text1.setText(_folders.get(i).getFolderName());
        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(_context);
            view = inflater.inflate(android.R.layout.simple_expandable_list_item_2, viewGroup, false);
        }

        TextView txtName = view.findViewById(android.R.id.text1);
        TextView txtDescription = view.findViewById(android.R.id.text2);
        final SetEntity set = _setCollections.get(_folders.get(i)).get(i1);
        txtName.setText(set.getSetName());
        txtDescription.setText(set.getDescription());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(_context, set.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final CardEntityService cardService = new CardEntityService(_context);
                final SetEntityService setService = new SetEntityService(_context);
                _dialog.setContentView(R.layout.dialog_set_options);

                Button btRemove = _dialog.findViewById(R.id.bt_remove);
                Button btEdit = _dialog.findViewById(R.id.bt_edit);
                Button btPlay = _dialog.findViewById(R.id.bt_play);

                btRemove.setOnClickListener(new View.OnClickListener() {
                    List<CardEntity> cards;
                    SetEntity setEntity;

                    @Override
                    public void onClick(View view) {

                        removeItem(view);

                        View expListView = ((Activity)_context).getWindow().getDecorView().findViewById(R.id.exp_list_view_folders);
                        Snackbar.make(expListView, R.string.item_removed, Snackbar.LENGTH_LONG)
                                .setAction(_context.getResources().getString(R.string.undo), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                undoRemoveItem();
                            }

                            private void undoRemoveItem() {
                                _setCollections.get(_folders.get(i)).add(i1,setEntity);
                                notifyDataSetChanged();
                                setService.endTransaction();
                                setService.beginTransaction();
                            }
                        }).show();
                        _dialog.dismiss();
                    }

                    private void removeItem(View view1) {
                        setEntity = _setCollections.get(_folders.get(i)).get(i1);
                        _setCollections.get(_folders.get(i)).remove(i1);
                        notifyDataSetChanged();
                        cards = cardService.getAll(setEntity.getId(), SettingValues.mode);
                        setService.beginTransaction();

                        setService.remove(setEntity.getId(), SettingValues.mode);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setService.saveTransaction();
                                setService.endTransaction();
                                setEntity = setService.getById(setEntity.getId(), SettingValues.mode);
                                if(setEntity == null) {
                                    for (CardEntity card : cards) {
                                        cardService.remove(card.getId(), SettingValues.mode);
                                    }
                                }

                                Log.i(TAG, "" + cardService.getItemsCount(SettingValues.mode));
                            }
                        }, 4000);
                    }
                });
                _dialog.show();

                return true;
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
