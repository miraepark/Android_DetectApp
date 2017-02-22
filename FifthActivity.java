package com.example.samsung.detectapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.DateFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.*;
import java.util.Date;

public class FifthActivity extends AppCompatActivity {

    public static Activity fifthActivity;
    String currentDateTimeString= DateFormat.getDateInstance(DateFormat.FULL).format(new Date());
    TextView NameView,DateView,BNameView;
    EditText Damage, DamageLocation,etc;
    ImageView iv;
    String getname,getBname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fifth);

        //날짜 자동입력
        DateView=(TextView)findViewById(R.id.AutoDate);
        DateView.setText("  "+currentDateTimeString);

        //조사자명, 구조물명 자동입력
        NameView=(TextView)findViewById(R.id.AutoName);
        BNameView=(TextView)findViewById(R.id.AutoBridgeName);
        //앞에서 받아온 정보 나타내기
        getname=getIntent().getStringExtra("이름");
        getBname= getIntent().getStringExtra("구조물명");
        NameView.setText(getname);
        BNameView.setText(getBname);

        Damage=(EditText)findViewById(R.id.Damage);
        DamageLocation=(EditText)findViewById(R.id.DamageLocation);
        iv=(ImageView)findViewById(R.id.iv);

//        Bitmap secondimage= MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
//        iv.setImageBitmap(secondimage);
        etc=(EditText)findViewById(R.id.etc);

    }

    //back
    public void onClickBackButton(View v){

        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(FifthActivity.this);
        alert_confirm.setMessage("뒤로가시면 입력하신 정보가 지워집니다. 정말 이전 페이지로 가시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(FifthActivity.this, FourthActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 'No'
                        return;
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();

    }

    //save 버튼
    public void onClickSaveBtn(View v) {
        //입력값 저장하기
        try {
            //Damage, DamageLocation,etc;
            EditText Damage = (EditText) findViewById(R.id.Damage);
            String InputDamage = Damage.getText().toString();

            EditText DamgeLocation = (EditText) findViewById(R.id.DamageLocation);
            String InputDamageLocation= DamgeLocation.getText().toString();

            EditText etc=(EditText)findViewById(R.id.etc);
            String InputOpinion=etc.getText().toString();

            Intent intent=new Intent(FifthActivity.this,TextFileActivity.class);
            intent.putExtra("이름",getname);
            intent.putExtra("날짜",currentDateTimeString);
            intent.putExtra("구조물명",getBname);
            intent.putExtra("피해",InputDamage);
            intent.putExtra("피해위치",InputDamageLocation);
            intent.putExtra("조사자의견", InputOpinion);

            Toast.makeText(FifthActivity.this,"입력값을 저장했습니다.",Toast.LENGTH_SHORT).show();
            startActivity(intent);


        }catch (Exception e){
            Toast.makeText(this,"입력값을 받지 못했습니다. 앱을 종료 후 다시 시작해주세요.",Toast.LENGTH_SHORT).show();
            finish();
        }


    }
    //share - id가 Report를 뽑아야함
    public void onClickShareBtn(View v){

        View container;
        container=getWindow().getDecorView();
        container.buildDrawingCache();
        Bitmap captureView=container.getDrawingCache();
        String address= Environment.getExternalStorageDirectory().getAbsolutePath()+"/capture.jpeg";
        FileOutputStream fos;

        try{
            fos=new FileOutputStream(address);
            //100은 여기서 퀄리티
            captureView.compress(Bitmap.CompressFormat.JPEG,100,fos);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
              Uri uri=Uri.fromFile(new File(address));

        Intent shareIntent=new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM,uri);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent,"공유"));

        //공유버튼까지 생성하고 나타나는데 캡쳐본이 저장이 안됨

    }
}
