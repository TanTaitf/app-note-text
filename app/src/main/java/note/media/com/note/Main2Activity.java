package note.media.com.note;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.view.menu.MenuBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static note.media.com.note.MainActivity.database;
import static note.media.com.note.R.layout.activity_main2;

public class Main2Activity extends AppCompatActivity {

    private ImageView imgback;
    private TextView txttile_addnote;
    private EditText edttitle ,edtIput;
    private NotificationCompat.Builder notBuilder;
    private static final int MY_NOTIFICATION_ID = 12345;
    private static final int MY_REQUEST_CODE = 100;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Main2Activity.this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //No title bar is set for the activity
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Full screen is set for the Window
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(activity_main2);
        Anhxa();

        // id ứng dụng
        MobileAds.initialize(this, "ca-app-pub-6352050986417104~5843370467");
        //MobileAds.initialize(this, idapp);


        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                //Toast.makeText(getApplicationContext(), "Đã mở Ad", Toast.LENGTH_SHORT).show();
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }
        });

//        imgback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              // Xacnhanback();
//                Update();
//                finish();
//            }
//        });
        this.notBuilder = new NotificationCompat.Builder(this);

        // Thông báo sẽ tự động bị hủy khi người dùng click vào Panel

        this.notBuilder.setAutoCancel(false);



    }
    public void notiButtonClicked()  {

        // --------------------------
        // Chuẩn bị một thông báo
        // --------------------------

        this.notBuilder.setSmallIcon(R.drawable.icon_main);
        this.notBuilder.setTicker("This is a ticker");

        // Sét đặt thời điểm sự kiện xẩy ra.
        // Các thông báo trên Panel được sắp xếp bởi thời gian này.
        this.notBuilder.setWhen(System.currentTimeMillis()+ 10* 1000);

        String ten = edttitle.getText().toString().trim();
        String ghichu = edtIput.getText().toString().trim();
        this.notBuilder.setContentTitle(ten);
        this.notBuilder.setContentText(ghichu);

        // Tạo một Intent
        Intent intent = new Intent(this, MainActivity.class);


        // PendingIntent.getActivity(..) sẽ start mới một Activity và trả về
        // đối tượng PendingIntent.
        // Nó cũng tương đương với gọi Context.startActivity(Intent).
        PendingIntent pendingIntent = PendingIntent.getActivity(this, MY_REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);


        this.notBuilder.setContentIntent(pendingIntent);

        // Lấy ra dịch vụ thông báo (Một dịch vụ có sẵn của hệ thống).
        NotificationManager notificationService  =
                (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);

        // Xây dựng thông báo và gửi nó lên hệ thống.

        Notification notification =  notBuilder.build();
        notificationService.notify(MY_NOTIFICATION_ID, notification);

    }


    private void Update()
    {
        String ten = edttitle.getText().toString().trim();
        String ghichu = edtIput.getText().toString().trim();
        String date = GetDate();


        if (ten.length() != 0 || ghichu.length()!= 0) {
            // Insert data
            if (ten.length() == 0){
                ten = "";
            }
            if (ghichu.length() == 0){
                ghichu = "";
            }
            database.QueryData("INSERT INTO NOTE VALUES(null, '" + ten + "','" + ghichu + "','"+date+"')");
            startActivity(new Intent(Main2Activity.this, MainActivity.class));
        }
        else {
            startActivity(new Intent(Main2Activity.this, MainActivity.class));
        }
    }
    private String GetDate()
    {
        // chọn java.util
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dindanggio =new SimpleDateFormat("hh:mm:ss a");//HH:mm:ss a
        String date = "" + simpleDateFormat.format(calendar.getTime())
                +" "+ dindanggio.format(calendar.getTime());
        return date;
    }

    private void Anhxa() {
//        imgback = (ImageView) findViewById(R.id.imageViewbackaddnote);
//        txttile_addnote = (TextView)findViewById(R.id.txttitle_addnote);
        edttitle = (EditText)findViewById(R.id.edttitle);
        edtIput = (EditText)findViewById(R.id.editText_Input);
    }
    private void Xacnhanback() {
        AlertDialog.Builder xacnhan = new AlertDialog.Builder(this);
        xacnhan.setTitle("Thông tin");
        xacnhan.setIcon(R.mipmap.ic_launcher);
        xacnhan.setMessage("Bạn có muốn lưu không ?");

        // nut co
        xacnhan.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Update();
            }
        });
        // nut khong
        xacnhan.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        xacnhan.show();
    }
    @Override
    public void onBackPressed() {

        Update();
        finish();
        overridePendingTransition(R.anim.enter_left,R.anim.exit_right);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getActionBar().getCustomView().findViewById(R.menu.main,txttile_addnote);
        getMenuInflater().inflate(R.menu.main, menu);
        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Update();
            finish();
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.save) {
            Update();
            finish();
            return true;
        }
        if (id == R.id.delete) {
            Main2Activity.this.finish();
            return true;
        }
        if (id == R.id.remis) {
            notiButtonClicked();
            Update();
            finish();
            return true;
        }
        overridePendingTransition(R.anim.enter_left,R.anim.exit_right);
        return super.onOptionsItemSelected(item);
    }

}
