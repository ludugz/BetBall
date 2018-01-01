package com.example.ntan.betball;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.ActionBar;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import static com.example.ntan.betball.R.id.spinnerHauVe1;
import static com.example.ntan.betball.R.id.spinnerThuMon;


public class EditDetailedData extends AppCompatActivity {
    private Spinner spinnerThuMon;
    DetailedDataPagerAdapter mDetailedDataPagerAdapter;
    ViewPager mViewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_detailed_data);
        //ActionBar control
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        //Set up Spinner and ArrayAdapter
        final View myViewSpinner = getLayoutInflater().inflate(R.layout.player_fragment, null);
        spinnerThuMon = (Spinner) myViewSpinner.findViewById(R.id.spinnerThuMon);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.player_array, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerThuMon.setAdapter(arrayAdapter);
        spinnerThuMon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if (item !=null){
                    Toast.makeText(EditDetailedData.this,
                            "OnItemSelectedListener : " + item,
                            Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(EditDetailedData.this,"Selected",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Link Adapter to ViewPager
        mDetailedDataPagerAdapter = new DetailedDataPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDetailedDataPagerAdapter);
    }
    public static class DetailedDataPagerAdapter extends FragmentPagerAdapter {

        public DetailedDataPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new PlayerFragment();
                case 1:
                    return new WeatherFragment();
                default:
                    // The other sections of the app are dummy placeholders.
                    Fragment fragment = new PlayerFragment();
                    return fragment;
            }
        }
        @Override
        public int getCount() {
            return 2;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0){
                return "Đội hình";
            }
            else {
                return "Thời tiết";
            }

        }
    }
    public static class PlayerFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.player_fragment, container, false);
            return rootView;
        }
    }
    public static class WeatherFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.weather_fragment, container, false);
            return rootView;
        }
    }
}

