package com.example.computerstarter.Build;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    TextView title;
    ImageButton backButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pc_part_build);
        title = findViewById(R.id.PartBuildTitle);
        backButton = findViewById(R.id.backPartBuild);
        title.setText(getIntent().getStringExtra("name"));
        partsID = getIntent().getIntArrayExtra("ID");
        numParts = getIntent().getIntArrayExtra("Num");
        counter = 0;
        for(int i = 0; i < PriceList.getLength(); i++)
        {
            if(PriceList.getPart(i).equals(getIntent().getExtras().getString("name").toLowerCase()) && counter < 11) {
                //Check compatibility
                switch(PriceList.getPart(i)){
                    case "cpu":
                        //Check socket w/ motherboard
                        if(partsID[1] != -1 && !(PriceList.getSocket(partsID[1]).equals(PriceList.getSocket(i))))
                            continue;
                        break;
                    case "motherboards":
                        //Check socket w/ CPU
                        if(partsID[0] != -1 && !(PriceList.getSocket(partsID[0]).equals(PriceList.getSocket(i))))
                            continue;
                        //Check mem_type with memory
                        if(partsID[2] != -1 && !(PriceList.getMemType(partsID[2]).equals(PriceList.getMemType(i))))
                            continue;
                        break;
                    case "memory":
                        //Check mem_type with motherboard
                        if(partsID[1] != -1 && !(PriceList.getMemType(partsID[1]).equals(PriceList.getMemType(i))))
                            continue;
                        break;
                }
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
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            LinearLayout details = view.findViewById(R.id.detailsParts);
            LinearLayout layout = view.findViewById(R.id.layoutParts);
            TextView textDetails = view.findViewById(R.id.socketInfo);
            layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
            int v = (details.getVisibility()==View.GONE)? View.VISIBLE: View.GONE;
            TransitionManager.beginDelayedTransition(layout, new AutoTransition());
            details.setVisibility(v);
            //Toast.makeText(PC_Build_Parts.this, "HELLO", Toast.LENGTH_SHORT).show();
            return true;
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i<counter) {
                    Toast.makeText(PC_Build_Parts.this, PriceList.getPriceAsString(ids[i]), Toast.LENGTH_SHORT).show();
                    switch (getIntent().getExtras().getString("name")) {
                        case "CPU":
                            partsID[0] = ids[i];
                            numParts[0] = 1;
                            break;
                        case "Motherboards":
                            partsID[1] = ids[i];
                            numParts[1]=1;
                            break;
                        case "Memory":
                            partsID[2] = ids[i];
                            numParts[2]=1;
                            break;
                        case "Storage":
                            partsID[3] = ids[i];
                            numParts[3]=1;
                            break;
                        case "Power Supplies":
                            partsID[4] = ids[i];
                            numParts[4]=1;
                            break;
                        case "CPU Cooler":
                            partsID[5] = ids[i];
                            numParts[5]=1;
                            break;
                        case "Monitor":
                            partsID[6] = ids[i];
                            numParts[6]=1;
                            break;
                        case "Video Cards":
                            partsID[7] = ids[i];
                            numParts[7]=1;
                            break;
                        case "Cases":
                            partsID[8] = ids[i];
                            numParts[8]=1;
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
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PC_Build_Parts.this,Build_Activity.class)
                        .putExtra("Name",getIntent().getStringExtra("name"))
                        .putExtra("Build",getIntent().getStringExtra("Build"))
                        .putExtra("ID",partsID)
                        .putExtra("Num",numParts)
                        .putExtra("Edit",getIntent().getExtras().getString("Edit"))
                        .putExtra("editBuild",getIntent().getStringArrayExtra("editBuild")));
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
        TextView details;

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

            convertView = getLayoutInflater().inflate(R.layout.cardviewparts,parent,false);
            ImageView images = convertView.findViewById(R.id.PartsImage);
            TextView myTitle = convertView.findViewById(R.id.PartsName);
            TextView myDesc = convertView.findViewById(R.id.PartsPrice);
            details = convertView.findViewById(R.id.socketInfo);

            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            myDesc.setText(rDesc[position]);
            String detailBuilder = detailBuilder(ids[position]);
            details.setText(detailBuilder);
            return convertView;
        }
    }

    public String detailBuilder(int partID)
    {
        String stringBuilder = "";
        switch(PriceList.getPart(partID))
        {
            case "cpu":
                stringBuilder += attachSocket(stringBuilder, partID) + "\n" + attachWattage(stringBuilder, partID);
                break;
            case "motherboards":
                stringBuilder += attachSocket(stringBuilder, partID) + "\n" + attachWattage(stringBuilder, partID) + "\n" + attachMemType(stringBuilder, partID) + "\n" + attachMemSlots(stringBuilder, partID);
                break;
            case "video cards":
            case "storage":
            case "power supplies":
            case "cpu cooler":
                stringBuilder += attachWattage(stringBuilder, partID);
                break;
            case "memory":
                stringBuilder += attachWattage(stringBuilder, partID) + "\n" + attachMemType(stringBuilder, partID) + "\n" + attachMemType(stringBuilder, partID);
                break;
        }
        return stringBuilder;
    }

    public String attachSocket(String stringBuilder, int partID)
    {
        return stringBuilder + "Socket: " + PriceList.getSocket(partID);
    }

    public String attachWattage(String stringBuilder, int partID)
    {
        return stringBuilder + "Wattage: " + PriceList.getWattage(partID);
    }

    public String attachMemType(String stringBuilder, int partID)
    {
        return stringBuilder + "Memory Type: " + PriceList.getMemType(partID);
    }

    public String attachMemSlots(String stringBuilder, int partID)
    {
        return stringBuilder + "Memory Slots: " + PriceList.getMemSlots(partID);
    }
}
