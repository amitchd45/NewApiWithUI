package com.amit.kotlinrecyclercarousel.viewModels

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amit.kotlinrecyclercarousel.News
import com.amit.kotlinrecyclercarousel.network.ApiClient
import com.amit.kotlinrecyclercarousel.network.NewsInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GetNewsViewModel : ViewModel() {

    private val api = ApiClient.apiClient().create(NewsInterface::class.java)!!

    private var sendMutableNewsData: MutableLiveData<News>? = null

    fun getNewData(activity: Activity, country: String, page: String): LiveData<News> {

        sendMutableNewsData = MutableLiveData<News>()

        api.getHeadLines(country, page).enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {

                if (response.isSuccessful) {
                    println("Data == "+ response.body()?.articles!!.size)
                    sendMutableNewsData!!.value = response.body()
                }
            }
            override fun onFailure(call: Call<News>, t: Throwable) {
                Toast.makeText(activity, "" + t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
        return sendMutableNewsData as MutableLiveData<News>
    }

}
