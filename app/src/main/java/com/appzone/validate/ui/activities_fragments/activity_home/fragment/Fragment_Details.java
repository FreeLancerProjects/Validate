package com.appzone.validate.ui.activities_fragments.activity_home.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appzone.validate.R;
import com.appzone.validate.models.ProductModel;
import com.appzone.validate.mvvm.DatabaseMvvm;
import com.appzone.validate.tags.Tags;
import com.appzone.validate.ui.activities_fragments.activity_home.HomeActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Fragment_Details extends Fragment {

    private static final String TAG = "DATA";
    private HomeActivity activity;
    private ImageView arrow,image;
    private ConstraintLayout co_back;
    private TextView tv_name,tv_date,tv_quality,tv_price;
    private ProductModel productModel;
    private DatabaseMvvm databaseMvvm;
    public static Fragment_Details newInstance(ProductModel productModel)
    {
        Fragment_Details fragmentDetails = new Fragment_Details();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG,productModel);
        fragmentDetails.setArguments(bundle);
        return fragmentDetails;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        databaseMvvm = ViewModelProviders.of(this).get(DatabaseMvvm.class);

        activity = (HomeActivity) getActivity();
        arrow = view.findViewById(R.id.arrow);

        if (Locale.getDefault().getLanguage().equals("ar"))
        {
            arrow.setImageResource(R.drawable.white_right_arrow);
        }else
        {
            arrow.setImageResource(R.drawable.white_left_arrow);

        }
        co_back = view.findViewById(R.id.co_back);
        image = view.findViewById(R.id.image);
        tv_name = view.findViewById(R.id.tv_name);
        tv_date = view.findViewById(R.id.tv_date);
        tv_quality = view.findViewById(R.id.tv_quality);
        tv_price = view.findViewById(R.id.tv_price);

        co_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.Back();
            }
        });
        Bundle bundle = getArguments();
        if (bundle!=null)
        {
            productModel = (ProductModel) bundle.getSerializable(TAG);
            updateUI(productModel);
        }

    }

    private void updateUI(final ProductModel productModel) {
        if (productModel.getImage()!=null&&productModel.getImage().length>0)
        {
            Bitmap bitmap = BitmapFactory.decodeByteArray(productModel.getImage(),0,productModel.getImage().length);
            image.setImageBitmap(bitmap);
        }else
            {

                Target target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        image.setImageBitmap(bitmap);
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG,90,outputStream);
                        byte [] bytes = outputStream.toByteArray();
                        productModel.setImage(bytes);
                        databaseMvvm.UpdateProduct(productModel);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                };
                Picasso.with(activity).load(Uri.parse(Tags.imageUrl+productModel.getImagePathEndPoint())).into(target);
            }


        tv_price.setText(productModel.getPrice()+" "+getString(R.string.sr));
        if (Locale.getDefault().getLanguage().equals("ar"))
        {
            tv_name.setText(productModel.getAr_name());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd",Locale.getDefault());
            String date = dateFormat.format(new Date(productModel.getProduction_date()));
            tv_date.setText(date);


        }else
            {
                tv_name.setText(productModel.getEn_name());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
                String date = dateFormat.format(new Date(productModel.getProduction_date()));
                tv_date.setText(date);

            }

            tv_quality.setText(R.string.trade);
    }
}
