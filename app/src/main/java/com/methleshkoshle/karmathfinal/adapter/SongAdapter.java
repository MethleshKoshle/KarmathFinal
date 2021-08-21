package com.methleshkoshle.karmathfinal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.methleshkoshle.karmathfinal.R;
import com.methleshkoshle.karmathfinal.model.ContentCard;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ExampleSongViewHolder> {
    private ArrayList<ContentCard> mSongList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onCopyClick(int position);
        void onShareClick(int position);
        void onAddFavoriteClick(int position);
        void onRemoveFavoriteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleSongViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mContentView;
        public CheckBox mAddFavorite;
        public ImageButton mShare;
        public ImageButton mCopyContent;

        public ExampleSongViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.contentIcon);
            mContentView = itemView.findViewById(R.id.SongView);
            mShare = itemView.findViewById(R.id.shareContent);
            mCopyContent = itemView.findViewById(R.id.copyContent);
            mAddFavorite = itemView.findViewById(R.id.addToFavorite);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            mCopyContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onCopyClick(position);
                        }
                    }
                }
            });
            mShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onShareClick(position);
                        }
                    }
                }
            });
            mAddFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        boolean isChecked = mAddFavorite.isChecked();
                        if (position != RecyclerView.NO_POSITION) {
                            if(isChecked)
                                listener.onAddFavoriteClick(position);
                            else
                                listener.onRemoveFavoriteClick(position);
                        }
                    }
                }
            });
        }
    }
    public SongAdapter(ArrayList<ContentCard> exampleList) {
        mSongList = exampleList;
    }
    @Override
    public ExampleSongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_song, parent, false);
        ExampleSongViewHolder evh = new ExampleSongViewHolder(v, mListener);
        return evh;
    }
    @Override
    public void onBindViewHolder(ExampleSongViewHolder holder, int position) {
        ContentCard currentItem = mSongList.get(position);
        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mContentView.setText(currentItem.getContent());
        holder.mAddFavorite.setChecked(currentItem.getState());
    }
    @Override
    public int getItemCount() {
        return mSongList.size();
    }
}