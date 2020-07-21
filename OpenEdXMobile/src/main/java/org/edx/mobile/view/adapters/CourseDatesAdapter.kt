package org.edx.mobile.view.adapters

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.edx.mobile.BR
import org.edx.mobile.R
import org.edx.mobile.databinding.ItemCourseDateBlockBinding
import org.edx.mobile.interfaces.OnDateBlockListener
import org.edx.mobile.model.course.CourseDateBlock
import org.edx.mobile.util.DateUtil
import org.edx.mobile.view.CourseDatesPageFragment
import java.util.*
import kotlin.collections.HashMap

class CourseDatesAdapter(private val data: HashMap<String, ArrayList<CourseDateBlock>>, private val keys: ArrayList<String>, private val onLinkClick: OnDateBlockListener) : RecyclerView.Adapter<CourseDatesAdapter.CourseDateHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseDatesAdapter.CourseDateHolder {
        val inflater = LayoutInflater.from(parent.context)
        val inflatedBinding = DataBindingUtil.inflate<ItemCourseDateBlockBinding>(inflater, R.layout.item_course_date_block, parent, false)
        return CourseDateHolder(inflatedBinding, onLinkClick)
    }

    override fun getItemCount(): Int {
        return keys.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: CourseDateHolder, position: Int) {
        when (position) {
            0 -> {
                holder.binding.aboveLineDot.visibility = View.INVISIBLE
                holder.binding.belowLineDot.visibility = View.VISIBLE
            }
            (itemCount - 1) -> {
                holder.binding.aboveLineDot.visibility = View.VISIBLE
                holder.binding.belowLineDot.visibility = View.INVISIBLE
            }
            else -> {
                holder.binding.aboveLineDot.visibility = View.VISIBLE
                holder.binding.belowLineDot.visibility = View.VISIBLE
            }
        }
        if (data.isNotEmpty()) {
            val key = keys[position]
            if (key.equals(CourseDatesPageFragment.getTodayDateBlock().getSimpleDateTime(), ignoreCase = true) && data[key].isNullOrEmpty()) {
                holder.bind(CourseDatesPageFragment.getTodayDateBlock(), arrayListOf())
            } else {
                holder.bind(data[key]?.first(), arrayListOf())
                holder.bind(data[key]?.first(), data[key])
            }
            holder.binding.root.setTag(getItemViewType(position))
        }
    }

    class CourseDateHolder(var binding: ItemCourseDateBlockBinding, private val onLinkClick: OnDateBlockListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CourseDateBlock?, list: ArrayList<CourseDateBlock>?) {
            binding.itemContainer.setBackgroundColor(Color.WHITE)
            binding.date.setTypeface(null, Typeface.BOLD)
            binding.dateTag.setTypeface(null, Typeface.BOLD_ITALIC)
            binding.setVariable(BR.dateType, item)
            binding.list = if (list?.isNotEmpty() == true) list else arrayListOf()
            binding.listener = onLinkClick
        }
    }
}
