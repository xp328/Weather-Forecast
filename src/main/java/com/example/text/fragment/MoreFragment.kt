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
            //????????? ???????????????
            provinceNameList.forEach {
                conn = URL(citiesUriString).openConnection() as HttpURLConnection
                conn!!.connect()
                conn!!.inputStream.use { input ->
                    //???use??????????????????????????? ??????if??????
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


        //?????????????????????????????????????????????????????????????????????????????????????????????
        GlobalScope.async {
            try {
                /*????????????AndroidManifest.xml??????
                    * <uses-permission android:name="android.permission.INTERNET"/>
                      <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
                    *
                   ?????????????????? EPERM (Operation not permitted)
                   ????????????AndroidManifest.xml???????????????<application>???????????????
                         * android:usesCleartextTraffic="true"
                            ??????????????????Cleartext HTTP traffic to xxx not permitted
                            * ??????????????????????????????????????????Google??????????????? Android ??????(Android P) ??????????????????
                            * ???????????????????????????????????????????????? Android P ????????? App ?????????????????????????????????
                            * ???????????? Android P ???????????????????????????????????????????????????????????????????????????????????????
                            * ????????????(Transport Layer Security)????????????????????????
                            * ??? Android Nougat ??? Oreo ??????????????????
                 * */

                conn = URL(uriString).openConnection() as HttpURLConnection
                conn!!.connect()
                conn!!.inputStream.use { input ->
                    val data = input.bufferedReader().readText()
                    val json = Gson().fromJson(data,Person::class.java)//?????????????????????
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

        //??????????????????RecyclerView???Adapter
        val mMyAdapter: MyAdapter = MyAdapter()
        // ??????????????????
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {

            override fun isItemViewSwipeEnabled(): Boolean {
                if (wListData.size == 3){
                    Toast.makeText(context,"????????????3?????????????????????????????????",Toast.LENGTH_SHORT).show()
                }
                return wListData.size > 3
            }

            //????????????
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

            //????????????
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                wListData.removeAt(viewHolder.adapterPosition)
                mMyAdapter.notifyItemRemoved(viewHolder.adapterPosition)
            }
        })
        itemTouchHelper.attachToRecyclerView(bd.fMoreRecyclerView)
        //????????????????????????????????????wListData
        mMyAdapter.submitList(wListData)
        bd.fMoreRecyclerView.adapter = mMyAdapter

//        ExpandableListView()

        return bd.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //????????????
        bd.fMoreButton.setOnClickListener {
            findNavController().navigate(R.id.action_moreFragment_to_addFragment)
        }

        //????????????
        bd.fMoreImageButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}


