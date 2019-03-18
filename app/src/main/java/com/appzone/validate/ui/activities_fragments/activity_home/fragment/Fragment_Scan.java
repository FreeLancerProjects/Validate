package com.appzone.validate.ui.activities_fragments.activity_home.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.appzone.validate.R;
import com.appzone.validate.models.ProductModel;
import com.appzone.validate.mvvm.DatabaseMvvm;
import com.appzone.validate.ui.activities_fragments.activity_home.HomeActivity;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Fragment_Scan extends Fragment implements ZXingScannerView.ResultHandler{

    /*private SurfaceView surfaceView;
    private CameraSource cameraSource;
    private BarcodeDetector detector;*/
    private HomeActivity activity;
    private final int CameraPermReq = 1;
    private DatabaseMvvm databaseMvvm;
    //private SurfaceHolder holder;
    private ZXingScannerView zxView;
    boolean  flag_selected = false;
    private ProgressDialog dialog;

    public static Fragment_Scan newInstance() {
        return new Fragment_Scan();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        initView(view);
        CheckCameraPermission();
        setRetainInstance(true);
        return view;
    }



    private void CheckCameraPermission() {

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, CameraPermReq);
        } else {
            setUpCamera();
        }
    }

    private void initView(View view) {
        databaseMvvm = ViewModelProviders.of(this).get(DatabaseMvvm.class);
        activity = (HomeActivity) getActivity();
        //surfaceView = view.findViewById(R.id.surfaceView);
        zxView = view.findViewById(R.id.zxView);
        CreateProgressDialog();
        setUpCamera();


    }

    private void setUpCamera()
    {

        zxView.setResultHandler(this);
        zxView.setAutoFocus(true);




    }

   /* private void setUpCamera() {
        detector = new BarcodeDetector.Builder(activity)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(activity, detector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(640, 480).build();

        holder = surfaceView.getHolder();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Fragment_Scan.this.holder = holder;

                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                try {
                    cameraSource.start(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        detector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(final Detector.Detections<Barcode> detections) {


                if (detections.getDetectedItems().size() != 0) {

                   if (!flag_selected)
                   {
                       flag_selected = true;
                       String serialNumber = detections.getDetectedItems().valueAt(0).displayValue;
                        Vibrator vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
                        if (vibrator != null) {
                            vibrator.vibrate(100);

                        }

                        InsertItem(serialNumber);

                   }





                }

            }


        });
    }*/



    private void InsertItem(final String serial_number) {

        final ProductModel productModel = databaseMvvm.getProductBySerialNumber(serial_number);
        if (productModel==null)
        {
            databaseMvvm.getProductData(activity,serial_number);
            databaseMvvm.getProductModel()
                    .observe(Fragment_Scan.this, new Observer<ProductModel>() {
                        @Override
                        public void onChanged(@Nullable ProductModel productModel) {
                            if (productModel!=null)
                            {

                                if (productModel.getStatus()==200)
                                {
                                    new Handler(Looper.getMainLooper())
                                            .postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    dialog.dismiss();
                                                }
                                            },1);
                                    databaseMvvm.Insert(productModel);
                                    activity.DisplayFragmentDetails(productModel);


                                }else if (productModel.getStatus() == 404)
                                {
                                    new Handler(Looper.getMainLooper())
                                            .postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    dialog.dismiss();
                                                }
                                            },1);
                                    new Handler(Looper.getMainLooper())
                                            .postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    CreateAlertDialog(activity,getString(R.string.pro_not_sup));

                                                }
                                            },500);
                                }else
                                {

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            UpdateCameraView();

                                        }
                                    },3000);

                                    new Handler(Looper.getMainLooper())
                                            .postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    dialog.dismiss();
                                                }
                                            },1);
                                }

                            }
                        }
                    });
        }else
        {
            new Handler(Looper.getMainLooper())
                    .postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            activity.DisplayFragmentDetails(productModel);

                        }
                    },1);

        }



    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CameraPermReq) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setUpCamera();

            }
        }
    }

    private void CreateProgressDialog()
    {
        dialog = new ProgressDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setMessage(getString(R.string.wait));
    }

    public void CreateAlertDialog(Context context, String msg)
    {
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(true)
                .create();

        View view = LayoutInflater.from(context).inflate(R.layout.dialog,null);
        Button doneBtn = view.findViewById(R.id.doneBtn);
        TextView tv_msg = view.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateCameraView();
                dialog.dismiss();
            }
        });

        dialog.getWindow().getAttributes().windowAnimations=R.style.dialog_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_window_bg);
        dialog.setView(view);
        dialog.show();
    }

    @Override
    public void onStop() {
        super.onStop();
        zxView.stopCamera();
    }



    @Override
    public void onResume() {
        super.onResume();
        zxView.setResultHandler(this);
        zxView.startCamera();
        UpdateCameraView();
    }

    @Override
    public void handleResult(final Result result) {

        new MyAsyncTask().execute(result.getText());
    }

    private class MyAsyncTask extends AsyncTask<String,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            dialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {
            InsertItem(strings[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();

        }
    }

    public void UpdateCameraView()
    {
        if (zxView!=null)
        {
            zxView.resumeCameraPreview(this);

        }

    }
}
