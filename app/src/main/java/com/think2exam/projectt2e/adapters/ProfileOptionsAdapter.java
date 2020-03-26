package com.think2exam.projectt2e.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.think2exam.projectt2e.R;
import com.think2exam.projectt2e.modals.ProfileOptionsModal;
import com.think2exam.projectt2e.ui.activities.AboutActivity;
import com.think2exam.projectt2e.ui.activities.EditProfileActivity;
import com.think2exam.projectt2e.ui.activities.PointsSummaryActivity;
import com.think2exam.projectt2e.utilities.User;
import com.think2exam.projectt2e.view_holders.ProfileOptionsViewHolder;
import java.util.ArrayList;
import com.think2exam.projectt2e.Constants;

import static com.think2exam.projectt2e.Constants.*;

public class ProfileOptionsAdapter extends RecyclerView.Adapter<ProfileOptionsViewHolder> {

    private final User user = User.getInstance();
    private Context context;
    private ArrayList<ProfileOptionsModal> list;
    private String tagPrefix;

    public ProfileOptionsAdapter(Context context, ArrayList<ProfileOptionsModal> list, String tag){
        this.context = context;
        this.list = list;
        this.tagPrefix = tag;
    }

    @NonNull
    @Override
    public ProfileOptionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_option_modal,parent, false);
        return new ProfileOptionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProfileOptionsViewHolder holder, final int position) {

        ProfileOptionsModal item = list.get(position);
        holder.iconView.setImageResource(item.iconId);
        holder.titleView.setText(item.title);

        holder.profileOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;

                if (tagPrefix == Constants.TAG_PROFILE_PERSONAL){
                    switch (holder.getAdapterPosition()){
                        case 0:
                            intent = new Intent(context,EditProfileActivity.class);
                            intent.putExtra(PROFILE_NAME,user.name);
                            intent.putExtra(PHONE_NO,user.phoneNo);
                            break;
                        case 1:
                            intent = new Intent(context, PointsSummaryActivity.class);
                            break;
                        default:
                            Toast.makeText(context,"Error",Toast.LENGTH_LONG).show();
                    }
                }else if(tagPrefix == Constants.TAG_PROFILE_APPLICATION){
                    switch (holder.getAdapterPosition()){
                        case 0:
                            Toast.makeText(context,tagPrefix + " position: " + position,Toast.LENGTH_LONG).show();
                            break;
                        case 1:
                            Toast.makeText(context,tagPrefix + " position: " + position,Toast.LENGTH_LONG).show();
                            break;
                        case 2:
                            intent = new Intent(context, AboutActivity.class);
                            break;
                        default:
                            Toast.makeText(context,"Error",Toast.LENGTH_LONG).show();
                    }
                }

                if(intent != null){
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
