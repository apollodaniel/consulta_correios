package com.apollo.consulta_correios;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apollo.consulta_correios.models.PackageTemplate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.viewHolder>{

    List<PackageTemplate> package_codes;

    HistoryAdapter.OnHistoryListener listener;

    public HistoryAdapter(List<PackageTemplate> package_codes,HistoryAdapter.OnHistoryListener listener) {
        this.package_codes = package_codes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_adapter, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        if(position == 0){
            holder.line_separator.setVisibility(View.INVISIBLE);
        }
        if(package_codes.get(position) != null){
            holder.txtCode.setText(String.format("Código: %s", package_codes.get(position).code));
            holder.txtData.setText(String.format("Data: %s", package_codes.get(position).data));
        }
    }

    @Override
    public int getItemCount() {
        return package_codes.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.history_adapter_txt_code)
        TextView txtCode;
        @BindView(R.id.history_adapter_txt_data)
        TextView txtData;

        @BindView(R.id.line_separator)
        View line_separator;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onNoteClick(v,package_codes.get(getAbsoluteAdapterPosition()).code);
        }
    }
    public interface OnHistoryListener{
        void onNoteClick(View v, String code);
    }
}
