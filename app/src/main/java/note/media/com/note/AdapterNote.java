package note.media.com.note;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by VP-T on 6/4/2017.
 */

public class AdapterNote extends BaseAdapter{

    private MainActivity context;
    private int layout;
    private List<Note> Listnote;

    public AdapterNote(MainActivity context, int layout, List<Note> listdaDatabases) {
        this.context = context;
        this.layout = layout;
        Listnote = listdaDatabases;
    }

    @Override
    public int getCount() {
        return Listnote.size();
    }

    @Override
    public Object getItem(int position) {
        return Listnote.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // tao cac view hien thi tren list view
    public class viewHolder
    {
        TextView txtTen, txtghichu, txtvien, txtdate;
        Button btnid;
        ImageView imgedit, imgdelete;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        viewHolder viewHolder;
        if (convertView == null)
        {
            viewHolder = new viewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            //convertView = inflater.inflate(layout,parent,false);



            // ánh xạ view hiển thị trên list từ dòng
            viewHolder.btnid = (Button)convertView.findViewById(R.id.button_id_note);
            viewHolder.txtTen = (TextView) convertView.findViewById(R.id.txtnamenote);
            viewHolder.txtghichu = (TextView) convertView.findViewById(R.id.txtcontent_note);
            viewHolder.txtdate = (TextView)convertView.findViewById(R.id.txtdate);
            viewHolder.txtvien = (TextView)convertView.findViewById(R.id.txtfill);


            viewHolder.imgdelete =(ImageView)convertView.findViewById(R.id.imgxoa);
            //viewHolder.imgedit = (ImageView)convertView.findViewById(R.id.imgedittext);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (viewHolder) convertView.getTag();
        }
        ArrayList<Integer> arrayColor = new ArrayList<>();
        arrayColor.add(Color.parseColor("#9C27B0"));
        arrayColor.add(Color.parseColor("#9575CD"));
        arrayColor.add(Color.parseColor("#7986CB"));
        arrayColor.add(Color.parseColor("#3949AB"));
        arrayColor.add(Color.parseColor("#64B5F6"));
        arrayColor.add(Color.parseColor("#2196F3"));
        arrayColor.add(Color.parseColor("#80DEEA"));
        arrayColor.add(Color.parseColor("#d81b60"));
        arrayColor.add(Color.parseColor("#2196f3"));
        arrayColor.add(Color.parseColor("#00c853"));
        arrayColor.add(Color.parseColor("#d32f2f"));
        arrayColor.add(Color.parseColor("#78909c"));




        //DecimalFormat dinhdangso = new DecimalFormat("###,###,###");
        final Note noteview = Listnote.get(position);
        viewHolder.btnid.setText(""+(position+1));
        viewHolder.btnid.setBackgroundColor(arrayColor.get(Ramdom()));
        viewHolder.btnid.setPadding(3,3,3,3);
        viewHolder.txtdate.setText(noteview.getNgay());

        if (noteview.getTen().length() > 15) {
            String ten = noteview.getTen().substring(0, 15).trim();
            viewHolder.txtTen.setText(ten);
        }else {
            viewHolder.txtTen.setText(noteview.getTen());
        }
        if (noteview.getNoidung().length() > 30) {
            String ghichu = noteview.getNoidung().substring(0, 20) + "....";
            viewHolder.txtghichu.setText(ghichu);
        }else {
            viewHolder.txtghichu.setText((noteview.getNoidung()));
        }




        // bắt sự kiện delete và update
//        viewHolder.imgedit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context,Main3Activity.class);
//                intent.putExtra("dulieuNote",note);
//                context.startActivity(intent);
//
//
//                //Toast.makeText(context,"Cập nhập dữ liệu "+chiTieu.getTen(),Toast.LENGTH_SHORT).show();
//            }
//        });



        viewHolder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"Xóa "+noteview.getTen(),Toast.LENGTH_SHORT).show();
                context.Hopthoaixoa(noteview);

            }
        });
        Animation animAlpha = AnimationUtils.loadAnimation(context,R.anim.enter);
        convertView.startAnimation(animAlpha);

        return convertView;
    }

    public int Ramdom()
    {

        Random ra = new Random();
        int number = ra.nextInt(12);
        Log.d("random",""+ number);
        //rand.nextInt((max - min) + 1) + min;
        //int soa = ra.nextInt((50-40)+1) +40;

        return number;
    }


}
