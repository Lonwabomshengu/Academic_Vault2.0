package com.example.academic_vault;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.ResourceViewHolder> {

    private Context context;
    private List<ResourceHelperClass> resourceList;

    public ResourceAdapter(Context context, List<ResourceHelperClass> resourceList) {
        this.context = context;
        this.resourceList = resourceList;
    }

    @NonNull
    @Override
    public ResourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_resource, parent, false);
        return new ResourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResourceViewHolder holder, int position) {
        ResourceHelperClass resource = resourceList.get(position);
        holder.txtModuleName.setText(resource.getModuleName());
        holder.txtType.setText(resource.getType());

        holder.btnView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(resource.getFileUri()), "application/*");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            
            Intent chooser = Intent.createChooser(intent, "Open Resource");
            context.startActivity(chooser);
        });
    }

    @Override
    public int getItemCount() {
        return resourceList.size();
    }

    public static class ResourceViewHolder extends RecyclerView.ViewHolder {
        TextView txtModuleName, txtType;
        Button btnView;
        ImageView imgIcon;

        public ResourceViewHolder(@NonNull View itemView) {
            super(itemView);
            txtModuleName = itemView.findViewById(R.id.txt_resource_module);
            txtType = itemView.findViewById(R.id.txt_resource_type);
            btnView = itemView.findViewById(R.id.btn_view_resource);
            imgIcon = itemView.findViewById(R.id.img_resource_type);
        }
    }
}
