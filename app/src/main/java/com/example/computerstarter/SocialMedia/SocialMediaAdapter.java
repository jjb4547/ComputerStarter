package com.example.computerstarter.SocialMedia;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.computerstarter.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SocialMediaAdapter extends RecyclerView.Adapter<SocialMediaAdapter.MyHolder> {

    Context context;
    String myuid;
    private DatabaseReference postref, likeRef;
    boolean mprocesslike = false;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    FirebaseUser user;
    private String mydp;

    public SocialMediaAdapter(Context context, List<SocialMediaModel> modelPosts) {
        this.context = context;
        this.modelPosts = modelPosts;
        myuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        user = FirebaseAuth.getInstance().getCurrentUser();
        //Uri profile = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
        postref = FirebaseDatabase.getInstance().getReference().child("Posts");
        StorageReference storageReference1 = FirebaseStorage.getInstance().getReference().child("ProfileImage/Users/"+user.getUid()+"/profile");
        storageReference1.getDownloadUrl().addOnSuccessListener(uri -> mydp = uri.toString());
    }

    List<SocialMediaModel> modelPosts;

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_posts, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        final String uid = modelPosts.get(position).getUid();
        String nameh = modelPosts.get(position).getUname();
        //Uri profile = modelPosts.get(position).getProfile();
        final String titlee = modelPosts.get(position).getTitle();
        final String descri = modelPosts.get(position).getDescription();
        final String ptime = modelPosts.get(position).getPtime();
        String dp = modelPosts.get(position).getUdp();
        String plike = modelPosts.get(position).getPlike();
       if(modelPosts.get(position).getisDefault()==false){
           holder.image.setVisibility(View.VISIBLE);
       }else{
           holder.image.setVisibility(View.GONE);
       }
        final String image = modelPosts.get(position).getUimage();
        String email = modelPosts.get(position).getUemail();
        String comm = modelPosts.get(position).getPcomments();
        final String pid = modelPosts.get(position).getPtime();
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(Long.parseLong(ptime));
        String timedate = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();
        holder.name.setText(nameh);
        holder.title.setText(titlee);
        holder.description.setText(descri);
        holder.time.setText(timedate);
        holder.like.setText(plike + " Likes");
        holder.comments.setText(comm + " Comments");
        holder.picture.setImageURI(modelPosts.get(position).getProfile());
        //Changes the color of the Heart
        postref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(pid).child("Likes").hasChild(myuid)){
                    holder.likebtn.setColorFilter(context.getResources().getColor(R.color.red));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //holder.picture.setImageURI(profile);
        try {
            Glide.with(context).load(dp).into(holder.picture);
        } catch (Exception e) {

        }
        try {
            Glide.with(context).load(image).into(holder.image);
        } catch (Exception e) {
        }
        holder.likebtn.setOnClickListener(v -> {
            final int plike1 = Integer.parseInt(modelPosts.get(holder.getAdapterPosition()).getPlike());
            mprocesslike = true;
            final String postid = modelPosts.get(holder.getAdapterPosition()).getPtime();
            postref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Toast.makeText(context.getApplicationContext(), "CLICKED ON LIKE",Toast.LENGTH_SHORT).show();
                    if (mprocesslike) {
                        //Toast.makeText(context.getApplicationContext(), "CLICKED ON LIKE",Toast.LENGTH_SHORT).show();
                        if (dataSnapshot.child(postid).child("Likes").hasChild(myuid)) {
                            postref.child(postid).child("plike").setValue("" + (plike1 - 1));
                            postref.child(postid).child("Likes").child(myuid).removeValue();
                            holder.likebtn.setSelected(false);
                            mprocesslike = false;
                        } else {
                            holder.likebtn.setSelected(true);
                            //holder.likebtn.setColorFilter(context.getResources().getColor(R.color.red));
                            postref.child(postid).child("plike").setValue("" + (plike1 + 1));
                            addLike(postid,holder);
                            mprocesslike = false;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        });
        if(!uid.equals(myuid)){
            holder.more.setVisibility(View.GONE);
        }
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context.getApplicationContext(), "CLICKED ON EDIT", Toast.LENGTH_SHORT).show();
                showMoreOptions(holder.more, uid, myuid, ptime, image);
            }
        });
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int pcomment = Integer.parseInt(modelPosts.get(holder.getAdapterPosition()).getPcomments());
                Intent intent = new Intent(context, SocialMediaPostActivities.class);
                intent.putExtra("pid", ptime).putExtra("comments", pcomment);
                context.startActivity(intent);
            }
        });
        holder.comments.setOnClickListener(v->{
            final int pcomment = Integer.parseInt(modelPosts.get(holder.getAdapterPosition()).getPcomments());
            Intent intent = new Intent(context, SocialMediaPostActivities.class);
            intent.putExtra("pid", ptime).putExtra("comments", pcomment);
            context.startActivity(intent);
        });
    }

    private void addLike(String ptime, MyHolder holder){
        String timestamp = String.valueOf(System.currentTimeMillis());
        DatabaseReference datarf = FirebaseDatabase.getInstance().getReference("Posts").child(ptime).child("Likes");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("ptime", timestamp);
        hashMap.put("uid", myuid);
        hashMap.put("udp", mydp);
        hashMap.put("uname", user.getDisplayName());
        datarf.child(myuid).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //progressDialog.dismiss();
                Toast.makeText(context.getApplicationContext(), "Added", Toast.LENGTH_LONG).show();
                //comment.setText("");
                //updatecommetcount();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //progressDialog.dismiss();
                Toast.makeText(context.getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showMoreOptions(ImageButton more, String uid, String myuid, final String pid, final String image) {
        PopupMenu popupMenu = new PopupMenu(context, more, Gravity.END);
        if (uid.equals(myuid)) {
            popupMenu.getMenu().add(Menu.NONE, 0, 0, "DELETE");
        }
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == 0) {
                deltewithImage(pid, image);
            }
            return false;
        });
        popupMenu.show();
    }

    private void deltewithImage(final String pid, String image) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Deleting");
        StorageReference picref = FirebaseStorage.getInstance().getReferenceFromUrl(image);
        picref.delete().addOnSuccessListener(aVoid -> {
            Query query = FirebaseDatabase.getInstance().getReference("Posts").orderByChild("ptime").equalTo(pid);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        dataSnapshot1.getRef().removeValue();
                    }
                    pd.dismiss();
                    Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_LONG).show();
                    notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return modelPosts.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ImageView picture, image;
        TextView name, time, title, description, like, comments;
        ImageButton more,likebtn;
        Button comment;

        LinearLayout profile;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.picturetv);
            image = itemView.findViewById(R.id.pimagetv);
            name = itemView.findViewById(R.id.unametv);
            time = itemView.findViewById(R.id.utimetv);
            more = itemView.findViewById(R.id.morebtn);
            title = itemView.findViewById(R.id.ptitletv);
            description = itemView.findViewById(R.id.descript);
            like = itemView.findViewById(R.id.plikeb);
            comments = itemView.findViewById(R.id.pcommentco);
            likebtn = itemView.findViewById(R.id.like);
            comment = itemView.findViewById(R.id.comment);
            profile = itemView.findViewById(R.id.profilelayout);
        }
    }
}
