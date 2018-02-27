package com.example.jogus.scroll;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.jogus.scroll.R.id.day;
import static com.example.jogus.scroll.R.layout.item;


public class MainActivity extends AppCompatActivity {

    Activity act = this;
    GridView gridView;
    RelativeLayout mainlayout;

    String pakage_name;
    String app_name;
    String file = "apptext.txt";
    String backfile = "backtext.txt";
    Drawable dra;
    PackageManager pack;


    Random rd=new Random();


    public static final int ran[]= {
            R.drawable.back0, R.drawable.back1, R.drawable.back2,
            R.drawable.back3, R.drawable.back4, R.drawable.back5,
            R.drawable.back6, R.drawable.back7, R.drawable.back8, R.drawable.back9,
            R.drawable.back10, R.drawable.back11, R.drawable.back12, R.drawable.back13,
            R.drawable.back14
    };


     gridAdapter adapter;
   // ArrayList<String> items;
  //  ArrayAdapter adapter2;
     int cnt=0;

//이미지 배열 선언
    ArrayList<Drawable> picArr = new ArrayList<Drawable>();
//텍스트 배열 선언
    ArrayList<String> textArr = new ArrayList<String>();


    TextView tv_battVolume_value = null;
    TextView time = null;
    TextView date = null;
    TextView ampm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    // deleteFile(file);


        Drawable de= getResources().getDrawable(R.drawable.delete);
        Drawable news=getResources().getDrawable(R.drawable.news);
        Drawable health=getResources().getDrawable(R.drawable.health);
        Drawable phone = getResources().getDrawable(R.drawable.phone);
        Drawable internet = getResources().getDrawable(R.drawable.internet);
        Drawable camera = getResources().getDrawable(R.drawable.camera);
        Drawable alarm = getResources().getDrawable(R.drawable.alarm);
        Drawable kakao = getResources().getDrawable(R.drawable.kakao);
        Drawable mms = getResources().getDrawable(R.drawable.mms);
        Drawable contacts = getResources().getDrawable(R.drawable.contacts);
        Drawable picture = getResources().getDrawable(R.drawable.picture);
        Drawable setting = getResources().getDrawable(R.drawable.setting);
        Drawable backchange = getResources().getDrawable(R.drawable.backchange);

        adapter=new gridAdapter();
        gridView = (GridView) findViewById(R.id.gridView1);
        gridView.setAdapter(adapter);
        mainlayout=(RelativeLayout)findViewById(R.id.main);


        //adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        //gridView.setAdapter(adapter2);


        tv_battVolume_value = (TextView) findViewById(R.id.battery);
        time = (TextView) findViewById(R.id.watch) ;
        date = (TextView) findViewById(day);
        ampm = (TextView) findViewById(R.id.ampm);

        picArr.add(phone);
        textArr.add("전화");
        picArr.add(internet);
        textArr.add("인터넷");
        picArr.add(kakao);
        textArr.add("카카오톡");
        picArr.add(contacts);
        textArr.add("연락처");
        picArr.add(picture);
        textArr.add("사진");
        picArr.add(camera);
        textArr.add("카메라");
        picArr.add(mms);
        textArr.add("문자");
        picArr.add(alarm);
        textArr.add("알람");


        Intent newint=getIntent();
        pakage_name=newint.getStringExtra("PAKAGE");
        pack = getPackageManager();


