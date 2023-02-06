package com.example.text.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.text.R
import com.example.text.adapter.MyAdapter
import com.example.text.databinding.FragmentMoreBinding
import com.example.text.network.*
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList


class MoreFragment : Fragment() {

    private var _bd : FragmentMoreBinding? = null
    private val bd : FragmentMoreBinding get() = _bd!!

    lateinit var mRecyclerView : RecyclerView
    var mWeatherList : List<WeatherListData> = ArrayList()


    init {
        println("More")
/*        var conn : HttpURLConnection? = null
        var uriString = "http://175.178.70.219:8083/api/place/province"
        GlobalScope.async {
            conn = URL(uriString).openConnection() as HttpURLConnection
            conn!!.connect()
            conn!!.inputStream.use { input ->
                var data = input.bufferedReader().readText()
                var jsonDecode = Gson().fromJson(data,Person::class.java)
                provinceNameList = jsonDecode.data
                provinceNameList.forEach { i ->
                    println(i)
                }
            }
        }*/
/*        var citiesConn : HttpURLConnection? = null
        var citiesUriString = "http://175.178.70.219:8083/api/place/cities?province=$provinceName"
        GlobalScope.async {
            citiesConn = URL(citiesUriString).openConnection() as HttpURLConnection
            citiesConn!!.connect()
            citiesConn!!.inputStream.use { input ->
                var data = input.bufferedReader().readText()
                var jsonDecode = Gson().fromJson(data,Person::class.java)
                citiesNameList = jsonDecode.data
                citiesNameList.forEach { i ->
                    println(i)
                }
            }
        }*/

        /*provinceNameList.forEach {
            getCitiesData(it)
        }*/

    }
    /*@OptIn(DelicateCoroutinesApi::class)
    fun getCitiesData(provinceNames: String) {
        var conn: HttpURLConnection? = null
        var citiesUriString = "http://175.178.70.219:8083/api/place/cities?province=$provinceNames"
        var temp = provinceNames

        GlobalScope.async {
            //再循环 确保按顺序
            provinceNameList.forEach {
                conn = URL(citiesUriString).openConnection() as HttpURLConnection
                conn!!.connect()
                conn!!.inputStream.use { input ->
                    //在use里执行的顺序会混乱 通过if判断
                    if (it == temp) {
                        Log.d("ITTEMP","$it----$temp")
                        var data = input.bufferedReader().readText()
                        var jsonDecode = Gson().fromJson(data, Person::class.java)
                        citiesNameList = jsonDecode.data
                        tempArray.add(citiesNameList)
                    }
                }
            }

        }
    }*/

    fun getData(){
        var conn: HttpURLConnection? = null
        var uriString : String = "http://175.178.70.219:8083/api/place/province"//api


        //不能在主线程进行请求网络操作，这里使用协程这一方法进行网络请求
        GlobalScope.async {
            try {
                /*需要先去AndroidManifest.xml添加
                    * <uses-permission android:name="android.permission.INTERNET"/>
                      <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
                    *
                   不然会报错： EPERM (Operation not permitted)
                   还需要在AndroidManifest.xml配置文件的<application>标签中添加
                         * android:usesCleartextTraffic="true"
                            不然会报错：Cleartext HTTP traffic to xxx not permitted
                            * 为保证用户数据和设备的安全，Google针对下一代 Android 系统(Android P) 的应用程序，
                            * 将要求默认使用加密连接，这意味着 Android P 将禁止 App 使用所有未加密的连接，
                            * 因此运行 Android P 系统的安卓设备无论是接收或者发送流量，未来都不能明码传输，
                            * 需要使用(Transport Layer Security)传输层安全协议，
                            * 而 Android Nougat 和 Oreo 则不受影响。
                 * */

                conn = URL(uriString).openConnection() as HttpURLConnection
                conn!!.connect()
                conn!!.inputStream.use { input ->
                    val data = input.bufferedReader().readText()
                    val json = Gson().fromJson(data,Person::class.java)//已经拿到里数据
                    println("$data--\n--${json.data}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                println("e")
            } finally {
                conn?.disconnect()
                println("conn")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _bd = FragmentMoreBinding.inflate(inflater,container,false)

        //设置更多页面RecyclerView的Adapter
        val mMyAdapter: MyAdapter = MyAdapter()
        // 设置长按拖拽
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {

            override fun isItemViewSwipeEnabled(): Boolean {
                if (wListData.size == 3){
                    Toast.makeText(context,"列表已剩3个城市，达到删除最小值",Toast.LENGTH_SHORT).show()
                }
                return wListData.size > 3
            }

            //上下移动
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition

                mMyAdapter.notifyItemMoved(fPosition, toPosition)
                Collections.swap(wListData, fPosition, toPosition)
                return true
            }

            //左滑删除
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                wListData.removeAt(viewHolder.adapterPosition)
                mMyAdapter.notifyItemRemoved(viewHolder.adapterPosition)
            }
        })
        itemTouchHelper.attachToRecyclerView(bd.fMoreRecyclerView)
        //添加主页就获取的省名数据wListData
        mMyAdapter.submitList(wListData)
        bd.fMoreRecyclerView.adapter = mMyAdapter

//        ExpandableListView()

        return bd.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //跳转页面
        bd.fMoreButton.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment_to_addFragment)
        }

        //返回页面
        bd.fMoreImageButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}


