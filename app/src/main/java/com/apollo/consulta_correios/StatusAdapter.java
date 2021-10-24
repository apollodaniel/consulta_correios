package com.apollo.consulta_correios;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apollo.consulta_correios.models.Evento;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.viewHolder>{

    List<Evento> eventos;

    public StatusAdapter(List<Evento> eventos) {
        this.eventos = eventos;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_adapter, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        String status = eventos.get(position).status;
        String local = eventos.get(position).local;
        String data = eventos.get(position).data;
        String hora = eventos.get(position).hora;

        String outro_string;

        StringBuilder outro_text = new StringBuilder();

        for (String outro : eventos.get(position).subStatus){
            outro_text.append(outro.replace("Destino:", "Para").replace("Origem:", "De").replace("Local:", "De"));
        }

        outro_string = outro_text.toString().replace(":","");

        if(outro_string.isEmpty()) outro_string = "Nenhuma informação";


        holder.txtStatus.setText(String.format("Status: %s",status));
        holder.txtLocal.setText(String.format("Local: %s",local));
        holder.txtData.setText(String.format("Data: %s",data));
        holder.txtHora.setText(String.format("Hora: %s",hora));
        holder.txtOutro.setText(String.format("Outro: %s",outro_string));
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.custom_adapter_txt_status)
        TextView txtStatus;
        @BindView(R.id.custom_adapter_txt_local)
        TextView txtLocal;
        @BindView(R.id.custom_adapter_txt_data)
        TextView txtData;
        @BindView(R.id.custom_adapter_txt_hora)
        TextView txtHora;
        @BindView(R.id.custom_adapter_txt_outro)
        TextView txtOutro;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
