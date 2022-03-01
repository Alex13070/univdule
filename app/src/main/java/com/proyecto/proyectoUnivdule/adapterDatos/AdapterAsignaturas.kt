package com.proyecto.proyectoUnivdule.adapterDatos


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proyecto.proyectoUnivdule.R
import com.proyecto.proyectoUnivdule.modelo.Asignatura
import com.proyecto.proyectoUnivdule.modelo.Estudios

class AdapterAsignaturas(private val datos: ArrayList<Asignatura>) : RecyclerView.Adapter<AdapterAsignaturas.ViewHolderDatos>() {

    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    class ViewHolderDatos(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {

        var nombre = itemView.findViewById<TextView>(R.id.txtAsignatura)


        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }

        fun asignarDatos(asignatura: Asignatura) {
            nombre.text = asignatura.nombre
        }
    }

    override fun onBindViewHolder(holder: ViewHolderDatos, position: Int) {
        holder.asignarDatos(datos[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDatos {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_asignatura, parent, false)
        return ViewHolderDatos(view, listener)
    }

    override fun getItemCount(): Int {
        return datos.size
    }
}