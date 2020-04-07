package dev.ahmdaeyz.pinster.ui.newsstory

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.ahmdaeyz.pinster.R

class NewsStoryFragment : Fragment() {

    companion object {
        fun newInstance() = NewsStoryFragment()
    }

    private lateinit var viewModel: NewsStoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_story_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewsStoryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}