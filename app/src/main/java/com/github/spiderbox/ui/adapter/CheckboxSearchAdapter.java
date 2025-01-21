package com.github.spiderbox.ui.adapter;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.github.spiderbox.R;
import com.github.spiderbox.bean.SourceBean;
import com.github.spiderbox.util.LOG;
import com.github.spiderbox.util.SearchHelper;
import com.github.spiderbox.util.HawkConfig;
import org.jetbrains.annotations.NotNull;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CheckboxSearchAdapter extends ListAdapter<SourceBean, CheckboxSearchAdapter.ViewHolder> {

    public CheckboxSearchAdapter(DiffUtil.ItemCallback<SourceBean> diffCallback) {
        super(diffCallback);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_checkbox_search, parent, false));
    }

    private void setCheckedSource(HashMap<String, String> checkedSources) {
        mCheckedSources = checkedSources;
    }

    private ArrayList<SourceBean> data = new ArrayList<>();
    public HashMap<String, String> mCheckedSources = new HashMap<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<SourceBean> newData, HashMap<String, String> checkedSources) {
        data.clear();
        data.addAll(newData);
        setCheckedSource(checkedSources);
        notifyDataSetChanged();
    }

    public void setMCheckedSources() {
        SearchHelper.putCheckedSources(mCheckedSources,data.size()==mCheckedSources.size());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        SourceBean sourceBean = data.get(pos);
        holder.oneSearchSource.setText(sourceBean.getName());
        holder.oneSearchSource.setOnCheckedChangeListener(null);
        if (mCheckedSources != null) {
            String value = mCheckedSources.get(sourceBean.getKey());
            boolean isChecked = value != null && value.equals("1");
            holder.oneSearchSource.setChecked(isChecked);
        }
        /*if (mCheckedSources != null) {
            holder.oneSearchSource.setChecked(mCheckedSources.containsKey(sourceBean.getKey()));
        }*/
        holder.oneSearchSource.setTag(sourceBean);
        holder.oneSearchSource.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckedSources.put(sourceBean.getKey(), "1");
                } else {
                    mCheckedSources.put(sourceBean.getKey(), "0");
                }
                notifyItemChanged(pos);
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox oneSearchSource;

        public ViewHolder(View view) {
            super(view);
            oneSearchSource = (CheckBox) view.findViewById(R.id.oneSearchSource);
        }
    }

}
