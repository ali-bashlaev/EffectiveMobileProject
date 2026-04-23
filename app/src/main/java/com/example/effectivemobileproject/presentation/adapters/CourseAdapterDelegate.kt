package com.example.effectivemobileproject.presentation.adapters

import com.example.effectivemobileproject.R
import com.example.domain.model.Course
import com.example.effectivemobileproject.databinding.ItemCourseBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import java.text.SimpleDateFormat
import java.util.Locale


fun courseAdapterDelegate(
    onFavoriteClick: (Course) -> Unit
) = adapterDelegateViewBinding<Course, Any, ItemCourseBinding>(
    { layoutInflater, parent -> ItemCourseBinding.inflate(layoutInflater, parent,
        false) }
) {
    
    binding.courseFavoriteBlurContainer.clipToOutline = true
    binding.courseRatingBlurContainer.clipToOutline = true
    binding.courseDateBlurContainer.clipToOutline = true

    bind {
        binding.courseTitleText.text = item.title
        binding.courseDescriptionText.text = item.text
        binding.coursePriceText.text = if (
            item.price.contains("₽")
            ) item.price else "${item.price} ₽"
        binding.courseRatingText.text = item.rate
        binding.coursePublicationDate.text = formatDate(item.startDate)

        val imageRes = mapTitleToImage(item.title)
        binding.courseImage.setImageResource(imageRes)

        val favoriteIcon = if (item.hasLike) {
            R.drawable.ic_favorites_selected
        } else {
            R.drawable.ic_favorites_unselected
        }
        binding.courseButtonFavorite.setImageResource(favoriteIcon)

        binding.courseButtonFavorite.setOnClickListener {
            onFavoriteClick(item)
        }
    }
}

private fun formatDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val russianLocale = Locale.forLanguageTag("ru")
        val outputFormat = SimpleDateFormat("d MMMM yyyy", russianLocale)
        val date = inputFormat.parse(dateString)
        if (date != null) {
            outputFormat.format(date).replaceFirstChar { it.uppercase() }
        } else {
            dateString
        }
    } catch (_: Exception) {
        dateString
    }
}

private fun mapTitleToImage(title: String): Int {
    val lowerTitle = title.lowercase()
    return when {
        lowerTitle.contains(
            "python") || lowerTitle.contains("kotlin"
            ) -> R.drawable.python_advanced_cover
        lowerTitle.contains(
            "3d") || lowerTitle.contains("моделирование"
            ) -> R.drawable.model_generalist_cover
        else -> R.drawable.img_course_placeholder
    }
}
