package com.example.nurservice.ui

import android.graphics.Color
import android.os.Build
import android.os.Build.VERSION_CODES
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.R
import androidx.transition.TransitionManager
import com.example.nurservice.model.Appointment
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import android.content.Context;
import android.widget.ArrayAdapter;


class AppointmentAdapter: RecyclerView.Adapter<AppointmentAdapter.ViewHolder> (){
    val appointments = ArrayList<Appointment>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvAppointmentId = itemView.findViewById<TextView>(R.id.tv_id)
        val tvDoctorName = itemView.findViewById<TextView>(R.id.tv_medico)
        val tvScheduledDate = itemView.findViewById<TextView>(R.id.tv_fecha)
        val tvSheduledTime = itemView.findViewById<TextView>(R.id.tv_hora)

        val tvSpecialty = itemView.findViewById<TextView>(R.id.tv_especilidad)
        val tvDescription = itemView.findViewById<TextView>(R.id.tv_descripcion)
        val tvStatus = itemView.findViewById<TextView>(R.id.tv_status)
        val tvType = itemView.findViewById<TextView>(R.id.tv_tipo)
        val tvCreateAt = itemView.findViewById<TextView>(R.id.tv_creado_en)

        val ibExpand = itemView.findViewById<ImageButton>(R.id.ib_expand)
        val linearLayoutDetails = itemView.findViewById<LinearLayout>(R.id.linear_layout_detalles)

        val flStatus = itemView.findViewById<FrameLayout>(R.id.fl_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_appointment, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appointment = appointments[position]

        holder.tvAppointmentId.text = "Cita médica N#${appointment.id}"
        holder.tvDoctorName.text = appointment.doctor.name
        holder.tvScheduledDate.text = "Atención el día ${appointment.scheduledDate}"
        holder.tvSheduledTime.text = "A las ${appointment.scheduledTime}"

        holder.tvSpecialty.text = appointment.specialty.name
        holder.tvDescription.text = appointment.description
        changeColorStatus(holder, position)

        holder.tvType.text = appointment.type

        val fecha = appointment.createdAt
        val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        val dateString = OffsetDateTime.parse(fecha)
        val formatFecha: String = dateString.format(dateFormatter)

        holder.tvCreateAt.text = "La cita se registró el día ${ formatFecha } con los siguientes síntomas: "

        holder.ibExpand.setOnClickListener {
            TransitionManager.beginDelayedTransition( holder.linearLayoutDetails as ViewGroup, AutoTransition())

            if (holder.linearLayoutDetails.visibility == View.VISIBLE) {

                changeColorStatus(holder, position)

            } else {
                holder.linearLayoutDetails.visibility = View.VISIBLE
                holder.ibExpand.setImageResource(R.drawable.ic_expand_lesd)
                holder.flStatus.setBackgroundColor(Color.parseColor("#FFFFFF"))

            }
        }

    }

    override fun getItemCount() = appointments.size

    private fun changeColorStatus(holder: ViewHolder, position: Int){
        val appointment = appointments[position]
        val estado = appointment.status

        when(estado) {
            "Cancelada" -> {
                holder.tvStatus.text = appointment.status
                holder.flStatus.setBackgroundColor(Color.parseColor("#FFB0B0"))
            }
            "Confirmada" -> {
                holder.tvStatus.text = appointment.status
                holder.flStatus.setBackgroundColor(Color.parseColor("#86C8BC"))
            }
            "Reservada" -> {
                holder.tvStatus.text = appointment.status
                holder.flStatus.setBackgroundColor(Color.parseColor("#FFF6BD"))
            }
            "Atendida" -> {
                holder.tvStatus.text = appointment.status
                holder.flStatus.setBackgroundColor(Color.parseColor("#8EA7E9"))
            }
        }
        holder.linearLayoutDetails.visibility = View.GONE
        holder.ibExpand.setImageResource(/* resId = */ R.drawable.ic_expand_more)

    }

}
