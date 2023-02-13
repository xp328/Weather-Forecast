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
import com.example.text.adapter.MoreAdapter
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _bd = FragmentMoreBinding.inflate(inflater,container,false)

        //设置更多页面RecyclerView的Adapter
        val mMyAdapter: MoreAdapter = MoreAdapter()

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
        //添加主页获取的省名数据wListData
        mMyAdapter.submitList(wListData)
        bd.fMoreRecyclerView.adapter = mMyAdapter


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


