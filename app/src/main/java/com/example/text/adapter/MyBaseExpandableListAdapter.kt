package com.example.text.adapter

import android.content.ClipData.Item
import android.content.Context
import android.database.DataSetObserver
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.text.R
import com.example.text.databinding.FragmentAddBinding
import com.example.text.databinding.ItemExlistGroupBinding
import com.example.text.databinding.ItemExlistItemBinding
import com.example.text.databinding.ItemListMoreBinding
import java.security.acl.Group
import kotlin.random.Random


class ExpandableListViewAdapter(var group: List<String>, var child: ArrayList<ArrayList<String>>)
    :ExpandableListAdapter {


    override fun registerDataSetObserver(observer: DataSetObserver?) {}

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {}

    override fun getGroupCount(): Int {
//        Log.d("addAdapter------","${child}")
        return group.size }

    override fun getChildrenCount(groupPosition: Int): Int {
//        Log.d("addAdapter","${child.size}")
        return child[groupPosition].size
//        TODO("Not yet implemented")
    }

    override fun getGroup(groupPosition: Int): Any { return group[groupPosition] }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return child[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long { return groupPosition.toLong() }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long { return childPosition.toLong() }

    override fun hasStableIds(): Boolean { return true }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?,
                              parent: ViewGroup?): View {

        val bd: ItemExlistGroupBinding = if (convertView != null) {
            ItemExlistGroupBinding.bind(convertView)
        } else {
            ItemExlistGroupBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        }

        bd.tvGroup.text = group[groupPosition]

        return bd.root
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean,
                              convertView: View?, parent: ViewGroup?): View {


        val bd: ItemExlistItemBinding = if (convertView != null) {
            ItemExlistItemBinding.bind(convertView)
        } else {
            ItemExlistItemBinding.inflate(LayoutInflater.from(parent?.context),
                parent, false)
        }

        Log.d("TAG", childPosition.toString())
        bd.tvChild.text = child[groupPosition][childPosition]

        return bd.root
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean { return true }

    override fun areAllItemsEnabled(): Boolean { return false }

    override fun isEmpty(): Boolean { return false }

    override fun onGroupExpanded(groupPosition: Int) {}

    override fun onGroupCollapsed(groupPosition: Int) {}

    override fun getCombinedChildId(groupId: Long, childId: Long): Long { return 0 }

    override fun getCombinedGroupId(groupId: Long): Long { return 0 }


}
