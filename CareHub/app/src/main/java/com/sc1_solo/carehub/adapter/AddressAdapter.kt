package com.sc1_solo.carehub.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sc1_solo.carehub.AddressAddUpdateActivity
import com.sc1_solo.carehub.data.Address
import com.sc1_solo.carehub.R
import com.sc1_solo.carehub.databinding.ItemAddressBinding
import com.sc1_solo.carehub.helper.EXTRA_POSITION
import com.sc1_solo.carehub.helper.EXTRA_ADDRESS
import com.sc1_solo.carehub.helper.REQUEST_UPDATE
import kotlin.collections.ArrayList

class AddressAdapter(private val activity: Activity): RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {
    var listAddress = ArrayList<Address>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_address, parent, false)
        return AddressViewHolder(view)
    }

    override fun getItemCount(): Int = this.listAddress.size
    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bind(listAddress[position],position)
    }

    inner class AddressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemAddressBinding.bind(itemView)
        fun bind(quote: Address, position: Int) {
            binding.tvItemTitle.text = quote.title
            binding.tvItemAddress.text = quote.address

            binding.cvItemQuote.setOnClickListener{
                val intent = Intent(activity, AddressAddUpdateActivity::class.java)
                intent.putExtra(EXTRA_POSITION, position)
                intent.putExtra(EXTRA_ADDRESS, quote)
                activity.startActivityForResult(intent, REQUEST_UPDATE)
            }
        }
    }
}