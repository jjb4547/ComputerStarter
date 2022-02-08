package com.example.computerstarter.Build;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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

import com.example.computerstarter.R;

public class PC_Build_Parts extends AppCompatActivity {
    ListView listView;
    //String mTitle[] = {"Ex1", "Ex2", "Ex3", "Ex4", "Ex5"};
    //String mDesc[] = {"D1", "D2", "D3", "D4", "D5"};
    String mTitle[] = new String[10];
    String mDesc[] = new String[10];
    //int images[] = {R.drawable.amd_cpu, R.drawable.amd_cpu, R.drawable.amd_cpu, R.drawable.amd_cpu, R.drawable.amd_cpu,
    //R.drawable.amd_cpu, R.drawable.amd_cpu, R.drawable.amd_cpu, R.drawable.amd_cpu, R.drawable.amd_cpu};
    int images[] = new int[10];
    int ids[] = new int[10];
    double[] partsId;
    String[] titles;
    String action;
    int[] componentImages;
    int counter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        String name = intent.getExtras().getString("name");
        partsId = intent.getDoubleArrayExtra("Parts");
        action = intent.getExtras().getString("Build");
        titles = intent.getStringArrayExtra("Titles");
        componentImages = intent.getIntArrayExtra("Images");
        setContentView(R.layout.activity_pc_part_build);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(name);
        counter = 0;
        for(int i = 0; i < PriceList.getLength(); i++)
        {
            if(PriceList.getPart(i).equals(name.toLowerCase()) && counter < 11) {
                mTitle[counter] = PriceList.getName(i);
                mDesc[counter] = PriceList.getPriceAsString(i);
                images[counter] = PriceList.getIcon(i);
                ids[counter]=i;
                counter++;
                System.out.println(counter);
            }
        }

        listView = findViewById(R.id.listview);

        MyAdapter adapter = new MyAdapter(this, mTitle, mDesc, images);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent buildIntent = new Intent(PC_Build_Parts.this,Build_Activity.class);
                buildIntent.putExtra("Name",name);
                buildIntent.putExtra("Build",action);
                if(i<counter) {
                    Toast.makeText(PC_Build_Parts.this, PriceList.getPriceAsString(ids[i]), Toast.LENGTH_SHORT).show();
                    switch (name) {
                        case "CPU":
                            partsId[0] = PriceList.getPrice(ids[i]);
                            titles[0] = PriceList.getName(ids[i]);
                            componentImages[0] = PriceList.getIcon(ids[i]);
                            break;
                        case "Motherboards":
                            partsId[1] = PriceList.getPrice(ids[i]);
                            titles[1] = PriceList.getName(ids[i]);
                            componentImages[1] = PriceList.getIcon(ids[i]);
                            break;
                        case "Memory":
                            partsId[2] = PriceList.getPrice(ids[i]);
                            titles[2] = PriceList.getName(ids[i]);
                            componentImages[2] = PriceList.getIcon(ids[i]);
                            break;
                        case "Storage":
                            partsId[3] = PriceList.getPrice(ids[i]);
                            titles[3] = PriceList.getName(ids[i]);
                            componentImages[3] = PriceList.getIcon(ids[i]);
                            break;
                        case "Power Supplies":
                            partsId[4] = PriceList.getPrice(ids[i]);
                            titles[4] = PriceList.getName(ids[i]);
                            componentImages[4] = PriceList.getIcon(ids[i]);
                            break;
                        case "CPU Cooler":
                            partsId[5] = PriceList.getPrice(ids[i]);
                            titles[5] = PriceList.getName(ids[i]);
                            componentImages[5] = PriceList.getIcon(ids[i]);
                            break;
                        case "Monitor":
                            partsId[6] = PriceList.getPrice(ids[i]);
                            titles[6] = PriceList.getName(ids[i]);
                            componentImages[6] = PriceList.getIcon(ids[i]);
                            break;
                        case "Video Cards":
                            partsId[7] = PriceList.getPrice(ids[i]);
                            titles[7] = PriceList.getName(ids[i]);
                            componentImages[7] = PriceList.getIcon(ids[i]);
                            break;
                        case "Cases":
                            partsId[8] = PriceList.getPrice(ids[i]);
                            titles[8] = PriceList.getName(ids[i]);
                            componentImages[8] = PriceList.getIcon(ids[i]);
                            break;
                    }
                    buildIntent.putExtra("Parts", partsId);
                    buildIntent.putExtra("Titles", titles);
                    buildIntent.putExtra("Images", componentImages);
                    startActivity(buildIntent);
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
