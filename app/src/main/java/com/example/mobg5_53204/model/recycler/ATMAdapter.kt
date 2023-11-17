package com.example.mobg5_53204.model.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mobg5_53204.database.api.ATMRecord
import com.example.mobg5_53204.databinding.ItemAtmBinding

class ATMAdapter(private val clickListener: ATMListener) :
    ListAdapter<ATMRecord, ATMAdapter.ViewHolder>(ATMDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemAtmBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ATMRecord, clickListener: ATMListener) {
            val atmFields = item.fields  // extracting ATMFields from ATMRecord
            binding.apply {
                this.atm = atmFields
                this.clickListener = clickListener
                this.record = item

                agence.text = atmFields.agence
                adresseFr.text = atmFields.adresse_fr
                // continue binding other fields

                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemAtmBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class ATMDiffCallback : DiffUtil.ItemCallback<ATMRecord>() {

    override fun areItemsTheSame(oldItem: ATMRecord, newItem: ATMRecord): Boolean {
        return oldItem.recordid == newItem.recordid
    }

    override fun areContentsTheSame(oldItem: ATMRecord, newItem: ATMRecord): Boolean {
        return oldItem == newItem
    }
}


class ATMListener(val clickListener: (atmId: String) -> Unit) {
    fun onClick(record: ATMRecord) = clickListener(record.recordid)

}