package codechamp.flashcard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import codechamp.flashcard.R;
import codechamp.flashcard.model.CardEntity;

import java.util.List;

public class CardRecyclerViewAdapter extends RecyclerView.Adapter<CardRecyclerViewAdapter.ViewHolder> {
    private Context _context;
    private List<CardEntity> _cardList;

    public CardRecyclerViewAdapter(Context context, List<CardEntity> cardList) {
        this._context = context;
        this._cardList = cardList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(_context);
        View itemView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardEntity card = _cardList.get(position);
        holder.textView1.setText(card.getQuestion());
        holder.textView2.setText(card.getAnswer());
    }

    @Override
    public int getItemCount() {
        return _cardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView1;
        public TextView textView2;

        public ViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(android.R.id.text1);
            textView2 = itemView.findViewById(android.R.id.text2);
        }
    }
}
