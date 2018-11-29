package com.test.ben.hyperproject;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.robert.hypergaragesale.R;

import java.util.ArrayList;






    public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> implements Filterable {
    private ArrayList<BrowsePosts> mDataset;
    private ArrayList<BrowsePosts> mFilteredList;

    public  Uri uri;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTitle;
        public TextView mPrice;
        public ImageView mImage;



        public ViewHolder(View view) {
            super(view);
            mTitle = (TextView) itemView.findViewById(R.id.titleView);
            mPrice = (TextView) itemView.findViewById(R.id.priceView);
            mImage=(ImageView) itemView.findViewById(R.id.thumbnail);



        }


    }




    public PostsAdapter(ArrayList<BrowsePosts> myDataset) {
        mDataset = myDataset;

        mFilteredList = mDataset;
    }


    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_text_view, parent, false);


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mTitle.setText(mFilteredList.get(position).mTitle);
        holder.mPrice.setText(mFilteredList.get(position).mPrice);
        uri=Uri.parse(mFilteredList.get(position).mImage);
        holder.mImage.setImageURI(uri);
    }


    @Override
    public int getItemCount() {

            return mFilteredList.size();

    }


    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<BrowsePosts> filteredList = new ArrayList<>();
                String charString = constraint.toString().toLowerCase();
                if (charString.isEmpty()) {

                    mFilteredList = mDataset;
                }
                else
                {

                    for( BrowsePosts post: mDataset){
                        if(post.getmTitle().toLowerCase().contains(charString))
                            filteredList.add(post);
                    }
                    mFilteredList=filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilteredList=(ArrayList<BrowsePosts>) results.values;

                notifyDataSetChanged();
                notifyItemRangeChanged(0, mFilteredList.size());

            }
        };
    }



}