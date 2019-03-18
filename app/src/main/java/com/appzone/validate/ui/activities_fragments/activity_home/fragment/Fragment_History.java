package com.appzone.validate.ui.activities_fragments.activity_home.fragment;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appzone.validate.R;
import com.appzone.validate.adapters.HistoryAdapter;
import com.appzone.validate.models.ProductModel;
import com.appzone.validate.mvvm.DatabaseMvvm;
import com.appzone.validate.ui.activities_fragments.activity_home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment_History extends Fragment {

    private RecyclerView recView;
    private RecyclerView.LayoutManager manager;
    private ProgressBar progBar;
    private HomeActivity activity;
    private DatabaseMvvm databaseMvvm;
    private TextView tv_empty_history;
    private Button btn_delete;
    private List<ProductModel> productModelList;
    private HistoryAdapter adapter;
    private ProgressDialog dialog;

    public static Fragment_History newInstance()
    {
        return new Fragment_History();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history,container,false);
        initView(view);
        setRetainInstance(true);

        return view;
    }

    private void initView(View view) {
        productModelList = new ArrayList<>();
        databaseMvvm = ViewModelProviders.of(this).get(DatabaseMvvm.class);
        activity = (HomeActivity) getActivity();
        tv_empty_history = view.findViewById(R.id.tv_empty_history);
        btn_delete = view.findViewById(R.id.btn_delete);

        progBar = view.findViewById(R.id.progBar);
        progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        recView = view.findViewById(R.id.recView);
        manager = new LinearLayoutManager(activity);
        recView.setLayoutManager(manager);
        adapter = new HistoryAdapter(activity,productModelList,this);
        recView.setAdapter(adapter);
        databaseMvvm.getAllProducts().observe(this, new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(@Nullable List<ProductModel> productModels) {
                progBar.setVisibility(View.GONE);

                if (productModels!=null&&productModels.size()>0)
                {
                    productModelList.clear();
                    productModelList.addAll(productModels);

                    adapter.notifyDataSetChanged();
                    tv_empty_history.setVisibility(View.GONE);
                    btn_delete.setVisibility(View.VISIBLE);


                }else
                    {
                        productModelList.clear();
                        adapter.notifyDataSetChanged();

                        tv_empty_history.setVisibility(View.VISIBLE);
                        btn_delete.setVisibility(View.GONE);


                    }



            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateProgressDialog();
                databaseMvvm.DeleteAll();
                dialog.dismiss();
            }
        });


    }


    private void CreateProgressDialog()
    {
        dialog = new ProgressDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setMessage(getString(R.string.deleting));
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (productModelList!=null&&productModelList.size()>0)
        {
            adapter.notifyDataSetChanged();
        }
    }


    public void setItemData(ProductModel productModel) {
        activity.DisplayFragmentDetails(productModel);
    }
}
