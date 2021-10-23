package com.apollo.consulta_correios;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apollo.consulta_correios.models.PackageTemplate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.viewHolder>{

    List<PackageTemplate> package_codes;

    public HistoryAdapter(List<PackageTemplate> package_codes) {
        this.package_codes = package_codes;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_adapter, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        if(position == 0){
            holder.line_separator.setVisibility(View.INVISIBLE);
        }
        holder.txtCode.setText(String.format("Código: %s", package_codes.get(position).code));
        holder.txtData.setText(String.format("Data: %s", package_codes.get(position).data));
    }

    @Override
    public int getItemCount() {
        return package_codes.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.history_adapter_txt_code)
        TextView txtCode;
        @BindView(R.id.history_adapter_txt_data)
        TextView txtData;

        @BindView(R.id.line_separator)
        View line_separator;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
