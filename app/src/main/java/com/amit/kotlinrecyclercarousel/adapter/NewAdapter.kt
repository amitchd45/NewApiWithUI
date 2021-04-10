package com.amit.kotlinrecyclercarousel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amit.kotlinrecyclercarousel.Article
import com.amit.kotlinrecyclercarousel.News
import com.amit.kotlinrecyclercarousel.R
import com.bumptech.glide.Glide

class NewAdapter(var context: Context, var articles: List<Article>) : RecyclerView.Adapter<NewAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var newImage = itemView.findViewById<ImageView>(R.id.newsImage)!!
        var newTitle = itemView.findViewById<TextView>(R.id.newTitle)!!
        var newDescription = itemView.findViewById<TextView>(R.id.newDescription)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false)

        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        val article=articles.get(position)
        holder.newTitle.text=article.title
        holder.newDescription.text=article.description
        Glide.with(context).load(article.urlToImage).into(holder.newImage)

    }

    override fun getItemCount(): Int {
        return articles.size
    }
}