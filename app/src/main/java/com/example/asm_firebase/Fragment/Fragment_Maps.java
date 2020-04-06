package com.example.asm_firebase.Fragment;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.asm_firebase.Adapter.Adapter_TabCourse;
import com.example.asm_firebase.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Maps extends Fragment implements OnMapReadyCallback {
    private View view;
    private static final float DEFAULT_ZOOM = 16f;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private ImageView imgGps;
    private FusedLocationProviderClient mFusedLocationClient;

    public Fragment_Maps() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_maps, container, false);
        initView();
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng FPoly = new LatLng(10.8538, 106.6284);
        mMap.addMarker(new MarkerOptions().position(FPoly).title("Marker in FPoly"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(FPoly, DEFAULT_ZOOM));
        mMap.setMyLocationEnabled(true);

    }
    private void initView() {
        mapFragment =  (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        AutocompleteSupportFragment fragmentAutoComplete =
                (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        mapFragment.getMapAsync(this);
        Places.initialize(getActivity(), getString(R.string.maps_key));
        PlacesClient placesClient = Places.createClient(getActivity());
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        imgGps = (ImageView) view.findViewById(R.id.img_Gps);

        imgGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceLocation();
            }
        });

        fragmentAutoComplete.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME));
        fragmentAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                if (place.getLatLng() != null) {
                    moveCamera(place.getLatLng(), DEFAULT_ZOOM);
                }
            }
            @Override
            public void onError(@NonNull Status status) {

            }
        });

    }
    private void getDeviceLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        try {
            Task<Location> location = mFusedLocationClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        Location currentLocation = task.getResult();
                        moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                DEFAULT_ZOOM);
                    }
                }
            });

        } catch (Exception e) {
        }
    }
    private void moveCamera(LatLng latLng, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        MarkerOptions options = new MarkerOptions().position(latLng);
        mMap.addMarker(options);
    }
}
