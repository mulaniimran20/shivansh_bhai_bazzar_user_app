package com.aarfaatechnovision.kjl.shivanshbhajibazar.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.List;
import com.aarfaatechnovision.kjl.shivanshbhajibazar.R;
import com.squareup.picasso.Picasso;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private List<String> data;
    private Context context;


    /**
     * setup Constructure
     */
    public ShopAdapter(List<String> data,Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_shop, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        Picasso.with(context).load(data.get(position)).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    /**
     * bind ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.ivCode);

        }
    }
}