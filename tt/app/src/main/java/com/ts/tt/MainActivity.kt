package com.ts.tt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TestSelectionFragment : Fragment() {
    private lateinit var adapter: LocalAdapter
    private var tracker: SelectionTracker<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.testselection, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // might be necessary under certain circumstances when using Jetpack Navigation
        // tracker = null

        initSelection()
        initList()
    }

    private fun initSelection() {
        selectionToolbar.apply {
            inflateMenu(R.menu.selection)
            setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.action_add_to -> {
                        // TODO: do something
                        true
                    }
                    R.id.action_delete -> {
                        // TODO: do something
                        true
                    }
                    else -> false
                }
            }

            // use navigationIcon as close icon
            navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_close_24)
            setNavigationOnClickListener {
                tracker?.clearSelection()
            }
        }
    }

    private fun initList() {
        val layoutManager = GridLayoutManager(context, 3)
        list.layoutManager = layoutManager

        if (!::adapter.isInitialized) {
            adapter = LocalAdapter()
            // adapter.setHasStableIds(true)
        }

        list.adapter = adapter

        // must set adapter to list before SelectionTracker.Builder, else IllegalArgumentException
        val tracker = this.tracker ?: run {
            Timber.d("set tracker")
            SelectionTracker.Builder<String>(
                "photo-selection",
                list,
                // StableIdKeyProvider(list),
                LocalItemKeyProvider(adapter),
                LocalItemDetailsLookup(list),
                // StorageStrategy.createLongStorage(),
                StorageStrategy.createStringStorage()
            ).withSelectionPredicate(
                // SelectionPredicates.createSelectAnything()
                LocalSelectionPredicate(adapter)
            ).build().also {
                this.tracker = it
            }
        }
        adapter.tracker = tracker
        selectionToolbar.isVisible = tracker.hasSelection()

        // var lastHasSelection = false
        tracker.addObserver(object: SelectionTracker.SelectionObserver<Long>() {
            var lastHasSelection = false

            override fun onSelectionChanged() {
                super.onSelectionChanged()

                // this might not be realiable under certain circumstances
                // val selectionStateChanged = selectionToolbar.isVisible != tracker.hasSelection()
                val selectionStateChanged = lastHasSelection != tracker.hasSelection()
                lastHasSelection = tracker.hasSelection()

                selectionToolbar.isVisible = tracker.hasSelection()
                selectionToolbar.title = tracker.selection.size().toString()

                // selection state changed
                // if (lastHasSelection != tracker.hasSelection()) {
                if (selectionStateChanged) {
                    adapter.notifySelectableItemsChanged()
                }

                // lastHasSelection = tracker.hasSelection()
            }
        })


        val items = mutableListOf<ViewItem>()
        for (number in 0..10) {

            if (number % 2 == 0) {
                val key = "number-$number"
                items.add(ViewItem.NumberItem(key))
            }
            else {
                val key = "noselect-$number"
                items.add(ViewItem.NotSelectable(key))
            }
        }
        adapter.submitList(items)
    }

    private class LocalItemDetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<String>() {
        override fun getItemDetails(event: MotionEvent): ItemDetails<String>? {
            val view = recyclerView.findChildViewUnder(event.x, event.y)
            if (view != null) {
                return (recyclerView.getChildViewHolder(view) as LocalAdapter.ViewHolder)
                    .getItemDetails()
            }
            return null
        }
    }

    private class LocalSelectionPredicate(private val adapter: LocalAdapter): SelectionTracker.SelectionPredicate<String>() {
        override fun canSelectMultiple(): Boolean {
            return true
        }

        override fun canSetStateForKey(key: String, nextState: Boolean): Boolean {
            val item = adapter.currentList.find { it.id == key }
            return item is ViewItem.NumberItem
        }

        override fun canSetStateAtPosition(position: Int, nextState: Boolean): Boolean {
            val item = adapter.currentList[position]
            return item is ViewItem.NumberItem
        }

    }

    private class LocalItemKeyProvider(private val adapter: LocalAdapter) : ItemKeyProvider<String>(ItemKeyProvider.SCOPE_MAPPED) {
        override fun getKey(position: Int): String {
            return adapter.currentList[position].id
        }

        override fun getPosition(key: String): Int {
            //  RecyclerView.NO_POSITION = -1
            return adapter.currentList.indexOfFirst { it.id == key }
        }
    }

    sealed class ViewItem(open val id: String, val resource: Int) {
        data class NumberItem(override val id: String) : ViewItem(id, R.layout.testselection_item)
        data class NotSelectable(override val id: String) : ViewItem(id, R.layout.testselection_static_item)
    }

    class LocalAdapter() : ListAdapter<ViewItem, LocalAdapter.ViewHolder>(DiffCallback()) {
        lateinit var tracker: SelectionTracker<String>

        // override fun getItemId(position: Int): Long = position.toLong()

        override fun getItemViewType(position: Int): Int {
            return getItem(position).resource
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = getItem(position)
            Timber.d("position=$position, selected=${tracker.isSelected(item.id)}, selection=${tracker.selection}")
            holder.bind(getItem(position), tracker.isSelected(item.id))
        }

        fun notifySelectableItemsChanged() {
            for (index in 0 until itemCount) {
                val item = getItem(index)
                if (item is ViewItem.NumberItem)
                    notifyItemChanged(index)
            }

        }

        private class DiffCallback: DiffUtil.ItemCallback<ViewItem>() {
            override fun areItemsTheSame(oldItem: ViewItem, newItem: ViewItem): Boolean {
                if (oldItem.resource != newItem.resource) return false
                // check if id is the same
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: ViewItem, newItem: ViewItem): Boolean {
                // check if content is the same
                // equals using data class
                return oldItem == newItem
            }
        }

        inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer {

            // why update ui here? easier to access view without need to holder.titleTextView
            fun bind(item: ViewItem, isSelected: Boolean) {
                when(item) {
                    is ViewItem.NumberItem -> {
                        numberTextView.text = item.id
                        val res = if (isSelected) R.drawable.ic_baseline_check_circle_24 else R.drawable.ic_baseline_radio_button_unchecked_24
                        selectionImageView.setImageResource(res)
                        selectionImageView.isVisible = tracker.hasSelection()
                    }
                }
            }

            fun getItemDetails(): ItemDetailsLookup.ItemDetails<String> =
                object : ItemDetailsLookup.ItemDetails<String>() {
                    override fun getPosition(): Int = adapterPosition
                    override fun getSelectionKey(): String? = getItem(adapterPosition).id
                }
        }
    }
}