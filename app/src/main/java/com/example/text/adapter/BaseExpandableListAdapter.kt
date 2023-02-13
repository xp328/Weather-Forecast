package com.example.text.adapter


import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import com.example.text.databinding.ItemExlistGroupBinding
import com.example.text.databinding.ItemExlistItemBinding



class ExpandableListViewAdapter(var group: List<String>, var child: ArrayList<List<String>>)
    :ExpandableListAdapter {


    override fun registerDataSetObserver(observer: DataSetObserver?) {}

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {}

    //设置一级列表的数量
    override fun getGroupCount(): Int {
        return group.size }

    //设置二级列表的数量
    override fun getChildrenCount(groupPosition: Int): Int {
        return child[groupPosition].size
    }

    //设置具体的一级列表省名
    override fun getGroup(groupPosition: Int): Any { return group[groupPosition] }


    //设置具体的二级列表城市名
    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return child[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long { return groupPosition.toLong() }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long { return childPosition.toLong() }

    override fun hasStableIds(): Boolean { return true }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?,
                              parent: ViewGroup?): View {

        //判断是否缓存，执行对应操作
        val bd: ItemExlistGroupBinding = if (convertView != null) {
            ItemExlistGroupBinding.bind(convertView)
        } else {
            ItemExlistGroupBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        }

        //设置一级列表数据
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

        //设置二级列表的数据
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
