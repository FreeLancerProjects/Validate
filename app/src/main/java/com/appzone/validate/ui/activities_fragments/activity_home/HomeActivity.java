package com.appzone.validate.ui.activities_fragments.activity_home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.appzone.validate.R;
import com.appzone.validate.models.ProductModel;
import com.appzone.validate.ui.activities_fragments.activity_home.fragment.Fragment_Details;
import com.appzone.validate.ui.activities_fragments.activity_home.fragment.Fragment_History;
import com.appzone.validate.ui.activities_fragments.activity_home.fragment.Fragment_Home;
import com.appzone.validate.ui.activities_fragments.activity_home.fragment.Fragment_Scan;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Fragment_Home fragment_home;
    private Fragment_History fragment_history;
    private Fragment_Scan fragment_scan;
    private Fragment_Details fragment_details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState==null)
        {
            DisplayFragmentHome();


        }
    }


    public void DisplayFragmentHome()
    {

        if (fragment_home==null)
        {
            fragment_home = Fragment_Home.newInstance();


        }
        if (fragment_home.isAdded())
        {
            fragmentManager.beginTransaction().show(fragment_home).commit();

        }else
        {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container,fragment_home,"fragment_home").addToBackStack("fragment_home").commit();

        }

        DisplayFragmentScan();



    }

    public void DisplayFragmentHistory()
    {

        if (fragment_scan!=null&&fragment_scan.isAdded())
        {
            fragmentManager.beginTransaction().hide(fragment_scan).commit();
        }

        if (fragment_history==null)
        {
            fragment_history = Fragment_History.newInstance();

        }


        if (fragment_history.isAdded())
        {
            fragmentManager.beginTransaction().show(fragment_history).commit();
        }else
        {
            fragmentManager.beginTransaction().add(R.id.fragment_history_scan_container,fragment_history,"fragment_history").addToBackStack("fragment_history").commit();
        }



    }

    public void DisplayFragmentScan()
    {

        if (fragment_history!=null&&fragment_history.isAdded())
        {
            fragmentManager.beginTransaction().hide(fragment_history).commit();

        }
        if (fragment_scan==null)
        {
            fragment_scan = Fragment_Scan.newInstance();

        }




        if (fragment_scan.isAdded())
        {
            fragmentManager.beginTransaction().show(fragment_scan).commit();
        }else
        {
            fragmentManager.beginTransaction().add(R.id.fragment_history_scan_container,fragment_scan,"fragment_scan").addToBackStack("fragment_scan").commit();
        }

        if (fragment_home!=null&&fragment_home.isAdded())
        {
            fragment_home.updateView();
        }
        fragment_scan.UpdateCameraView();

    }

    public void DisplayFragmentDetails(ProductModel productModel)
    {


        if (fragment_home!=null&&fragment_home.isAdded())
        {
            fragmentManager.beginTransaction().hide(fragment_home).commit();
        }

        fragment_details = Fragment_Details.newInstance(productModel);



        if (fragment_details.isAdded())
        {
            fragmentManager.beginTransaction().show(fragment_details).commit();
        }else
        {
            fragmentManager.beginTransaction().add(R.id.fragment_app_container,fragment_details,"fragment_details").addToBackStack("fragment_details").commit();
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragmentList)
        {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragmentList)
        {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onBackPressed() {
        Back();
    }

    public void Back() {


        if (fragment_details!=null&&fragment_details.isVisible())
        {
            fragmentManager.popBackStack();
            fragmentManager.beginTransaction().show(fragment_home).commit();
            if (fragment_scan!=null&&fragment_scan.isAdded())
            {
                fragment_scan.UpdateCameraView();

            }

        }else if (fragment_history!=null&&fragment_history.isVisible())
        {
            DisplayFragmentHome();


        }else if (fragment_home!=null&&fragment_home.isVisible())
        {
            finish();
        }else
            {
                DisplayFragmentHome();
            }
    }


}
