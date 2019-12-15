package com.example.apartmentcitizen.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apartmentcitizen.R;
import com.example.apartmentcitizen.home.dashboard.newsfeed.CommentDTO;
import com.example.apartmentcitizen.network.RetrofitInstance;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    Context context;
    List<CommentDTO> commentDTOList;

    public CommentAdapter(Context context, List<CommentDTO> commentDTOList) {
        this.context = context;
        this.commentDTOList = commentDTOList;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_comment, parent, false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        holder.fullName.setText(commentDTOList.get(position).getUser().getLastName() + " " + commentDTOList.get(position).getUser().getFirstName());
        holder.cmtDetail.setText(commentDTOList.get(position).getDetail());
        if (commentDTOList.get(position).getUser().getProfileImage() == null || commentDTOList.get(position).getUser().getProfileImage().isEmpty()) {
            holder.imgAvatar.setImageResource(R.drawable.image_avatar_default);
        } else {
            Glide.with(context)
                    .load(RetrofitInstance.BASE_URL + RetrofitInstance.VERSION_API
                            + RetrofitInstance.GET_USER_IMAGE + commentDTOList.get(position).getUser().getProfileImage())
                    .into(holder.imgAvatar);
        }
    }

    @Override
    public int getItemCount() {
        return commentDTOList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgAvatar;
        TextView cmtDetail, fullName;
        ConstraintLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.cmt_avatar);
            cmtDetail = itemView.findViewById(R.id.cmt_desc);
            fullName = itemView.findViewById(R.id.cmt_fullname);
            parent = itemView.findViewById(R.id.listComment);
        }
    }
}
