package com.ewe.parlae.ratestask;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RatesAdapter extends RecyclerView.Adapter<RatesAdapter.CurrencyViewHolder> {
    private static final String TAG = "RatesAdapter";

    public interface OnMoveClickListener {
        void moveOnItemClickFrom(int position, String value);
    }

    private ArrayList<CurrencyModel> items = new ArrayList<>();
    private Context mContext;
    private DataRepository mDataRepository;
    private LinearLayoutManager mLlm;
    private OnMoveClickListener mMoveListener;
    private DecimalFormat df = new DecimalFormat("#0.00");


    public RatesAdapter(Context context, OnMoveClickListener onMoveClickListener) {
        mDataRepository = DataRepository.get();
        mMoveListener = onMoveClickListener;
        this.mContext = context;
    }


    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_cell_layout, parent, false);
        return new CurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        final CurrencyModel cm = items.get(position);

        if (cm.getId()==0) holder.setIsRecyclable(false);
        holder.curr_title.setText(cm.getCurrencyCode());
        holder.curr_description.setText(cm.getDescription());
        holder.curr_image.setImageDrawable(getImageDrawableForCurrency(cm.getCurrencyCode().toLowerCase()));
        try {
            Double baseValue = mDataRepository.getBaseValue().getValue();
            if (cm.getRate() != null && cm.getId() > 0) {
                if (baseValue != 0.0) {
                    holder.curr_value.setText(df.format(baseValue * cm.getRate()));
//                    holder.curr_value.setText(cm.getCurrentValue());
                } else holder.curr_value.setText("");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        holder.curr_value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "onTextChanged: cell position is: " + cm.getId() + " and text: " + charSequence.toString());
                if (cm.getId() == 0) {
                    if (charSequence.length() > 0) {
                        setBaseValueTo(charSequence.toString());
                    } else mDataRepository.getBaseValue().setValue(0.0);
//                    items.get(position).setCurrentValue(holder.curr_value.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG, "afterTextChanged: base value je: " + mDataRepository.getBaseValue().getValue().toString() + ", a cm.id = " + cm.getId() + ", a position = " + position);
                Log.d(TAG, "afterTextChanged: edittext value je: " + editable.toString() +  ", a cm.id = " + cm.getId() + ", a position = " + position);
            }
        });
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.curr_value.clearFocus();
                mMoveListener.moveOnItemClickFrom(position, holder.curr_value.getText().toString());
            }
        });
    }

    private void setBaseValueTo(String string) {
        if (!string.equals("")) {
            mDataRepository.getBaseValue().setValue(Double.valueOf(string));
        } else {
            mDataRepository.getBaseValue().setValue(0.0);
        }
    }

    public void moveItemToStartFrom(int position, String currentValue) {
        if (position > 0) {

            // update data array
            CurrencyModel item = items.get(position);
            items.remove(position);
            item.setId(0);
            items.add(0, item);
            setBaseValueTo(currentValue);
            mDataRepository.getBaseRate().setValue(item.getCurrencyCode());
            items.get(1).setId(position);
            // notify adapter
            this.notifyItemMoved(position, 0);
            Log.d(TAG, "moveItemToStartFrom: baseValue = " + mDataRepository.getBaseValue().getValue().toString() + ", a cm.id = " + item.getId() + ", a base rate = " + mDataRepository.getBaseRate().getValue());
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: list size: " + items.size());
        return items.size();
    }

    public void setData(ArrayList<CurrencyModel> currencyModelArrayList) {
        items.clear();
        items.addAll(currencyModelArrayList);
    }

    // method to update values using DiffUtil. Not tested.
    public void setData1(ArrayList<CurrencyModel> currencyModelArrayList){
        if (items != null) {
            CurrDiffCallback postDiffCallback = new CurrDiffCallback(currencyModelArrayList, items);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(postDiffCallback);

            items.clear();
            items.addAll(currencyModelArrayList);
            diffResult.dispatchUpdatesTo(this);
            Log.d(TAG, "setData: dispatched to adapter");
        } else {
            // first initialization
            items = currencyModelArrayList;
        }
    }

    class CurrDiffCallback extends DiffUtil.Callback {
        private final ArrayList<CurrencyModel> oldList, newList;

        public CurrDiffCallback(ArrayList<CurrencyModel> oldList, ArrayList<CurrencyModel> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getCurrencyCode().equals(newList.get(newItemPosition).getCurrencyCode());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            CurrencyModel oldItem, newItem;
            oldItem = oldList.get(oldItemPosition);
            newItem = newList.get(newItemPosition);
            return oldItem.getCurrencyCode().equals(newItem.getCurrencyCode()) && oldItem.getId()==newItem.getId() && oldItem.getRate().equals(newItem.getRate());
        }
    }

    class CurrencyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView curr_image;
        private TextView curr_title, curr_description;
        public EditText curr_value;
        private ConstraintLayout item;

        public CurrencyViewHolder(@NonNull View itemView) {
            super(itemView);

            curr_image = itemView.findViewById(R.id.curr_image);
            curr_title = itemView.findViewById(R.id.title);
            curr_description = itemView.findViewById(R.id.description);
            curr_value = itemView.findViewById(R.id.amount);
            item = itemView.findViewById(R.id.cellLayout);
        }
    }

    public void loadCurrencyImage(ImageView imageview, String picName){
        Drawable picDrawable;
        try {
            int resId = mContext.getResources().getIdentifier(picName, "drawable", mContext.getPackageName());
        picDrawable = mContext.getResources().getDrawable(resId, null);
        } catch (Resources.NotFoundException e){
            e.printStackTrace();
            picDrawable = mContext.getResources().getDrawable(R.drawable.coins, null);
        }

        Glide.with(mContext)
                .asDrawable()
                .load(picDrawable)
                .apply(RequestOptions.placeholderOf(R.drawable.coins))
                .into(imageview);
    }

    private Drawable getImageDrawableForCurrency(String currency){
        Drawable picDrawable;
        try {
            int resId = mContext.getResources().getIdentifier(currency, "drawable", mContext.getPackageName());
            picDrawable = mContext.getResources().getDrawable(resId, null);
        } catch (Resources.NotFoundException e){
            e.printStackTrace();
            picDrawable = mContext.getResources().getDrawable(R.drawable.coins, null);
        }
        return picDrawable;
    }
}
