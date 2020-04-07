package dev.ahmdaeyz.pinster.ui.newsreader

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.ahmdaeyz.pinster.R

class NewsReaderFragment : Fragment() {

    companion object {
        fun newInstance() = NewsReaderFragment()
    }

    private lateinit var viewModel: NewsReaderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_reader_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewsReaderViewModel::class.java)
        // TODO: Use the ViewModel
    }

}