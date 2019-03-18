package com.appzone.validate.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appzone.validate.R;
import com.appzone.validate.models.ProductModel;
import com.appzone.validate.share.TimeAgo;
import com.appzone.validate.ui.activities_fragments.activity_home.fragment.Fragment_History;

import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyHolder> {

    private Context context;
    private List<ProductModel> productModelList;
    private Fragment_History fragment_history;
    private TimeAgo timeAgo;

    public HistoryAdapter(Context context, List<ProductModel> productModelList,Fragment_History fragment_history) {
        this.context = context;
        this.productModelList = productModelList;
        this.fragment_history = fragment_history;
        timeAgo = TimeAgo.newInstance();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_row,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        ProductModel productModel = productModelList.get(position);
        holder.BindData(productModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductModel productModel = productModelList.get(holder.getAdapterPosition());
                fragment_history.setItemData(productModel);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (productModelList!=null)
        {
            return productModelList.size();

        }else
            {
                return 0;
            }
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tv_name,tv_time;
        public MyHolder(View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time = itemView.findViewById(R.id.tv_time);

        }

        public void BindData(ProductModel productModel)
        {
            tv_time.setText(timeAgo.getTime(productModel.getCreated_at()));

            if (Locale.getDefault().getLanguage().equals("ar"))
            {

                tv_name.setText(productModel.getAr_name());
            }else
                {
                    tv_name.setText(productModel.getEn_name());

                }
        }
    }
}
