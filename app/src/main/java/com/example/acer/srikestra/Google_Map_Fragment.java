package com.example.acer.srikestra;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import utils.GPSTracker;

public class Google_Map_Fragment extends Fragment implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;


    GoogleMap mgoogleMap;
    private LatLng currentLatLng;
    private LatLng routeTo;
    private int currentRouteType = 0;
    private Routing.TravelMode[] routeTypes= new Routing.TravelMode[4];
    MapView mapView;
    View view;
    private boolean mPermissionDenied = false;

    private ArrayList<Object> polylines;
    private GPSTracker gpsTracker;
    private FragmentManager supportFragmentManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_maps,null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView=(MapView) view.findViewById(R.id.mapview );

        if (mapView!=null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        gpsTracker = new GPSTracker(getContext());
        mgoogleMap=googleMap;
        mgoogleMap.setOnMyLocationButtonClickListener((GoogleMap.OnMyLocationButtonClickListener) this);
        mgoogleMap.setOnMyLocationClickListener((GoogleMap.OnMyLocationClickListener)this);
        enableMyLocation();
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(18.828304, 95.250628)).title("Pyay").snippet("I will go Pyay"));
        CameraPosition cameraPosition= CameraPosition.builder().target(new LatLng(18.828304, 95.250628)).zoom(16).bearing(0).tilt(15).build();



    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);

            //Toast.makeText( this, "Current Lat Lng" + currentLatLng, Toast.LENGTH_SHORT ).show();

        } else if (mgoogleMap != null) {
            // Access to the location has been granted to the app.
            mgoogleMap.setMyLocationEnabled(true);
            if(gpsTracker.canGetLocation() && gpsTracker.getLongitude() > 0 && gpsTracker.getLongitude() > 0) {
                currentLatLng = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
                //Toast.makeText(this, "Current Lat Lng" + currentLatLng, Toast.LENGTH_SHORT).show();


                gotoCurrentLocation();
            }



        }
    }
    public boolean onMyLocationButtonClick() {
      //  Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).

        /*Intent intent = new Intent(MyLocationDemoActivity.this, MapsActivity.class);
        startActivity(intent);*/

        routeTo = new LatLng(16.849610, 96.117740);
        getRoute(routeTypes[currentRouteType], routeTo);

        return false;
    }
    @Override
    public void onMyLocationClick(@NonNull Location location) {
      //  Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();

        //16.849610, 96.117740




    }

    private GoogleMap.OnMarkerClickListener onClickMarker = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            /*if(marker.getTag() != null && marker.getTag() instanceof Building){
                showBuilding((Building) marker.getTag());
            }*/
            return false;
        }
    };
    private void getRoute(Routing.TravelMode travelMode, LatLng toRoute) {
        Routing routing = new Routing.Builder()
                .travelMode(travelMode)
                .withListener(new RoutingListener() {
                    @Override
                    public void onRoutingFailure(RouteException e) {
                        //showLoading(false);
                        Toast.makeText(getContext(), "This routing is not available.", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onRoutingStart() {

                    }

                    @Override
                    public void onRoutingSuccess(ArrayList<Route> routes, int shortestRouteIndex) {
                        //showLoading(false);
                        mgoogleMap.clear();
                        gotoCurrentLocation();

                        mgoogleMap.addMarker(new MarkerOptions()
                                .position(routeTo) //new LatLng(building.getLat(), building.getLng())
                                .icon(bitmapDescriptorFromVector(getContext(), R.drawable.ic_nearby_maps_maker)));

                        //.icon(bitmapDescriptorFromVector(getActivity(), getMaker(building.getType())))).setTag(building);
                        /*for(Building building: buildings) {

                        }
                        if(slidingPanel.getPanelState() != SlidingUpPanelLayout.PanelState.COLLAPSED) {
                            recyclerView.smoothScrollToPosition(0);
                            slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                        }*/
                        //mMap.setOnMarkerClickListener(onClickMarker);

                        polylines = new ArrayList<>();
                        //add route(s) to the map.
                        double distance = 0.0;
                        for (int i = 0; i < routes.size(); i++) {
                            PolylineOptions polyOptions = new PolylineOptions();
                            polyOptions.color(getResources().getColor(R.color.colorAccent));
                            polyOptions.width(16);
                            polyOptions.addAll(routes.get(i).getPoints());
                            Polyline polyline = mgoogleMap.addPolyline(polyOptions);
                            polylines.add(polyline);
                            distance += routes.get(i).getDistanceValue();
                        }
                    }

                    @Override
                    public void onRoutingCancelled() {

                    }
                })
                .waypoints(currentLatLng, toRoute)
                .build();
        routing.execute();
    }

    private void gotoCurrentLocation() {
        MarkerOptions options = new MarkerOptions();
        options.icon(bitmapDescriptorFromVector(getContext(), R.drawable.ic_nearby_maps_maker));
        options.position(currentLatLng);
        mgoogleMap.addMarker(options);

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(currentLatLng)
                .zoom(15)
                .build();

        // Animate the change in camera view over 2 seconds
        mgoogleMap.animateCamera( CameraUpdateFactory.newCameraPosition(cameraPosition),100, null);
    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance( true ).show( getSupportFragmentManager(), "dialog" );
    }

    public FragmentManager getSupportFragmentManager() {
        return supportFragmentManager;
    }
}
