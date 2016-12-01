package com.example.chenlij.myapplication3.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.picassopalette.PicassoPalette;
import com.squareup.picasso.Picasso;

import moe.feng.oechan.R;
import moe.feng.oechan.model.DetailsResult;
import moe.feng.oechan.ui.callback.OnItemClickListener;

public class GridRecyclerViewAdapter extends RecyclerView.Adapter<GridRecyclerViewAdapter.ItemHolder> {

	@Override
	public GridRecyclerViewAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return null;
	}

	@Override
	public void onBindViewHolder(GridRecyclerViewAdapter.ItemHolder holder, int position) {

	}

	@Override
	public int getItemCount() {
		return 0;
	}

	class ItemHolder extends RecyclerView.ViewHolder {

		TextView title, text;
		ImageView image;

		ItemHolder(View itemView) {
			super(itemView);

			image = (ImageView) itemView.findViewById(R.id.image);
			title = (TextView) itemView.findViewById(R.id.title);
			text = (TextView) itemView.findViewById(R.id.text);

			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (onItemClickListener != null) {
						DetailsResult.Episode item = data.get(getAdapterPosition());
						onItemClickListener.onItemClick(ItemHolder.this, getAdapterPosition(), item);
					}
				}
			});
		}

	}
}
