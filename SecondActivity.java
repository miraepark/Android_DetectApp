package com.example.samsung.detectapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import static android.content.ContentValues.TAG;

public class SecondActivity extends Activity implements View.OnClickListener{

    private static final int Camera=0;
    private static final int Album=1;
    private static final int Crop=2;

    private File dir;

    private int count;
    private String url;

    private Uri CaptureUri;
    private ImageView iv;
    private String absoultePath;
    String dirPath;

    public static final String STRSAVEPATH = Environment.
            getExternalStorageDirectory()+"/DetectData/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        count = getSharedPreferences("pref",MODE_PRIVATE).getInt("count",0);
        url = "Detect_"+count+".png";

        //폴더생성
        dir = makeDirectory(STRSAVEPATH);

        iv=(ImageView) this.findViewById(R.id.user_image);
        Button btn_agreeJoin=(Button) this.findViewById(R.id.PicBtn);

        btn_agreeJoin.setOnClickListener(this);

    }
    /**
     * 디렉토리 생성
     * @return dir
     */
    private File makeDirectory(String dir_path){
        dir = new File(dir_path);
        if (!dir.exists())
        {
            dir.mkdirs();
            Log.i( TAG , "!dir.exists" );
        }else{
            Log.i( TAG , "dir.exists" );
        }

        return dir;
    }

    //카메라 사진촬영
    public void TakePhotoAction(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        CaptureUri=Uri.fromFile(new File(dir,url));

        //원래는 intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, CaptureUri);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,CaptureUri);
        startActivityForResult(intent,Camera);
    }

    //앨범에서 이미지가져오기
    public void TakeAlbumAction(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent,Album);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode!=RESULT_OK)
            return;

        switch (requestCode){
            case Album : {
                CaptureUri=data.getData();
            }
            case Camera : {
                Intent intent=new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(CaptureUri,"image/*");

                //CROP할 이미리 200*200 크기로 저장
                intent.putExtra("outputX",350);
                intent.putExtra("outputY",450);
                intent.putExtra("aspextX",1);
                intent.putExtra("aspextY",1);
                intent.putExtra("scale",true);
                intent.putExtra("return-data",true);
                startActivityForResult(intent,Crop);
                break;
            }
            case Crop : {
                if(resultCode!=RESULT_OK){
                    return;
                }

                final Bundle extras=data.getExtras();
                String filePath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/DetectData/"+url;
                if(extras!=null){
                    Bitmap photo=extras.getParcelable("data");

                    iv.setImageBitmap(photo);
                    

                    storeCropImage(photo,filePath);
                    absoultePath=filePath;
                    break;
                }
                File f=new File(CaptureUri.getPath());
                if(f.exists()){
                    f.delete();
                }
            }
            //count 초기화
//            getSharedPreferences("pref",MODE_PRIVATE).edit().clear().commit();
        }
        getSharedPreferences("pref",MODE_PRIVATE).edit().putString("fileName",url).commit();
        getSharedPreferences("pref",MODE_PRIVATE).edit().putInt("count",count++).commit();

    }

    public void onClick(View v){
        if(v.getId()==R.id.PicBtn){
            DialogInterface.OnClickListener cameraListener=new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialog,int which){
                    TakePhotoAction();
                }
            };

            DialogInterface.OnClickListener albumListener=new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog,int which){
                    TakeAlbumAction();
                }
            };

            DialogInterface.OnClickListener cancelListener=new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog,int which){
                    dialog.dismiss();
                }
            };
            new AlertDialog.Builder(this).setTitle("업로드할 이미지 선택")
                    .setPositiveButton("사진촬영",cameraListener)
                    .setNeutralButton("앨범선택",albumListener)
                    .setNegativeButton("취소",cancelListener)
                    .show();
        }

    }

    /**
     * 수정하는 부분
      **/
    private void storeCropImage(Bitmap bitmap,String filePath){
        dirPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/DetectData";
        File directory_SmartWheel=new File(dirPath);

        if(!directory_SmartWheel.exists())
            directory_SmartWheel.mkdir();

        File copyFile=new File(filePath);
        BufferedOutputStream out=null;

        try{
            copyFile.createNewFile();
            out=new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.PNG,100,out);

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.fromFile(copyFile)));

            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //back버튼
    public void onClickBackButton(View v){
        Intent intent=new Intent(SecondActivity.this, MainActivity.class);
        startActivity(intent);
    }
    //detext버튼
    public void onClickDetectBtn(View v){
        Intent intent=new Intent(SecondActivity.this,NdkActivity.class);
        startActivity(intent);

    }
    //next버튼
    public void onClickNextBtn(View v){

        //앞에서 입력값 받아오기
        Intent getintent=this.getIntent();
        String pass1=getintent.getStringExtra("이름");
        String pass2=getintent.getStringExtra("구조물명");

        //받아온 값 포함해서 뒤로 넘기기
        Intent intent=new Intent(SecondActivity.this,MapsActivity.class);
        intent.putExtra("이름",pass1);
        intent.putExtra("구조물명",pass2);
        startActivity(intent);

    }

}
