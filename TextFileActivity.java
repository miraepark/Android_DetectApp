package com.example.samsung.detectapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class TextFileActivity extends Activity {

    public static final String TAG = "TextFileActivity";
    public static final String STRSAVEPATH = Environment.
            getExternalStorageDirectory()+"/DetectData/";
    public static final String STRSAVEPATH2 = Environment.
            getExternalStorageDirectory()+"/testfolder/";
    public static final String SAVEFILEPATH = "DetectData.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textfile);

        //폴더생성
        File dir = makeDirectory(STRSAVEPATH);
        //파일생성
        File file = makeFile(dir, (STRSAVEPATH+SAVEFILEPATH));
        //절대경로
        Log.i(TAG, ""+getAbsolutePath(dir));
        Log.i(TAG, ""+getAbsolutePath(file));

//        파일쓰기
//        String content = new String("가나다라마바사아자차카타파하");
//        writeFile(file , content.getBytes());
        //앞에서 변수값 받아오기
        String Iname=getIntent().getStringExtra("이름");
        String IDate=getIntent().getStringExtra("날짜");
        String IBname= getIntent().getStringExtra("구조물명");
        String IDamage=getIntent().getStringExtra("피해");
        String IDamageLocation=getIntent().getStringExtra("피해위치");
        String IOpinion=getIntent().getStringExtra("조사자의견");

        String fileName = getSharedPreferences("pref",MODE_PRIVATE).getString("fileName","");
        Log.d("fileName",fileName);

        String X1=getSharedPreferences("pref",MODE_PRIVATE).getString("getX1","");
        Log.d("getX1",X1);
        String Y1=getSharedPreferences("pref",MODE_PRIVATE).getString("getY1","");
        Log.d("getY1",Y1);
        String X2=getSharedPreferences("pref",MODE_PRIVATE).getString("getX2","");
        Log.d("getX2",X2);
        String Y2=getSharedPreferences("pref",MODE_PRIVATE).getString("getY2","");
        Log.d("getY2",Y2);


        String Allinput="====================\n조사자 이름 : "+Iname+"\n날짜 : "+IDate+"\n구조물 이름 : "+IBname
               +"\n균열사진 파일명 : "+fileName+"\n피해 : "+IDamage+"\n피해위치 : "+IDamageLocation+"\n조사자 의견 : "+IOpinion
               +"\n\nX1좌표 :"+X1+", Y1좌표 : "+Y1+"\nX2좌표 :"+X2+", Y2좌표 : "+Y2;

        //파일쓰기
        writeFile(file, Allinput);

        //파일읽기
//        readFile(file);

        //파일복사
        makeDirectory(STRSAVEPATH2); //복사할폴더
        copyFile(file , (STRSAVEPATH2+SAVEFILEPATH));

//        //디렉토리 내용 얻어오기
//        String[] list = getList(dir);
//        for(String s : list){
//            Log.d(TAG, s);
//        }

        //3초 후 액티비티 종료료
       Handler EndHandler=new Handler();
        EndHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finishAffinity();
            }
        },3000);    //3000ms(3sec)

    }

    /**
     * 디렉토리 생성
     * @return dir
     */
    private File makeDirectory(String dir_path){
        File dir = new File(dir_path);
        if (!dir.exists())
        {
            dir.mkdirs();
            Log.i( TAG , "!dir.exists" );
        }else{
            Log.i( TAG , "dir.exists" );
        }

        return dir;
    }

    /**파일생성
     * @param dir
     * @return file
     */
    private File makeFile(File dir , String file_path){
        File file = null;
        boolean isSuccess = false;
        if(dir.isDirectory()){
            file = new File(file_path);
            if(file!=null&&!file.exists()){
                Log.i( TAG , "!file.exists" );
                try {
                    isSuccess = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally{
                    Log.i(TAG, "파일생성 여부="+isSuccess);
                }
            }else{
                Log.i( TAG , "file.exists" );
            }
        }
        return file;
    }

    /**
     * (dir/file) 절대경로 얻어오기
     * @param file
     * @return String
     */
    private String getAbsolutePath(File file){
        return ""+file.getAbsolutePath();
    }

    /**
     디렉토리 내부 내용 보여주기
     */
    private String[] getList(File dir){
        if(dir!=null&&dir.exists())
            return dir.list();
        return null;
    }

    /**
     파일에 내용쓰기
     * @param file
     * @param file_content
     * @return
     */
    private boolean writeFile(File file , String file_content){
        boolean result;

        if(file!=null&&file.exists()&&file_content!=null){
            try {
                //FileWriter(파일명,boolean append) append 할건지 안할건지 / 안하면 새로운 파일 내용으로 갱신
                FileWriter fos = new FileWriter(STRSAVEPATH+SAVEFILEPATH,true);
                try {
                    //두줄 띄어쓰기
                    fos.write(file_content+"\n\n");
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            result = true;
        }else{
            result = false;
        }
        return result;
    }
    /**
     파일 읽어오기
     * @param file
//     */
//    private void readFile(File file){
//        int readcount=0;
//        if(file!=null&&file.exists()){
//            try {
//                FileInputStream fis = new FileInputStream(file);
//                readcount = (int)file.length();
//                byte[] buffer = new byte[readcount];
//                fis.read(buffer);
//                for(int i=0 ; i<file.length();i++){
//                    Log.d(TAG, ""+buffer[i]);
//                }
//                fis.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    /**
     파일복사
     * @param file
     * @param save_file
     * @return
     */
    private boolean copyFile(File file , String save_file){
        boolean result;
        if(file!=null&&file.exists()){
            try {
                FileInputStream fis = new FileInputStream(file);
                FileOutputStream newfos = new FileOutputStream(save_file);
                int readcount=0;
                byte[] buffer = new byte[1024];
                while((readcount = fis.read(buffer,0,1024))!= -1){
                    newfos.write(buffer,0,readcount);
                }
                newfos.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            result = true;
        }else{
            result = false;
        }
        return result;
    }

    //    /**
//     * (dir/file) 삭제하기
//     * @param file
//     */
//    private boolean deleteFile(File file){
//        boolean result;
//        if(file!=null&&file.exists()){
//            file.delete();
//            result = true;
//        }else{
//            result = false;
//        }
//        return result;
//    }
//
//    /**
//     * 파일여부체크하기
//     * @param file
//     * @return
//     */
//    private boolean isFile(File file){
//        boolean result;
//        if(file!=null&&file.exists()&&file.isFile()){
//            result=true;
//        }else{
//            result=false;
//        }
//        return result;
//    }
//
//    /**
//     디렉토리 여부 체크하기
//     * @param dir
//     * @return
//     */
//    private boolean isDirectory(File dir){
//        boolean result;
//        if(dir!=null&&dir.isDirectory()){
//            result=true;
//        }else{
//            result=false;
//        }
//        return result;
//    }
//
//    /**
//     파일존재여부 확인하기
//     * @param file
//     * @return
//     */
//    private boolean isFileExist(File file){
//        boolean result;
//        if(file!=null&&file.exists()){
//            result=true;
//        }else{
//            result=false;
//        }
//        return result;
//    }
//
//    /**
//     파일이름 바꾸기
//     */
//    private boolean reNameFile(File file , File new_name){
//        boolean result;
//        if(file!=null&&file.exists()&&file.renameTo(new_name)){
//            result=true;
//        }else{
//            result=false;
//        }
//        return result;
//    }

}








