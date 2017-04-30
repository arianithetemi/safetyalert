package safety.safetyalerts;

import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ncapdevi.fragnav.FragNavController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import safety.safetyalerts.fragments.BlankFragment;
import safety.safetyalerts.fragments.DataChartsFragment;
import safety.safetyalerts.fragments.HomeFragment;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback , BlankFragment.OnFragmentInteractionListener, HomeFragment.OnFragmentInteractionListener , DataChartsFragment.OnFragmentInteractionListener {

    public FragNavController fragNavController;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapi);;
        mapFragment.getMapAsync(MainActivity.this);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(BlankFragment.newInstance("",""));
        fragments.add(DataChartsFragment.newInstance("",""));

        fragNavController = new FragNavController(getSupportFragmentManager(), R.id.content, fragments);
        fragNavController.switchTab(FragNavController.TAB1);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_home) {
                    fragNavController.switchTab(fragNavController.TAB1);
                } else if (tabId == R.id.tab_charts) {
                    fragNavController.switchTab(FragNavController.TAB2);
                }
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng location1 = new LatLng(42.6629,21.1655);
        LatLng location2 = new LatLng(42.7629,21.1655);
        LatLng location3 = new LatLng(42.8629,21.1655);
        LatLng location4 = new LatLng(42.9629,21.1655);
        mMap.addMarker(new MarkerOptions().position(location1).title("Your location"));
        mMap.addMarker(new MarkerOptions().position(location2).title("Your location"));
        mMap.addMarker(new MarkerOptions().position(location3).title("Your location"));
        mMap.addMarker(new MarkerOptions().position(location4).title("Your location"));
        // Add a marker in Sydney and move the camera
        SmartLocation.with(getApplicationContext()).location().start(new OnLocationUpdatedListener() {
            @Override
            public void onLocationUpdated(Location location) {
                LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(sydney).title("Your location"));
                CameraUpdate locationn = CameraUpdateFactory.newLatLngZoom(
                        sydney, 10);
                mMap.animateCamera(locationn);
            }
        });
    }

}