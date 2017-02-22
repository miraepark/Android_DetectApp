package com.example.samsung.detectapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class FourthActivity extends AppCompatActivity {
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fourth);

        //콤보박스(스피너 관련)
        spinner=(Spinner)findViewById(R.id.spinner);

        SpinnerAdapter sAdapter = ArrayAdapter.createFromResource(this,R.array.selectImage,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(sAdapter);
    }
    //back
    public void onClickBackButton(View v){
        Intent intent=new Intent(this, MapsActivity.class);
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

    //save next
    public void onClickSaveBtn(View v) {
        //앞에서 정보받아오기
        Intent getintent=this.getIntent();
        String pass1=getintent.getStringExtra("이름");
        String pass2=getintent.getStringExtra("구조물명");

        //뒤로 정보 넘기기
        Intent intent = new Intent(FourthActivity.this, FifthActivity.class);
        intent.putExtra("이름",pass1);
        intent.putExtra("구조물명",pass2);
        startActivity(intent);
    }

}
