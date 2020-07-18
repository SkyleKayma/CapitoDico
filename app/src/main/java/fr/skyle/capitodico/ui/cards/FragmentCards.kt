package fr.skyle.capitodico.ui.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import fr.skyle.capitodico.R
import fr.skyle.capitodico.base.fragment.AbstractFragment
import fr.skyle.capitodico.ui.cards.adapter.AdapterCards
import fr.skyle.capitodico.utils.JsonUtils
import kotlinx.android.synthetic.main.fragment_cards.*

class FragmentCards : AbstractFragment() {

    private lateinit var adapterCards: AdapterCards

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_cards, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupAdapter()
    }

    private fun setupListeners() {

    }

    private fun setupAdapter() {
        // Init once the adapter
        adapterCards = AdapterCards(getData(),
            onCardClicked = { cardName ->
//                val dialog = DialogMainCardDetail.getInstance(cardName)
//                dialog.show(parentFragmentManager, "cardDetail")
            }
        )

        recyclerViewCards.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterCards
        }
    }

    private fun getData(): List<AdapterCards.Data> {
        val list = mutableListOf<AdapterCards.Data>()

        JsonUtils.cards.sortedBy { it.name }.forEach {
            list.add(AdapterCards.Data(it))
        }

        return list
    }
}