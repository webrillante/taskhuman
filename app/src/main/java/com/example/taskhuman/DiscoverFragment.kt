package com.example.taskhuman

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskhuman.adapter.Adapter
import com.example.taskhuman.data.DataModel
import com.example.taskhuman.data.FavouriteInput
import com.example.taskhuman.databinding.FragmentDiscoverBinding
import com.example.taskhuman.utils.Status

class DiscoverFragment : Fragment(), Adapter.FavouriteListener {
    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BaseActivityViewModel by activityViewModels()

    private val adapter = Adapter()
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        initialize()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.getPhysicalFitnessData().observe(requireActivity(), Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        setData(it.data)
                        it.data?.skills?.let { it1 -> adapter.reload(it1) }
                    }
                    Status.ERROR -> {
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }

    private fun setData(data: DataModel?) {
        binding.rvHeading.text = data?.topicHeader?.tileName
    }

    private fun initialize() {

        val itemDecoration = BottomMarginItemDecoration(12)
        binding.recyclerView.addItemDecoration(itemDecoration)
        layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        adapter.setListener(this)
        binding.recyclerView.adapter = adapter


        setItemTouchHelper()
    }

    private fun setItemTouchHelper() {
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            private val limitScrollX = dipToPx(100f, requireContext())
            private var currentScrollX = 0
            private var currentScrollXWhenInActive = 0

            private var initXWhenInActive = 0f
            private var firstInActive = false

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = 0
                val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                return makeMovementFlags(dragFlags, swipeFlags)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            }

            override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
                return Integer.MAX_VALUE.toFloat()
            }

            override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
                return Integer.MAX_VALUE.toFloat()
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    if(dX == 0f) {
                        currentScrollX = viewHolder.itemView.scrollX
                        firstInActive = true
                    }

                    if(isCurrentlyActive) {
                        var scrollOffset  = currentScrollX + (-dX).toInt()
                        if(scrollOffset > limitScrollX) {
                            scrollOffset = limitScrollX
                        }
                        else if(scrollOffset < 0) {
                            scrollOffset = 0
                        }
                        viewHolder.itemView.scrollTo(scrollOffset, 0)
                    }
                } else {
                    if(firstInActive) {
                        firstInActive = false
                        currentScrollXWhenInActive = viewHolder.itemView.scrollX
                        initXWhenInActive = dX
                    }

                    if(viewHolder.itemView.scrollX < limitScrollX) {
                        viewHolder.itemView.scrollTo((currentScrollXWhenInActive * dX / initXWhenInActive).toInt(), 0)
                    }
                }
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)

                if(viewHolder.itemView.scrollX > limitScrollX) {
                    viewHolder.itemView.scrollTo(limitScrollX, 0)
                } else if( viewHolder.itemView.scrollX < 0) {
                    viewHolder.itemView.scrollTo(0,0)
                }
            }

        }).apply {
            attachToRecyclerView(binding.recyclerView)
        }
    }

    private fun dipToPx(dipValue: Float, context: Context): Int {
        return (dipValue * context.resources.displayMetrics.density).toInt()
    }

    inner class BottomMarginItemDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)

            val position = parent.getChildAdapterPosition(view)
            val itemCount = parent.adapter?.itemCount ?: 0

            if (position < itemCount - 1) {
                outRect.bottom = margin
            }
        }
    }

    override fun addFavourite(tileName: String, dictionaryName: String?, position: Int) {
        viewModel.addFavourite(FavouriteInput(tileName, dictionaryName)).observe(requireActivity(), Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        adapter.setAddedFavourite(resource.data, position)
                    }
                    Status.ERROR -> {
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }

    override fun removeFavourite(tileName: String, position: Int) {
        viewModel.removeFavourite(FavouriteInput(tileName, null)).observe(requireActivity(), Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        adapter.setRemoveFavourite(resource.data, position)
                    }
                    Status.ERROR -> {
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }
}