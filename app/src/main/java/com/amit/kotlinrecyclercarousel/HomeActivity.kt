package com.amit.kotlinrecyclercarousel

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amit.kotlinrecyclercarousel.adapter.NewAdapter
import com.amit.kotlinrecyclercarousel.viewModels.GetNewsViewModel
import com.littlemango.stacklayoutmanager.StackLayoutManager.*


class HomeActivity : AppCompatActivity() {
    private lateinit var getNewsViewModel: GetNewsViewModel
    private lateinit var activity: HomeActivity
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewAdapter
    private val articles = mutableListOf<Article>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var progressDialog: ProgressDialog
    private lateinit var progress_circular: ProgressBar
    var currentItes: Int = 0
    var totalItem: Int = 0
    var scrollOutItems: Int = 0
    var page: Int = 1
    var isScrolling = false
    private var totalCount = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        init()
        showDialog()
        setAdapterPreFetchData()
        scrollView()
        getArticle()

    }

    private fun init() {
        getNewsViewModel = ViewModelProviders.of(this).get(GetNewsViewModel::class.java)
        activity = this@HomeActivity
        recyclerView = findViewById(R.id.recyclerView)
        progress_circular = findViewById(R.id.progress_circular)
    }

    private fun setAdapterPreFetchData() {
        adapter = NewAdapter(activity, articles)
        recyclerView.adapter = adapter
        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager
    }

    private fun showDialog() {
        progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("please wait...")
        progressDialog.show()
    }

    private fun scrollView() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                    Log.i("page", "isScrolling: ===$isScrolling")
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItes = linearLayoutManager.childCount
                totalItem = linearLayoutManager.itemCount
                scrollOutItems = linearLayoutManager.findFirstVisibleItemPosition()
//                Log.i("page", "currentItes: ===$currentItes")
                Log.i("page", "first visible item: ===$scrollOutItems")
                Log.i("page", "Total count: ===$totalItem")


                if (isScrolling && currentItes + scrollOutItems == totalItem - 2) {
                    isScrolling = false
//                    Log.i("page", "isScrolling: ===$isScrolling")
                    page++
                    getNextData(page)
                }
            }
        })
    }

    private fun getNextData(page: Int) {
        progress_circular.visibility = View.VISIBLE
        getNewsViewModel.getNewData(activity, "in", page.toString()).observe(activity, Observer<News> { t ->
            if (t?.status.equals("ok")) {
                if (t != null) {
                    for (element in t.articles) {
                        articles.add(element)
                    }
                    adapter.notifyDataSetChanged()
                    progress_circular.visibility = View.GONE
                }
            } else {
                progress_circular.visibility = View.GONE
                Toast.makeText(activity, "list not fond...", Toast.LENGTH_SHORT).show()

            }
        })
    }

    private fun getArticle() {
        getNewsViewModel.getNewData(activity, "in", page.toString()).observe(activity, Observer<News> { t ->
            if (t?.status.equals("ok")) {
                if (t != null) {
                    totalCount = t.totalResults
                    articles.addAll(t.articles)
                    adapter.notifyDataSetChanged()
                    progressDialog.dismiss()
                }

            }
        })
    }
}


