package com.example.computerstarter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PC_Build_Parts extends AppCompatActivity {
    ListView listView;
    //String mTitle[] = {"Ex1", "Ex2", "Ex3", "Ex4", "Ex5"};
    //String mDesc[] = {"D1", "D2", "D3", "D4", "D5"};
    String mTitle[] = new String[10];
    String mDesc[] = new String[10];
    int images[] = {R.drawable.amd_cpu, R.drawable.amd_cpu, R.drawable.amd_cpu, R.drawable.amd_cpu, R.drawable.amd_cpu,
            R.drawable.amd_cpu, R.drawable.amd_cpu, R.drawable.amd_cpu, R.drawable.amd_cpu, R.drawable.amd_cpu};

    //ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        String name = intent.getExtras().getString("name");
        setContentView(R.layout.activity_pc_part_build);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(name);

        int counter = 0;
        for(int i = 0; i < PriceList.getLength(); i++)
        {
            if(PriceList.getPart(i).equals(name.toLowerCase()) && counter < 11) {
                mTitle[counter] = PriceList.getName(i);
                mDesc[counter] = PriceList.getPriceAsString(i);
                counter++;
            }
        }

        listView = findViewById(R.id.listview);

        MyAdapter adapter = new MyAdapter(this, mTitle, mDesc, images);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Toast.makeText(PC_Build_Parts.this, "Example Text 1", Toast.LENGTH_SHORT).show();
                }
            }
        });



//        listView = findViewById(R.id.listview);
//        ArrayList<String> arrayList = new ArrayList<>();
//
//        for(int i = 0; i < PriceList.getLength(); i++)
//        {
//            if(PriceList.getPart(i).equals(name.toLowerCase()))
//                arrayList.add(PriceList.getName(i));
//        }
//
//        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
//
//        listView.setAdapter((arrayAdapter));
//        listView.setClickable(true);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(PC_Build_Parts.this, "Clicked: " + i + " " + arrayList.get(i).toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
        //TextView text = findViewById(R.id.pc_build_parts);
    }

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        inflater.inflate(R.menu.app_menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("PC PART ID: " + item.getItemId());
        if (item.getItemId()==android.R.id.home) {
            this.finish();
            return true;
        }else
            return super.onOptionsItemSelected(item);
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rTitle[];
        String rDesc[];
        int rImgs[];

        MyAdapter(Context c, String title[], String desc[], int imgs[]) {
            super(c, R.layout.row, R.id.text1, title);
            this.context = c;
            this.rTitle = title;
            this.rDesc = desc;
            this.rImgs = imgs;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflator = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflator.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.text1);
            TextView myDesc = row.findViewById(R.id.text2);

            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            myDesc.setText(rDesc[position]);
            return row;
        }
    }
}
