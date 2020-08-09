package fr.skyle.capitodico.ui.cards

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import fr.openium.kotlintools.ext.appCompatActivity
import fr.openium.kotlintools.ext.getColorCompat
import fr.openium.kotlintools.ext.hideKeyboard
import fr.skyle.capitodico.R
import fr.skyle.capitodico.base.fragment.AbstractFragment
import fr.skyle.capitodico.enum.SortType
import fr.skyle.capitodico.event.eventFilterChanged
import fr.skyle.capitodico.ext.navigate
import fr.skyle.capitodico.ext.setNavigationIconColor
import fr.skyle.capitodico.ui.cards.adapter.AdapterCards
import fr.skyle.capitodico.utils.JsonUtils
import fr.skyle.capitodico.utils.PreferencesUtils
import kotlinx.android.synthetic.main.fragment_cards.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class FragmentCards : AbstractFragment(), SearchView.OnQueryTextListener {

    private lateinit var adapterCards: AdapterCards
    private lateinit var searchMenu: MenuItem

    private val prefUtils by inject<PreferencesUtils>()

    private var latestQueryString: String = ""

    // --- Life cycle
    // ---------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_cards, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupListeners()
        setupAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_cards, menu)

        // Init search function
        searchMenu = menu.findItem(R.id.menu_item_cards_search)
        val searchView = searchMenu.actionView as SearchView
        searchView.queryHint = getString(R.string.cards_query_hint)
        searchView.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.menu_item_cards_filter -> {
                navigate(FragmentCardsDirections.actionNavigationCardsToBottomSheetDialogCardFilter())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onQueryTextSubmit(query: String): Boolean {
        activity?.hideKeyboard()
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        latestQueryString = newText
        adapterCards.searchBy(latestQueryString)
        return true
    }

    // --- Methods
    // ---------------------------------------------------

    private fun setupToolbar() {
        val toolbar = toolbarCards as Toolbar
        toolbar.setNavigationIconColor(requireContext().getColorCompat(R.color.ca_text_light_primary))
        appCompatActivity.setSupportActionBar(toolbar)
        appCompatActivity.supportActionBar?.title = getString(R.string.cards_title)
    }

    private fun setupListeners() {
        lifecycleScope.launch {
            eventFilterChanged.asFlow().collect {
                (recyclerViewCards.adapter as AdapterCards).refreshData(getData(), latestQueryString)
            }
        }
    }

    private fun setupAdapter() {
        // Init once the adapter
        adapterCards = AdapterCards(getData(),
            onCardClicked = { cardName ->
                navigate(FragmentCardsDirections.actionNavigationCardsToFragmentCardDetail(cardName))
            }
        )

        recyclerViewCards.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterCards
        }
    }

    private fun getData(): MutableList<AdapterCards.Data> {
        val list = mutableListOf<AdapterCards.Data>()

        val cards = JsonUtils.cards
        val events = JsonUtils.events

        when (SortType.fromOrdinal(prefUtils.sortSelected)) {
            SortType.ALPHABETICAL -> cards.sortBy { it.name }
            SortType.ACTIVATION -> cards.sortBy { item -> events[events.keys.first { it == item.events.first() }]?.order }
            SortType.VALUE -> cards.sortByDescending { it.value }
        }

        cards.forEach {
            list.add(AdapterCards.Data(it))
        }

        return list
    }
}