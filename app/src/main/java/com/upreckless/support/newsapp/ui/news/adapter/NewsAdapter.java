package com.upreckless.support.newsapp.ui.news.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.upreckless.support.newsapp.R;
import com.upreckless.support.newsapp.ui.news.model.ItemView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by @mistreckless on 10.05.2017. !
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private NewsAdapterListener listener;
    private List<ItemView> items;

    public NewsAdapter(NewsAdapterListener listener) {
        this.listener=listener;
        items=new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemView item=items.get(position);
        holder.txtTitle.setText(item.getTitle());
        holder.txtDate.setText(item.getDate());
       // holder.txtSimpleDesc.setText(item.getSimpleDesc());
        RxView.clicks(holder.layoutItem)
                .debounce(200, TimeUnit.MILLISECONDS)
                .subscribe(v->listener.itemClicked(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<ItemView> items){
        this.items=items;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txt_title)
        TextView txtTitle;
        @Bind(R.id.txt_date)
        TextView txtDate;
        @Bind(R.id.txt_desc)
        TextView txtSimpleDesc;
        @Bind(R.id.layout_item)
        LinearLayout layoutItem;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
