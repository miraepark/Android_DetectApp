package com.example.samsung.detectapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapRegionDecoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.util.Date;

import javax.crypto.SealedObject;

public class MainActivity extends AppCompatActivity {

    public static Activity mainActivity;
    //날짜 문자열로 얻기
    String currentDateTimeString= DateFormat.getDateInstance(DateFormat.FULL).format(new Date());
    String InputNValue;
    String InputBValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //오늘 날짜 받아오기
        TextView DateView=(TextView)findViewById(R.id.DateView);
        DateView.setText("  "+currentDateTimeString);

    }

    //edit
    public void onClickEditBtn(View v){
        //edittext 초기화
        EditText IName = (EditText) findViewById(R.id.IName);
        IName.setText(null);

        EditText BridgeName = (EditText) findViewById(R.id.BridgeName);
        BridgeName.setText(null);
        Toast.makeText(MainActivity.this,"내용이 초기화 되었습니다.",Toast.LENGTH_SHORT).show();
    }

    //check the current location
    public void onClickCLocateBtn(View v){
        //지도앱불러 확인하기
        Intent intent = new Intent(Intent.ACTION_VIEW);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "위치가 확인되지 않으면 현재위치를 클릭해서 확인하세요.",Toast.LENGTH_LONG).show();
    }

    //save and next
    public void onClickSaveBtn(View v){
        //입력값 저장하기
        try {
            EditText IName = (EditText) findViewById(R.id.IName);
            InputNValue = IName.getText().toString();

            EditText BridgeName = (EditText) findViewById(R.id.BridgeName);
            InputBValue = BridgeName.getText().toString();

            Intent intentV=new Intent(MainActivity.this,SecondActivity.class);
            intentV.putExtra("이름",InputNValue);
            intentV.putExtra("구조물명",InputBValue);
            Toast.makeText(MainActivity.this,"입력값을 저장했습니다.",Toast.LENGTH_SHORT).show();
            startActivity(intentV);

        }catch (Exception e){
            Toast.makeText(this,"입력값을 받지 못했습니다. 앱을 종료 후 다시 시작해주세요.",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
