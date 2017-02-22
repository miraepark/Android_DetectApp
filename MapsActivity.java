package com.example.samsung.detectapp;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.google.android.gms.maps.*;
import android.widget.AdapterView.OnItemSelectedListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private Spinner spinner;
    private GoogleMap mMap;
    private ImageView imageview1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //맵관련
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        //iv관련
//        imageview1=(ImageView)findViewById(R.id.imageview1);
//
        //콤보박스(스피너 관련)
        spinner=(Spinner)findViewById(R.id.spinner);

        SpinnerAdapter sAdapter = ArrayAdapter.createFromResource(this,R.array.selectImage,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(sAdapter);
//
//        spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
//
//            public void onItemSelected(AdapterView parent, View v, int position, long id) {
//                    if(position==0){
//                        imageview1.setImageResource(R.drawable.image1);
//                    }
//                    else if(position==1){
//                        imageview1.setImageResource(R.drawable.image2);
//                    }
//            }
//            public void onNothingSelected(AdapterView parent, View v, int position, long id){}
//        });
    }

    //맵뷰안에 보여주는 내용, 여기는 현재위치 보여줌
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //밑에 빨간줄은 퍼미션 필요 경고로 앱실행에는 전혀 문제 없음
        if(mMap!=null){
            mMap.setMyLocationEnabled(true);
        }
    }

    //back
    public void onClickBackBtn(View v){
        Intent intent=new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    //색칠하기버튼
    public void onClickDColoringBtn(View v){
        Toast.makeText(getApplicationContext(),"색칠하기 시작버튼",Toast.LENGTH_LONG).show();
    }

    //색칠수정버튼
    public void onClickEditBtn(View v) {
        Toast.makeText(this,"색칠 지우개역할",Toast.LENGTH_SHORT).show();
    }

    //next
    public void onClickNextBtn(View v) {
        //앞에서 정보받아오기
        Intent getintent=this.getIntent();
        String pass1=getintent.getStringExtra("이름");
        String pass2=getintent.getStringExtra("구조물명");

        //뒤로 정보 같이 넘기기
        Intent intent = new Intent(MapsActivity.this, FourthActivity.class);
        intent.putExtra("이름",pass1);
        intent.putExtra("구조물명",pass2);
        startActivity(intent);
    }
}
