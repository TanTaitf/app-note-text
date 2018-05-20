package note.media.com.note;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView imgAddNote;
    public static Database database;
    private ArrayList<Note> ArrayNote;
    public static ListView listViewNote;
    public AdapterNote adapterNote;
    private  TextView title, info;
    Button btnok;
    private AdView mAdView;
    Note note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        database = new Database(this,"quanly.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS NOTE( Id INTEGER PRIMARY KEY AUTOINCREMENT, Ten VARCHAR(200),Noidung VARCHAR(200), Ngay VARCHAR(200))");

        // Insert data
//        database.QueryData("INSERT INTO NOTE VALUES(null, 'This is note', 'Chuẩn bị khuyến mãi lấy tiền mua sách về đọc', '12/10/2017')");
//        database.QueryData("INSERT INTO NOTE VALUES(null, 'Monday 12', 'Đi ăn trưa với bạn','21/3/2016')");
//        database.QueryData("INSERT INTO NOTE VALUES(null, 'Mua giày Hunter', 'Quá đẹp so với dự tính.....','1/6/2017')");


        //select data
        ArrayNote = new ArrayList<>();
        adapterNote = new AdapterNote(MainActivity.this,R.layout.dong_list_note, ArrayNote);

        loadListNote();
        // id ứng dụng
        MobileAds.initialize(this, "ca-app-pub-6352050986417104~5843370467");
        //MobileAds.initialize(this, idapp);


        mAdView = (AdView)this.findViewById(R.id.addadView);
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


        // bắt sự kiện thêm note
        imgAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_right,R.anim.exit_left);

            }
        });
//        listViewNote.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(),"Cập nhập dữ liệu ", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
//        listViewNote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"Cập nhập dữ liệu ", Toast.LENGTH_SHORT).show();
//            }
//        });
//        listViewNote.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(),"Cập nhập dữ liệu ", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        listViewNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(getApplicationContext(),"chon vi tri list",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),Main3Activity.class);
                intent.putExtra("Mang",ArrayNote.get(position));

                Animation animAlpha = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.enter);
                //view.startAnimation(animAlpha);
                startActivity(intent);
//                finish();
                overridePendingTransition(R.anim.enter_right,R.anim.exit_left);

            }
        });

        listViewNote.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDialogCopy(ArrayNote.get(i).getTen().toString());
                return false;
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //copyDirectoryOneLocationToAnotherLocation(database,database);

    }


    private void loadListNote() {
            Cursor cursor = database.GetData("SELECT * FROM NOTE");
            // clear mảng rỗng trước khi load lại dữ liệu
            ArrayNote.clear();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String ten = cursor.getString(1);
                String ghichu = cursor.getString(2);
                String date = cursor.getString(3);
//                if (ten.length() > 15) {
//                    ten = ten.substring(0, 15).trim();
//                }
//                if (ghichu.length() > 30) {
//                    ghichu = ghichu.substring(0, 25) + "....";
//                }
                ArrayNote.add(new Note(id, ten, ghichu, date));
            }

        setListview_Adapter();
//        Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim_listview);
//        listViewNote.startAnimation(animation);
    }
    private void setListview_Adapter()
    {
        listViewNote.setAdapter(adapterNote);
        adapterNote.notifyDataSetChanged();
    }

    private void sortname() {
        loadListNote();
        Collections.sort(ArrayNote);
        setListview_Adapter();
    }
    private void sortdate() {
        loadListNote();
        Collections.reverse(ArrayNote);
        setListview_Adapter();
    }
    private void Anhxa() {
        imgAddNote = (ImageView)findViewById(R.id.imageViewaddnote);
        listViewNote = (ListView)findViewById(R.id.listnote);
    }
    public void Hopthoaixoa(final Note note) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Xác nhận");
        alertDialog.setIcon(R.drawable.ic_menu_camera);
        alertDialog.setMessage("Bạn có muốn xóa NOTE "+note.getTen().toUpperCase() + " không?");
        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM NOTE WHERE Id = '"+note.getId()+"'");
                loadListNote();
            }
        });
        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        moveTaskToBack(true);
        finish();


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sort_menu, menu);
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


        //noinspection SimplifiableIfStatement
        if (id == R.id.sortname) {
            sortname();
            return true;
        }
        if (id == R.id.sortdate) {
            sortdate();
            return true;
        }
        if (id == R.id.sortauto) {
            loadListNote();
            setListview_Adapter();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Note Application ";
            String shareSub = "Perfect reminder";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
        } else if (id == R.id.nav_send) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Note Application.");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        } else if (id == R.id.nav_info) {
            ShowDialog(R.style.DialogAnimation);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private  void showDialogCopy(final String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String []array = {"Copy Tiêu đề", "Copy Nội dung", "Hủy"};
        builder.setCancelable(false);
        builder.setTitle("Chọn hành động");
        builder.setIcon(R.drawable.icon_info);

        builder.setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which)
                {
                    case 0:
                        CopyText(text);
                        break;
                    case 1:
                        CopyText(text);
                        break;
                    case 2:
                        break;
                }
            }
        });
        builder.show();

    }
    private void CopyText(String text){
        int sdk_Version = android.os.Build.VERSION.SDK_INT;
        if(sdk_Version < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
            Toast.makeText(getApplicationContext(), "Đã Copy!", Toast.LENGTH_SHORT).show();
        }
        else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Text Label", text);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(), "Đã Copy!", Toast.LENGTH_SHORT).show();
        }
    }
    public void ShowDialog(int animationSource)
    {
        final Dialog dialog = new Dialog(MainActivity.this);
        // bỏ title của dialog đặt trước khi gắn vào layout
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
//              dialog.setTitle("              Đăng nhập ");
        dialog.setCanceledOnTouchOutside(false); // set hiển thị cứng cho dialog
        AnhxaDialog(dialog);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.sequential);
        dialog.getWindow().getAttributes().windowAnimations = animationSource;
        btnok.setOnClickListener(new View.OnClickListener() {
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
        title = (TextView) dialog.findViewById(R.id.txttitleinfo);
        info  = (TextView) dialog.findViewById(R.id.txtinfo);
        btnok = (Button)dialog.findViewById(R.id.ok);

    }
    public static void copyDirectoryOneLocationToAnotherLocation(File sourceLocation, File targetLocation)
            throws IOException {

        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }

            String[] children = sourceLocation.list();
            for (int i = 0; i < sourceLocation.listFiles().length; i++) {

                copyDirectoryOneLocationToAnotherLocation(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
            }
        } else {

            InputStream in = new FileInputStream(sourceLocation);

            OutputStream out = new FileOutputStream(targetLocation);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }

    }

}
