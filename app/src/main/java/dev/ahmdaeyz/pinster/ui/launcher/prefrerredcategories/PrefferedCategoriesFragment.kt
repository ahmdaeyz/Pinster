package dev.ahmdaeyz.pinster.ui.launcher.prefrerredcategories

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.ahmdaeyz.pinster.R

class PrefferedCategoriesFragment : Fragment() {

    companion object {
        fun newInstance() = PrefferedCategoriesFragment()
    }

    private lateinit var viewModel: PrefferedCategoriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.preffered_categories_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PrefferedCategoriesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}