        if(pakage_name!=null) {
            try {
                FileOutputStream fos = openFileOutput(file,MODE_APPEND);
                PrintWriter out = new PrintWriter(fos);
                out.println(pakage_name);
                out.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        try {
            StringBuffer data1 = new StringBuffer();
            FileInputStream fis1 = openFileInput(backfile);//파일명
            BufferedReader buffer1 = new BufferedReader
                    (new InputStreamReader(fis1));
            String str1 = buffer1.readLine();
            int backint = Integer.parseInt(str1);
            mainlayout.setBackgroundResource(backint);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            StringBuffer data = new StringBuffer();
            FileInputStream fis = openFileInput(file);//파일명
            BufferedReader buffer = new BufferedReader
                    (new InputStreamReader(fis));
            String str=buffer.readLine();
            if(str!=null) {
                // 파일에서 한줄을 읽어옴
                try {
                    dra =pack.getApplicationIcon(str);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                try {
                    app_name=(String) getPackageManager().getApplicationLabel(
                            getPackageManager().getApplicationInfo(str,
                                    PackageManager.GET_UNINSTALLED_PACKAGES));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                picArr.add(dra);
                textArr.add(app_name);

            }
            while (str != null) {
                data.append(str + "\n");
                str = buffer.readLine();
                if(str!=null && str!="\r\n" && str!= ""&& str!=" ") {

                    try {
                        dra =pack.getApplicationIcon(str);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }

                    try {
                        app_name=(String) getPackageManager().getApplicationLabel(
                                getPackageManager().getApplicationInfo(str,
                                        PackageManager.GET_UNINSTALLED_PACKAGES));
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    picArr.add(dra);
                    textArr.add(app_name);


                }

            }
            buffer.close();
            fis.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




        picArr.add(news);
        textArr.add("뉴스");
        picArr.add(health);
        textArr.add("건강");
        picArr.add(setting);
        textArr.add("설정");
        picArr.add(backchange);
        textArr.add("");
        picArr.add(de);
        textArr.add("");

        //adapter.notifyDataSetChanged();

    }

    public class gridAdapter extends BaseAdapter {
        LayoutInflater inflater;
        public gridAdapter() {
            inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
// TODO Auto-generated method stub
            return picArr.size();    //그리드뷰에 출력할 목록 수
        }

        @Override

        public Object getItem(int position) {
// TODO Auto-generated method stub
            return picArr.get(position);    //아이템을 호출할 때 사용하는 메소드

        }
        @Override
        public long getItemId(int position) {
// TODO Auto-generated method stub
            return position;    //아이템의 아이디를 구할 때 사용하는 메소드
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final int pos = position;
// TODO Auto-generated method stub

            if (convertView == null)
                convertView = inflater.inflate(item, parent, false);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView1);
            TextView textView = (TextView) convertView.findViewById(R.id.textView1);
            ImageView xview = (ImageView) convertView.findViewById(R.id.exo);


            imageView.setImageDrawable(picArr.get(position));
            textView.setText(textArr.get(position));
            if(cnt==1)
            {
                xview.setVisibility(View.VISIBLE);
            }
            else
            {
                xview.setVisibility(View.INVISIBLE);
            }
          //  adapter.notifyDataSetChanged();


            xview.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    if(cnt==1)
                    {

                        int checked=position;
                        int  count = adapter.getCount();

                            if (checked > 7 && checked!=(count-1) && checked!=(count-2) && checked!=(count-3) && checked!=(count-4)
                                    && checked!=(count-5)) {
                                // 아이템 삭제
                                textArr.remove(checked);
                                picArr.remove(checked);

                                adapter.notifyDataSetChanged();
                            }
                        String dummy = "";
                        if(checked==(count-1) || checked<=7 || checked==(count-2) || checked==(count-3) || checked==(count-4)
                                || checked==(count-5))
                        {
                            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();     //닫기
                                }
                            });
                            alert.setMessage("기본어플이라 지울 수 없습니다");
                            alert.show();
                            cnt=0;
                            adapter.notifyDataSetChanged();

                        }
                        if(checked > 7 && checked!=(count-1) && checked!=(count-2) && checked!=(count-3) && checked!=(count-4)
                                && checked!=(count-5)) {
                            try {
                                FileInputStream fis = openFileInput(file);//파일명
                                BufferedReader br = new BufferedReader
                                        (new InputStreamReader(fis));
                                //1. 삭제하고자 하는 position 이전까지는 이동하며 dummy에 저장
                                String line;
                                for (int i = 8; i < checked; i++) {//4는 기본어플개수
                                    line = br.readLine(); //읽으며 이동
                                    Log.d("mstag", (i+1) + " 데이터 = " + line);
                                    dummy += (line + "\r\n");
                                }
                                //2. 삭제하고자 하는 데이터는 건너뛰기
                                String delData = br.readLine();
                                Log.d("mstag", "삭제되는 데이터 = " + delData);
                                //3. 삭제하고자 하는 position 이후부터 dummy에 저장
                                while ((line = br.readLine()) != null) {
                                    Log.d("mstag", "추가데이터 = " + line);
                                    dummy += (line + "\r\n");
                                }
                                deleteFile(file);
                                file.replaceAll(dummy,file);
                                FileOutputStream fos = openFileOutput(file, MODE_APPEND);
                                PrintWriter out = new PrintWriter(fos);
                                out.print(dummy);
                                out.close();
                                br.close();
                                gridView.clearChoices();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        cnt=0;
                      //  adapter.notifyDataSetChanged();
                    }
                }

            });
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
// TODO Auto-generated method stub

                    if(cnt==0) {
                        //  int deletecount=adapter.getCount()-1;
                        // int settingcnt=adapter.getCount()-2;
                        int app_cnt=position+1;
                        int ccnt=9;
                        try {
                            StringBuffer data = new StringBuffer();
                            FileInputStream fis = openFileInput(file);//파일명
                            BufferedReader buffer = new BufferedReader
                                    (new InputStreamReader(fis));
                            String str=buffer.readLine();
                            if(str!=null && app_cnt==9) {
                                // 파일에서 한줄을 읽어옴
                               // Intent newin1= new Intent(Intent.ACTION_VIEW, Uri.parse("tel:"));
                                Intent newint = getPackageManager().getLaunchIntentForPackage(str);
                                startActivity(newint);
                            }
                            else  {
                                while (str != null ) {
                                    data.append(str + "\n");
                                    ++ccnt;
                                    str = buffer.readLine();
                                    if(ccnt==app_cnt)
                                    {
                                        break;
                                    }
                                }
                                Intent newint = getPackageManager().getLaunchIntentForPackage(str);
                                startActivity(newint);
                            }
                            buffer.close();
                            fis.close();


                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }catch (IOException e) {
                            e.printStackTrace();
                        }


                        if (pos==adapter.getCount()-1)
                        {
                            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MainActivity.this);
                            alert_confirm.setTitle("추가/제거")
                                    .setMessage("아이콘을 추가하겠습니까 제거하겠습니까?").setCancelable(false).setPositiveButton("제거",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            cnt=1;
                                            adapter.notifyDataSetChanged();


                                        }




                                    }).setNegativeButton("추가", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent int1= new Intent();

                                    ComponentName com = new ComponentName(
                                            "com.example.jogus.scroll",
                                            "com.example.jogus.scroll.AppInfoActivity"
                                    );
                                    int1.setComponent(com);
                                    startActivity(int1);

                                }
                            });
                            AlertDialog alert = alert_confirm.create();
                            alert.show();
                        }
                        if(pos==0)
                        {
                            Intent phint= new Intent(Intent.ACTION_VIEW, Uri.parse("tel:"));
                            startActivity(phint);
                        }
                        if(pos==1)
                        {
                            Intent iin = new Intent(Intent.ACTION_VIEW,Uri.parse("http://m.naver.com"));
                            startActivity(iin);
                            //Intent intint32 = getPackageManager().getLaunchIntentForPackage("com.sec.android.app.sbrowser");
                         //   startActivity(intint32);
                        }
                        if(pos==2)
                        {
                            Intent kakaoint = getPackageManager().getLaunchIntentForPackage("com.kakao.talk");
                            startActivity(kakaoint);
                        }
                        if(pos==3)
                        {
                            Intent intint5 = getPackageManager().getLaunchIntentForPackage("com.android.contacts");
                            startActivity(intint5);
                        }
                        if(pos==4)
                        {
                            Intent intint6 = getPackageManager().getLaunchIntentForPackage("com.sec.android.gallery3d");
                            startActivity(intint6);
                        }
                        if(pos==5)
                        {
                            Intent intint7 = getPackageManager().getLaunchIntentForPackage("com.sec.android.app.camera");
                            startActivity(intint7);
                        }
                        if(pos==6)
                        {
                            Intent intint = getPackageManager().getLaunchIntentForPackage("com.android.mms");
                            startActivity(intint);
                        }
                        if(pos==7)
                        {
                            Intent intint = getPackageManager().getLaunchIntentForPackage("com.sec.android.app.clockpackage");
                            startActivity(intint);
                        }
                        if (pos==adapter.getCount()-2)
                        {
                            int a=rd.nextInt(14)+1;
                            int b=ran[a];
                            mainlayout.setBackgroundResource(b);
                            deleteFile(backfile);
                            try {
                            FileOutputStream fos = openFileOutput(backfile,MODE_APPEND);
                            PrintWriter out = new PrintWriter(fos);
                            out.print(b);
                            out.close();

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        }
                        if(pos==adapter.getCount()-3)
                        {
                            Intent setting = getPackageManager().getLaunchIntentForPackage("com.android.settings");
                            startActivity(setting);

                        }
                        if(pos==adapter.getCount()-4)
                        {
                            Intent hintent = new Intent();
                            hintent.setAction(Intent.ACTION_VIEW);
                            hintent.setData(Uri.parse("http://hi.nhis.or.kr/m/main.do"));
                            startActivity(hintent);

                        }
                        if(pos==adapter.getCount()-5)
                        {
                            Intent newsintent = new Intent();
                            newsintent.setAction(Intent.ACTION_VIEW);
                            newsintent.setData(Uri.parse("http://m.news.naver.com"));
                            startActivity(newsintent);

                        }

                    }
                    if(cnt==1)
                    {

                        int checked=position;
                       int  count = adapter.getCount();

                        if (count > 0) {
                            // 현재 선택된 아이템의 position 획득.


                            if (checked > 7 && checked!=(count-1) && checked!=(count-2) && checked!=(count-3) && checked!=(count-4)
                                    && checked!=(count-5)) {
                                // 아이템 삭제
                                textArr.remove(checked);
                                picArr.remove(checked);
                                // listview 선택 초기화.
                                // listview 갱신.
                                adapter.notifyDataSetChanged();
                            }
                        }
                        String dummy = "";

                        if(checked==(count-1) || checked<=7 || checked==(count-2) || checked==(count-3) || checked==(count-4)
                                || checked==(count-5))
                        {
                            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();     //닫기
                                }
                            });
                            alert.setMessage("기본 어플이라 지울 수 없습니다");
                            alert.show();
                            cnt=0;

                            adapter.notifyDataSetChanged();
                        }

                         if(checked > 7 && checked!=(count-1) && checked!=(count-2) && checked!=(count-3) && checked!=(count-4)
                                 && checked!=(count-5)) {
                            try {
                                FileInputStream fis = openFileInput(file);//파일명
                                BufferedReader br = new BufferedReader
                                        (new InputStreamReader(fis));
                                //1. 삭제하고자 하는 position 이전까지는 이동하며 dummy에 저장
                                String line;
                                for (int i = 8; i < checked; i++) {//4는 기본어플 개수
                                    line = br.readLine(); //읽으며 이동
                                    Log.d("mstag", (i+1) + " 데이터 = " + line);
                                    dummy += (line + "\r\n");
                                }
                                //2. 삭제하고자 하는 데이터는 건너뛰기
                                String delData = br.readLine();
                                Log.d("mstag", "삭제되는 데이터 = " + delData);
                                //3. 삭제하고자 하는 position 이후부터 dummy에 저장
                                while ((line = br.readLine()) != null) {
                                    Log.d("mstag", "추가데이터 = " + line);
                                    dummy += (line + "\r\n");
                                }
                                deleteFile(file);
                                file.replaceAll(dummy,file);
                                FileOutputStream fos = openFileOutput(file, MODE_APPEND);
                                PrintWriter out = new PrintWriter(fos);
                                out.print(dummy);
                                out.close();
                                br.close();
                                gridView.clearChoices();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        cnt=0;
                       // adapter.notifyDataSetChanged();
                    }


//이미지를 터치했을때 동작하는 곳
                }


            });

            return convertView;
        }
    }


    //시계, 배터리 실시간 측정
    private BroadcastReceiver receiver = new BroadcastReceiver(){
        public void onReceive(Context context, Intent intent){


            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float batteryPct = level / (float)scale;
            int volumePct = (int)(batteryPct * 100);
            tv_battVolume_value.setText(" "+volumePct+"%");


            int hour, min;
            int year, month, day;

            final Calendar c = Calendar.getInstance();

            hour = c.get(Calendar.HOUR_OF_DAY);
            if(hour >12) {
                hour = hour - 12;
                ampm.setText("PM");
            }
            else
                ampm.setText("AM");

            min = c.get(Calendar.MINUTE);
           // sec = c.get(Calendar.SECOND);
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH)+1;
            day = c.get(Calendar.DATE);

            if(hour>12) {
                if (hour - 12 < 10) {
                    if (min < 10)
                        time.setText("0" + hour + ":0" + min);
                    else
                        time.setText("0" + hour + ":" + min);
                }
                else
                {
                    if(min <10)
                        time.setText(hour+":0"+min);
                    else
                        time.setText(hour+":"+min);
                }
            }
            else
            {
                if (hour < 10) {
                    if (min < 10)
                        time.setText("0" +hour + ":0" + min);
                    else
                        time.setText("0" +hour + ":" + min);
                }
                else {
                    if (min < 10)
                        time.setText(hour + ":0" + min);
                    else
                        time.setText(hour + ":" + min);
                }
            }



            date.setText(year+"."+month+"."+day);
            TimerTask task = new TimerTask(){
                public void run() {
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            Timer mTimer = new Timer();
            mTimer.schedule(task, 1000, 1000);

        }
    };

    protected void onResume() {
        super.onResume();
        TimerTask task = new TimerTask(){
            public void run() {
                try {
                    registerReceiver(receiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Timer mTimer = new Timer();
        mTimer.schedule(task, 1000, 1000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }







}


