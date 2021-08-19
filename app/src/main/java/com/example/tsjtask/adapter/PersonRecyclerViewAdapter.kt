package com.example.tsjtask.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tsjtask.R
import com.example.tsjtask.data.Result
import com.example.tsjtask.databinding.ItemPersonMainActivityBinding

class PersonRecyclerViewAdapter(private val context: Context, private val listener: OnPersonClick) : RecyclerView.Adapter<PersonRecyclerViewAdapter.PersonViewHolder>() {

    private val differCallBack = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.login.uuid == newItem.login.uuid
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    val differ = AsyncListDiffer(this,differCallBack)

    inner class PersonViewHolder(val binding: ItemPersonMainActivityBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder(ItemPersonMainActivityBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.binding.personAgeTV.text = "${differ.currentList[position].dob.age} years old"
        holder.binding.personGenderTV.text = differ.currentList[position].gender
        holder.binding.personNameTV.text = "${differ.currentList[position].name.title}. ${differ.currentList[position].name.first} ${differ.currentList[position].name.last}"
        Glide.with(context)
            .load(differ.currentList[position].picture.large)
            .placeholder(R.drawable.ic_person)
            .into(holder.binding.personImageIV)
        holder.binding.personCL.setOnClickListener {
            listener.onClick(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    interface OnPersonClick{
        fun onClick(result: Result)
    }
}