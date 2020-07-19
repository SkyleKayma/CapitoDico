package fr.skyle.capitodico.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.skyle.capitodico.R
import fr.skyle.capitodico.base.fragment.AbstractFragment

class FragmentAbout : AbstractFragment() {

    // --- Life cycle
    // ---------------------------------------------------

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_about, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}