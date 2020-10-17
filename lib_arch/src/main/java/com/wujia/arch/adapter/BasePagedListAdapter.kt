package com.wujia.arch.adapter

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver

/**
 * Paging list.
 *
 * @author WuJia.
 * @date 2020/9/15
 * @version 1.0
 */
abstract class BasePagedListAdapter<T : Any, VH : RecyclerView.ViewHolder> protected constructor(
    diffCallback: DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, VH>(diffCallback) {

    private val headers = SparseArray<View>()
    private val footers = SparseArray<View>()
    private var baseItemTypeHeader = 0x100000
    private var baseItemTypeFooter = 0x200000

    fun addHeaderView(view: View) {
        //判断给View对象是否还没有处在mHeaders数组里面
        if (headers.indexOfValue(view) < 0) {
            headers.put(baseItemTypeHeader++, view)
            notifyDataSetChanged()
        }
    }

    fun addFooterView(view: View) {
        //判断给View对象是否还没有处在mFooters数组里面
        if (footers.indexOfValue(view) < 0) {
            footers.put(baseItemTypeFooter++, view)
            notifyDataSetChanged()
        }
    }

    // 移除头部
    fun removeHeaderView(view: View) {
        val index = headers.indexOfValue(view)
        if (index < 0) return
        headers.removeAt(index)
        notifyDataSetChanged()
    }

    // 移除底部
    fun removeFooterView(view: View) {
        val index = footers.indexOfValue(view)
        if (index < 0) return
        footers.removeAt(index)
        notifyDataSetChanged()
    }

    protected val headerCount: Int
        get() = headers.size()

    protected val footerCount: Int
        get() = footers.size()

    override fun getItemCount(): Int {
        val itemCount = super.getItemCount()
        return itemCount + headers.size() + footers.size()
    }

    protected val originalItemCount: Int
        get() = itemCount - headers.size() - footers.size()

    override fun getItemViewType(position: Int): Int {
        var index = position
        if (isHeaderPosition(index)) { //返回该position对应的headerview的  viewType
            return headers.keyAt(index)
        }
        if (isFooterPosition(index)) { //footer类型的，需要计算一下它的position实际大小
            index = index - originalItemCount - headers.size()
            return footers.keyAt(index)
        }
        index -= headers.size()
        return getItemViewType2(index)
    }

    protected fun getItemViewType2(position: Int): Int {
        return 0
    }

    private fun isFooterPosition(position: Int): Boolean {
        return position >= originalItemCount + headers.size()
    }

    private fun isHeaderPosition(position: Int): Boolean {
        return position < headers.size()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        if (headers.indexOfKey(viewType) >= 0) {
            val view = headers[viewType]
            return object : RecyclerView.ViewHolder(view) {} as VH
        }
        if (footers.indexOfKey(viewType) >= 0) {
            val view = footers[viewType]
            return object : RecyclerView.ViewHolder(view) {} as VH
        }
        return onCreateViewHolder2(parent, viewType)
    }

    protected abstract fun onCreateViewHolder2(parent: ViewGroup, viewType: Int): VH

    protected abstract fun onBindViewHolder2(holder: VH, position: Int)

    override fun onBindViewHolder(holder: VH, position: Int) {
        var index = position
        if (isHeaderPosition(index) || isFooterPosition(index)) return
        //列表中正常类型的itemView的 position 咱们需要减去添加headerView的个数
        index -= headers.size()
        onBindViewHolder2(holder, index)
    }


    override fun onViewAttachedToWindow(holder: VH) {
        if (!isHeaderPosition(holder.adapterPosition) && !isFooterPosition(holder.adapterPosition)) {
            onViewAttachedToWindow2(holder as VH)
        }
    }

    protected fun onViewAttachedToWindow2(holder: VH) {}

    override fun onViewDetachedFromWindow(holder: VH) {
        if (!isHeaderPosition(holder.adapterPosition) && !isFooterPosition(holder.adapterPosition)) {
            onViewDetachedFromWindow2(holder as VH)
        }
    }

    protected fun onViewDetachedFromWindow2(holder: VH) {}

    override fun registerAdapterDataObserver(observer: AdapterDataObserver) {
        super.registerAdapterDataObserver(AdapterDataObserverProxy(observer))
    }

    //如果我们先添加了headerView,而后网络数据回来了再更新到列表上
    //由于Paging在计算列表上item的位置时 并不会顾及我们有没有添加headerView，就会出现列表定位的问题
    //实际上 RecyclerView#setAdapter方法，它会给Adapter注册了一个AdapterDataObserver
    //咱么可以代理registerAdapterDataObserver()传递进来的observer。在各个方法的实现中，把headerView的个数算上，再中转出去即可
    private inner class AdapterDataObserverProxy(private val mObserver: AdapterDataObserver) :
        AdapterDataObserver() {
        override fun onChanged() {
            mObserver.onChanged()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            mObserver.onItemRangeChanged(positionStart + headers.size(), itemCount)
        }

        override fun onItemRangeChanged(
            positionStart: Int,
            itemCount: Int,
            payload: Any?
        ) {
            mObserver.onItemRangeChanged(positionStart + headers.size(), itemCount, payload)
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            mObserver.onItemRangeInserted(positionStart + headers.size(), itemCount)
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            mObserver.onItemRangeRemoved(positionStart + headers.size(), itemCount)
        }

        override fun onItemRangeMoved(
            fromPosition: Int,
            toPosition: Int,
            itemCount: Int
        ) {
            mObserver.onItemRangeMoved(
                fromPosition + headers.size(),
                toPosition + headers.size(),
                itemCount
            )
        }

    }
}