package com.sebangsa.adnanto.cobarealm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sebangsa.adnanto.cobarealm.R;
import com.sebangsa.adnanto.cobarealm.model.ArticleModel;

import java.util.ArrayList;

/**
 * Created by adnanto on 9/6/16.
 */
public class AdapterArticle extends RecyclerView.Adapter<AdapterArticle.ViewHolder> {
    private final OnItemClickListener onItemClickListener;
    private ArrayList<ArticleModel> articleModelArrayList;

    public AdapterArticle(ArrayList<ArticleModel> articleModelArrayList,
                          OnItemClickListener onItemClickListener) {
        this.articleModelArrayList = articleModelArrayList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.click(articleModelArrayList.get(position), onItemClickListener);
        holder.tvId.setText(String.valueOf(articleModelArrayList.get(position).getId()));
        holder.tvTitle.setText(articleModelArrayList.get(position).getTitle());
        holder.tvDescription.setText(articleModelArrayList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return articleModelArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvId;
        protected TextView tvTitle;
        protected TextView tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            tvId = (TextView) itemView.findViewById(R.id.tv_id);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
        }

        public void click(final ArticleModel articleModel,
                          final OnItemClickListener onItemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(articleModel);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onClick(ArticleModel articleModel);
    }
}
