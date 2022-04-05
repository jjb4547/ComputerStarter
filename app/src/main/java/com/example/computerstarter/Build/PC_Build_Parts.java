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
    String mTitle[] = new String[10];
    String mDesc[] = new String[10];
    int images[] = new int[10];
    int ids[] = new int[10];
    int[] partsID = new int[10];
    int[] numParts;
    int counter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pc_part_build);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("name"));
        partsID = getIntent().getIntArrayExtra("ID");
        numParts = getIntent().getIntArrayExtra("Num");
        counter = 0;
        for(int i = 0; i < PriceList.getLength(); i++)
        {
            if(PriceList.getPart(i).equals(getIntent().getExtras().getString("name").toLowerCase()) && counter < 11) {
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
                if(i<counter) {
                    Toast.makeText(PC_Build_Parts.this, PriceList.getPriceAsString(ids[i]), Toast.LENGTH_SHORT).show();
                    switch (getIntent().getExtras().getString("name")) {
                        case "CPU":
                            partsID[0] = ids[i];
                            numParts[0] = numParts[0]+1;
                            break;
                        case "Motherboards":
                            partsID[1] = ids[i];
                            numParts[1]=numParts[1]+1;
                            break;
                        case "Memory":
                            partsID[2] = ids[i];
                            numParts[2]=numParts[2]+1;
                            break;
                        case "Storage":
                            partsID[3] = ids[i];
                            numParts[3]=numParts[3]+1;
                            break;
                        case "Power Supplies":
                            partsID[4] = ids[i];
                            numParts[4]=numParts[4]+1;
                            break;
                        case "CPU Cooler":
                            partsID[5] = ids[i];
                            numParts[5]=numParts[5]+1;
                            break;
                        case "Monitor":
                            partsID[6] = ids[i];
                            numParts[6]=numParts[6]+1;
                            break;
                        case "Video Cards":
                            partsID[7] = ids[i];
                            numParts[7]=numParts[7]+1;
                            break;
                        case "Cases":
                            partsID[8] = ids[i];
                            numParts[8]=numParts[8]+1;
                            break;
                    }
                    startActivity(new Intent(PC_Build_Parts.this,Build_Activity.class)
                            .putExtra("Name",getIntent().getStringExtra("name"))
                            .putExtra("Build",getIntent().getStringExtra("Build"))
                            .putExtra("ID",partsID)
                            .putExtra("Num",numParts)
                            .putExtra("Edit",getIntent().getExtras().getString("Edit"))
                            .putExtra("editBuild",getIntent().getStringArrayExtra("editBuild")));
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
