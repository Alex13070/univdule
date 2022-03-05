package com.proyecto.proyectoUnivdule.adapterDatos


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.proyecto.proyectoUnivdule.R
import com.proyecto.proyectoUnivdule.administracionBBDD.UnivduleDB
import com.proyecto.proyectoUnivdule.modelo.Tarea

class AdapterTareas(private val datos: ArrayList<Tarea>, private val bbdd: UnivduleDB) : RecyclerView.Adapter<AdapterTareas.ViewHolderDatos>() {

    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: AdapterTareas.OnItemClickListener){
        this.listener = listener
    }


    class ViewHolderDatos(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val nombre = itemView.findViewById<TextView>(R.id.txtTareaNombre)
        val asignatura = itemView.findViewById<TextView>(R.id.txtTareaAsignatura)
        val fecha = itemView.findViewById<TextView>(R.id.txtTareaFecha)
        val contenido = itemView.findViewById<TextView>(R.id.txtTareaContenido)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }

        fun asignarDatos(tarea: Tarea, bbdd: UnivduleDB) {
            nombre.text = tarea.nombre
            asignatura.text = bbdd.asignaturaDAO().findById(tarea.idAsignatura).nombre
            fecha.text = tarea.fecha
            contenido.text = tarea.contenido
        }
    }

    override fun onBindViewHolder(holder: ViewHolderDatos, position: Int) {
        holder.asignarDatos(datos[position], bbdd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDatos {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tarea, parent, false)
        return ViewHolderDatos(view, listener)
    }

    override fun getItemCount(): Int {
        return datos.size
    }
}