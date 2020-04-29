package dev.ahmdaeyz.pinster.ui.launcher.prefrerredcategories

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import dev.ahmdaeyz.pinster.R
import dev.ahmdaeyz.pinster.databinding.PrefferedCategoriesFragmentBinding
import kotlinx.android.synthetic.main.preffered_categories_fragment.*
import org.koin.android.ext.android.inject

class PreferredCategoriesFragment : Fragment() {
    private  val viewModelFactory: PreferredCategoriesViewModelFactory by inject()
    private lateinit var viewModel: PreferredCategoriesViewModel
    private lateinit var binding: PrefferedCategoriesFragmentBinding
    private val firebaseAuth: FirebaseAuth by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory).get(PreferredCategoriesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PrefferedCategoriesFragmentBinding.inflate(inflater,container,false)
        val bindableCategories = getBindableCategories()
        val gridAdapter = CategoriesGridAdapter(bindableCategories,viewModel.categoriesSubject)
        val layoutManager = GridLayoutManager(
                this.context,
                3
        )
        //TODO("handle name extraction gracefully")
        val userName = firebaseAuth.currentUser?.displayName?.split(" ")?.get(0)
        binding.userName = userName
        binding.categoriesGrid.adapter = gridAdapter
        binding.categoriesGrid.layoutManager = layoutManager
        viewModel.getStarted.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(PreferredCategoriesFragmentDirections.actionPreferredCategoriesFragmentToHomeFragment())
        })
        return binding.root
    }

    private fun getBindableCategories() :List<BindableCategory>{
        val categoryTitles = listOf<String>(*resources.getStringArray(R.array.categories_names))
        val imagesResIds = listOf(R.drawable.ic_masks, R.drawable.ic_chip, R.drawable.ic_steering_wheel, R.drawable.ic_recycle_sign, R.drawable.ic_mortarboard, R.drawable.ic_dress, R.drawable.ic_coins, R.drawable.ic_dish, R.drawable.ic_film, R.drawable.ic_strategy, R.drawable.ic_map_of_roads, R.drawable.ic_physics)
        val imagesSelectedResIds = listOf(R.drawable.ic_masks_selected, R.drawable.ic_chip_selected, R.drawable.ic_steering_wheel_selected, R.drawable.ic_recycle_sign_selected, R.drawable.ic_mortarboard_selected, R.drawable.ic_dress_selected, R.drawable.ic_coins_selected, R.drawable.ic_dish_selected, R.drawable.ic_film_selected, R.drawable.ic_strategy_selected, R.drawable.ic_map_of_roads_selected, R.drawable.ic_physics_selected)
        return categoryTitles.mapIndexed { index, title ->
            BindableCategory(title, imagesResIds[index], imagesSelectedResIds[index])
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        get_started_button.setOnClickListener {
            viewModel.tappedGetStarted()
            chosePreferredCategories()
        }
    }

    private fun chosePreferredCategories() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putBoolean(getString(R.string.has_chose_categories), true)
            commit()
        }
    }
}