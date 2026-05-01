package com.example.academic_vault;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PaperAdapter extends RecyclerView.Adapter<PaperAdapter.PaperViewHolder> {

    private Context context;
    private List<PaperHelperClass> paperList;

    public PaperAdapter(Context context, List<PaperHelperClass> paperList) {
        this.context = context;
        this.paperList = paperList;
    }

    @NonNull
    @Override
    public PaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_paper, parent, false);
        return new PaperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaperViewHolder holder, int position) {
        PaperHelperClass paper = paperList.get(position);
        holder.txtModuleName.setText(paper.getModuleName());
        holder.txtYear.setText("Year: " + paper.getYear());

        holder.btnDownload.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(paper.getFileUri()), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            
            // Try to open with external PDF viewer
            Intent chooser = Intent.createChooser(intent, "Open PDF");
            context.startActivity(chooser);
        });
    }

    @Override
    public int getItemCount() {
        return paperList.size();
    }

    public static class PaperViewHolder extends RecyclerView.ViewHolder {
        TextView txtModuleName, txtYear;
        Button btnDownload;

        public PaperViewHolder(@NonNull View itemView) {
            super(itemView);
            txtModuleName = itemView.findViewById(R.id.txt_module_name);
            txtYear = itemView.findViewById(R.id.txt_year);
            btnDownload = itemView.findViewById(R.id.btn_download);
        }
    }
}
