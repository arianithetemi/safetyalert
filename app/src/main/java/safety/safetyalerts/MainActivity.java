package safety.safetyalerts;

import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ncapdevi.fragnav.FragNavController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import safety.safetyalerts.fragments.AlertsFragment;
import safety.safetyalerts.fragments.BlankFragment;
import safety.safetyalerts.fragments.DataChartsFragment;
import safety.safetyalerts.fragments.HomeFragment;
import safety.safetyalerts.fragments.NearPlacesFragment;
import safety.safetyalerts.fragments.NewsFragment;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback , BlankFragment.OnFragmentInteractionListener, HomeFragment.OnFragmentInteractionListener , DataChartsFragment.OnFragmentInteractionListener , NearPlacesFragment.OnFragmentInteractionListener, AlertsFragment.OnFragmentInteractionListener ,NewsFragment.OnFragmentInteractionListener {

    private static final String TAG = MainActivity.class.getSimpleName();
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
        fragments.add(NearPlacesFragment.newInstance("",""));
        fragments.add(AlertsFragment.newInstance("",""));
        fragments.add(NewsFragment.newInstance("",""));

        fragNavController = new FragNavController(getSupportFragmentManager(), R.id.content, fragments);
        fragNavController.switchTab(FragNavController.TAB1);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch(tabId){
                    case R.id.tab_home:
                        fragNavController.switchTab(fragNavController.TAB1);
                        break;
                    case R.id.tab_charts:
                        fragNavController.switchTab(FragNavController.TAB2);
                        break;
                    case R.id.tab_near:
                        fragNavController.switchTab(FragNavController.TAB3);
                        break;
                    case R.id.tab_al:
                        fragNavController.switchTab(FragNavController.TAB4);
                        break;
                    case R.id.tab_news:
                        fragNavController.switchTab(FragNavController.TAB5);
                        break;
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


        mMap.addMarker(new MarkerOptions().position(location1).title("Melbourne").icon(BitmapDescriptorFactory.fromResource(R.drawable.circleabig)));
        mMap.addMarker(new MarkerOptions().position(location2).title("Your location").icon(BitmapDescriptorFactory.fromResource(R.drawable.circleasmall)));
        mMap.addMarker(new MarkerOptions().position(location3).title("Your location").icon(BitmapDescriptorFactory.fromResource(R.drawable.circlea60)));
        mMap.addMarker(new MarkerOptions().position(location4).title("Your location").icon(BitmapDescriptorFactory.fromResource(R.drawable.circleadangersmall)));
        // Add a marker in Sydney and move the camera
        SmartLocation.with(getApplicationContext()).location().start(new OnLocationUpdatedListener() {
            @Override
            public void onLocationUpdated(Location location) {
                LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(sydney).title("Your location").icon(BitmapDescriptorFactory.fromResource(R.drawable.circleasmall)));
                CameraUpdate locationn = CameraUpdateFactory.newLatLngZoom(
                        sydney, 10);
                mMap.animateCamera(locationn);
            }
        });
    }

}
