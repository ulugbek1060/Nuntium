package com.example.nuntium.di.commponent

import com.example.nuntium.di.module.DatabaseModule
import com.example.nuntium.di.module.NetworkModule
import com.example.nuntium.ui.article.ArticleFragment
import com.example.nuntium.ui.blank.BlankFragment
import com.example.nuntium.ui.bookmark.BookmarkFragment
import com.example.nuntium.ui.category.CategoryFragment
import com.example.nuntium.ui.home.HomeFragment
import com.example.nuntium.ui.random.RandomFragment
import com.example.nuntium.ui.search.SearchFragment
import com.example.nuntium.ui.splash.SelectionCategoryFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class])
interface AppComponent {

    fun injectBlankFragment(fragment: BlankFragment)

    fun injectHomeFragment(fragment: HomeFragment)

    fun injectSelectFragment(fragment: SelectionCategoryFragment)

    fun injectCategoryFragment(fragment: CategoryFragment)

    fun injectRandomFragment(fragment: RandomFragment)

    fun injectBookmarkFragment(fragment: BookmarkFragment)

    fun injectArticleFragment(fragment: ArticleFragment)

    fun injectSearchFragment(fragment: SearchFragment)

}