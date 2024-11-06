package com.example.forest_app.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import com.example.forest_app.R;
import com.example.forest_app.api.ApiManager;
import com.example.forest_app.form.Hospital;
import com.example.forest_app.form.HospitalListResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private ApiManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // apiManager setting
        apiManager = new ApiManager();

        // Google Map 준비
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // FusedLocationProviderClient 초기화
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // 위치 업데이트 요청 설정
        LocationRequest locationRequest = LocationRequest.create();
        //locationRequest.setInterval(10000); // 10초마다 업데이트
        //locationRequest.setFastestInterval(5000); // 5초 최소 간격
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    if (location != null && mMap != null) {
                        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                        //mMap.addMarker(new MarkerOptions().position(currentLocation).title("현재 위치"));
                    }
                }
            }
        };

        // 권한 확인 및 위치 업데이트 요청
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);

        getHospitalList();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // 권한이 있을 경우 위치 표시
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        fetchLocationFromServer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    private void fetchLocationFromServer() {
        double latitude = 37.8665;
        double longitude = 127.7445;

        String locationInfo = "전화번호 : 010-1234-1234" +
                "영업시간 : 09:00 ~ 18:00";
        addMarkerOnMap(latitude, longitude,locationInfo);
    }


    private void addMarkerOnMap(double latitude, double longitude, String locationInfo){
        if(mMap != null) {
            LatLng location = new LatLng(latitude, longitude);
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(location)
                    .title("한사랑 재활센터")   // 마커의 제목
                    .snippet(locationInfo);

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15));
        }
    }

    private void getHospitalList(){
        Call<HospitalListResponse> call = apiManager.getApiService().getHospitalList();
        call.enqueue(new Callback<HospitalListResponse>() {
            @Override
            public void onResponse(Call<HospitalListResponse> call, Response<HospitalListResponse> response) {
                if(response.isSuccessful()){
                    List<Hospital> list = response.body().getItems();
                    for(Hospital h : list){
                        double latitude = h.getA();
                        double longitude = h.getB();
                        String info = h.getInfo();
                        addMarkerOnMap(latitude, longitude, info);
                    }
                }
                else{
                    Log.e("getHospitalList", "http code: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<HospitalListResponse> call, Throwable t) {
                Log.e("getHospitalList", "network error: "+t.getLocalizedMessage());
            }
        });
    }
}