package com.example.damian.earthquakemonitor;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

public class DetailActivity extends FragmentActivity {
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        HashMap<String, String> hashMap = (HashMap<String, String>)intent.getSerializableExtra(MainActivity.HASH_MAP);
        DetailText detailText = (DetailText) getSupportFragmentManager().findFragmentById(R.id.detail_text_fragment);
        detailText.setValues(hashMap.get(MainActivity.TAG_MAGNITUDE),
                hashMap.get(MainActivity.TAG_DATE),
                hashMap.get(MainActivity.TAG_PLACE),
                hashMap.get(MainActivity.TAG_DEPHT));

        googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMyLocationEnabled(true);

        double latitude = Double.parseDouble(hashMap.get(MainActivity.TAG_LATITUDE));
        double longitude = Double.parseDouble(hashMap.get(MainActivity.TAG_LONGITUDE));
        final LatLng Pin = new LatLng(latitude, longitude);
        Marker pin = googleMap.addMarker(new MarkerOptions()
                .position(Pin)
                .title(hashMap.get(MainActivity.TAG_PLACE))
                .snippet(hashMap.get(MainActivity.TAG_DEPHT))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        CameraUpdate center = CameraUpdateFactory.newLatLng(Pin);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);

        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);
    }

}