package dev.ahmdaeyz.pinster.ui.launcher.prefrerredcategories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.ahmdaeyz.pinster.databinding.CategoryBinding
import io.reactivex.subjects.PublishSubject

class CategoriesGridAdapter(
        private val bindableCategories: List<BindableCategory>,
        private val categoriesSubject: PublishSubject<String>
)
    : RecyclerView.Adapter<CategoriesGridAdapter.ViewHolder>(){

    class ViewHolder(private val binding: CategoryBinding, private val categoriesSubject: PublishSubject<String>)
        : RecyclerView.ViewHolder(binding.root) {

        companion object{
            fun from(parent: ViewGroup,categoriesSubject: PublishSubject<String>): ViewHolder{
                val binding = CategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                return ViewHolder(binding,categoriesSubject)
            }
        }

        fun bind(bindableCategory: BindableCategory){
            val (categoryTitle, categoryImageResourceId ,categorySelectedImageResourceId) = bindableCategory
            binding.categoryText.text = categoryTitle
            binding.categoryImage.setImageResource(categoryImageResourceId)
            binding.categoryImage.tag = categoryImageResourceId
            binding.root.setOnClickListener{
                if (binding.categoryImage.tag == categoryImageResourceId){
                    binding.categoryImage.setImageResource(categorySelectedImageResourceId)
                    binding.categoryImage.tag = categorySelectedImageResourceId
                    categoriesSubject.onNext(categoryTitle)
                }else{
                    binding.categoryImage.setImageResource(categoryImageResourceId)
                    binding.categoryImage.tag = categoryImageResourceId
                    categoriesSubject.onNext("$categoryTitle!")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, categoriesSubject)
    }

    override fun getItemCount(): Int = bindableCategories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = bindableCategories[position]
        holder.bind(item)
    }
}
