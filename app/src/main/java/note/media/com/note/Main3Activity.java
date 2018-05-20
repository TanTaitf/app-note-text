package note.media.com.note;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main3Activity extends AppCompatActivity {

    private ImageView imgedit;
    private TextView txtxedit;
    private EditText edtedit;
    private NotificationCompat.Builder notBuilder;
    private static final int MY_NOTIFICATION_ID = 12345;
    private static final int MY_REQUEST_CODE = 100;
    int idnote = 0;
    private Note note_edit;

    Button btnok, btnhuy;
    EditText edt_Titlenote;
    TextView txt_titlenote;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main3);
        Anhxa();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intentnhan = getIntent();

        if (intentnhan != null) {
            note_edit = (Note) intentnhan.getSerializableExtra("Mang");
            txtxedit.setText(note_edit.getTen());
            edtedit.setText(note_edit.getNoidung());
            idnote = note_edit.getId();
            idnote = note_edit.getId();
        }
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

        txtxedit.setOnTouchListener(new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //do stuff here
                    ShowDialog(R.style.DialogAnimation);
                }

                Log.i("click text", "kakak");
                return false;
            }
        });



//        note = (Note) getIntent().getSerializableExtra("dulieuNote");
//        if (note != null)
//        {
//            txtxedit.setText(note.getTen());
//            edtedit.setText(note.getNoidung());
//            idnote = note.getId();
//
//        }
//        else
//        {
//            Toast.makeText(Main3Activity.this,"Không lấy được thông tin",Toast.LENGTH_SHORT).show();
//        }





//        imgedit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CapnhapNote(note_edit.getId());
//                finish();
//            }
//        });

        this.notBuilder = new NotificationCompat.Builder(this);

        // Thông báo sẽ tự động bị hủy khi người dùng click vào Panel

        this.notBuilder.setAutoCancel(false);

    }
    public void ShowDialog(int animationSource)
    {
        final Dialog dialog = new Dialog(Main3Activity.this);
        // bỏ title của dialog đặt trước khi gắn vào layout
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edittitle_dialog);
        dialog.setCanceledOnTouchOutside(false); // set hiển thị cứng cho dialog
        AnhxaDialog(dialog);
        edt_Titlenote.setText(txtxedit.getText().toString().trim());
        dialog.getWindow().getAttributes().windowAnimations = animationSource;
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int idupdate = note_edit.getId();
                String ten = edt_Titlenote.getText().toString().trim();
                String ghichu = edtedit.getText().toString().trim();
                String date = GetDate();
                if (ten.length()==0)
                {
                    Toast.makeText(Main3Activity.this,"vui lòng nhập đủ thông tin",Toast.LENGTH_SHORT).show();
                }else {

                    MainActivity.database.QueryData("UPDATE NOTE SET Ten ='"+ten+"', Noidung ='"+ghichu+"',Ngay ='"+date+"' WHERE Id = '"+idupdate+"'");
                    startActivity(new Intent(Main3Activity.this,MainActivity.class));
                }
                dialog.dismiss(); // lệnh tắt dialog
                //dialog.cancel();
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // lệnh tắt dialog
                //dialog.cancel();
            }
        });

        dialog.show();

    }
    public void AnhxaDialog(Dialog dialog)
    {
        // anh xa dialog
        txt_titlenote = (TextView) dialog.findViewById(R.id.tennote_dialog);
        edt_Titlenote  = (EditText) dialog.findViewById(R.id.edt_tennote);
        btnok = (Button)dialog.findViewById(R.id.btnok);
        btnhuy = (Button)dialog.findViewById(R.id.btnhuy);

    }
    private  void CapnhapNote(int idupdate){
        String ten = txtxedit.getText().toString().trim();
        String ghichu = edtedit.getText().toString().trim();
        String date = GetDate();
        if (ten.length()==0 && ghichu.matches(""))
        {
            Toast.makeText(Main3Activity.this,"vui lòng nhập đủ thông tin",Toast.LENGTH_SHORT).show();
        }else {

            MainActivity.database.QueryData("UPDATE NOTE SET Ten ='"+ten+"', Noidung ='"+ghichu+"',Ngay ='"+date+"' WHERE Id = '"+idupdate+"'");
            startActivity(new Intent(Main3Activity.this,MainActivity.class));
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
    @Override
    public void onBackPressed() {
        CapnhapNote(note_edit.getId());
        finish();
        overridePendingTransition(R.anim.enter_left,R.anim.exit_right);
    }

    private void Anhxa() {
//        imgedit = (ImageView)findViewById(R.id.imgedit);
        txtxedit = (TextView)findViewById(R.id.txtedit);
        edtedit = (EditText)findViewById(R.id.edt_edit);
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

        String ten = txtxedit.getText().toString().trim();
        String ghichu = edtedit.getText().toString().trim();
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
            CapnhapNote(note_edit.getId());
            finish();

        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.save) {

            CapnhapNote(note_edit.getId());
            finish();
            return true;
        }
        if (id == R.id.delete) {
            Main3Activity.this.finish();
            return true;
        }
        if (id == R.id.remis) {
            notiButtonClicked();
            CapnhapNote(note_edit.getId());
            finish();
            return true;
        }
        overridePendingTransition(R.anim.enter_left,R.anim.exit_right);
        return super.onOptionsItemSelected(item);
    }
}
