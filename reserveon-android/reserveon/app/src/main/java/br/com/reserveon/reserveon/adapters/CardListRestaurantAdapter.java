package br.com.reserveon.reserveon.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.reserveon.reserveon.R;
import br.com.reserveon.reserveon.models.Institute;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bruno on 25/02/2016.
 */
public class CardListRestaurantAdapter extends RecyclerView.Adapter<CardListRestaurantAdapter.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Institute> mInstitutes;

    public CardListRestaurantAdapter(List<Institute> institutes, Context context) {
        mInstitutes = institutes;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.card_list_restaurant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textViewName.setText(mInstitutes.get(position).getName());
        //holder.textViewExpedient.setText(mInstitutes.get(position).getExpedient());

        Glide.with(holder.itemView.getContext())
                .load(mInstitutes.get(position).getImage())
                .into(holder.imageViewImage);
    }

    @Override
    public int getItemCount() {
        return mInstitutes.size();
    }

    public void setInstitutes(List<Institute> institutes) {
        mInstitutes = institutes;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.card_list_restaurant_textview_name)
        TextView textViewName;
        /*@Bind(R.id.card_list_restaurant_textview_expedient)
        TextView textViewExpedient;*/
        @Bind(R.id.card_list_restaurant_imageview_image)
        ImageView imageViewImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
