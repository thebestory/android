/*
 * The Bestory Project
 */

package com.thebestory.android.fragment.main

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx2.Rx2Apollo

import com.thebestory.android.R
import com.thebestory.android.TheBestoryApplication
import com.thebestory.android.activity.MainActivity
import com.thebestory.android.adapter.main.NewTopicsAdapter
import com.thebestory.android.apollo.TopicsQuery
import com.thebestory.android.fragment.main.stories.SubmitStoryFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Fragment for Topics screen.
 * Use the [TopicsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TopicsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {


    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @return A new instance of fragment TopicsFragment.
         */
        fun newInstance(): TopicsFragment {
            return TopicsFragment()
        }
    }

    private var adapter: NewTopicsAdapter? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var rv: RecyclerView? = null
    private var errorTextView: TextView? = null
    private var progressView: ProgressBar? = null

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_main_topics, container, false)

        val toolbar = view!!.findViewById(R.id.main_topics_toolbar) as Toolbar

        toolbar.setTitle(R.string.navdrawer_main_topics)
        (activity as MainActivity).setSupportActionBar(toolbar)

        progressView = view.findViewById(R.id.progress) as ProgressBar
        errorTextView = view.findViewById(R.id.error_text) as TextView

        adapter = NewTopicsAdapter(activity, NewTopicsAdapter.OnClickListener { v, topic ->
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                    R.anim.enter_from_right,
                    R.anim.exit_to_left,
                    R.anim.enter_from_left,
                    R.anim.exit_to_right
            )

            (activity.application as TheBestoryApplication).
                    currentIdTopic.add(topic.fragments().topicFragment().id().toString())

            (activity.application as TheBestoryApplication).currentTitleTopic =
                    topic.fragments().topicFragment().title()


            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.main_frame_layout, StoriesFragment.newInstance())
            transaction.addToBackStack(null)
            // Commit the transaction
            transaction.commit()
        })

        rv = view.findViewById(R.id.rv_topics) as RecyclerView
        rv!!.layoutManager = LinearLayoutManager(activity)
        rv!!.adapter = adapter

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container) as SwipeRefreshLayout
        mSwipeRefreshLayout!!.setOnRefreshListener(this)
        mSwipeRefreshLayout!!.setColorSchemeColors(
                Color.RED, Color.GREEN, Color.BLUE, Color.CYAN)

        errorTextView!!.visibility = View.GONE
        rv!!.visibility = View.GONE


        getTopics()

        //TODO: Cache

        return view
    }

    private fun getTopics() {
        val topicsQueryCall: ApolloCall<TopicsQuery.Data> = TheBestoryApplication.getApolloClient()
                .query(TopicsQuery(20, null, null))


        val disObsForTopicsQuery = getDisObsForTopicsQuery()

        Rx2Apollo.from(topicsQueryCall)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disObsForTopicsQuery)

        compositeDisposable.add(disObsForTopicsQuery)
    }

    private fun getDisObsForTopicsQuery(): DisposableObserver<Response<TopicsQuery.Data>> {
        return object : DisposableObserver<Response<TopicsQuery.Data>>() {
            override fun onNext(t: Response<TopicsQuery.Data>) {
                adapter?.addTopics(t.data()?.topics())
                progressView!!.visibility = View.GONE
                errorTextView!!.visibility = View.GONE
                rv!!.visibility = View.VISIBLE
            }

            override fun onComplete() {
                //TODO
            }

            override fun onError(e: Throwable) {
                Log.e("TopicsFragment", e.message, e)
            }

        }
    }

    override fun onRefresh() {
        getTopics()
        mSwipeRefreshLayout!!.isRefreshing = false
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.main_topics, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val transaction = activity!!.supportFragmentManager.beginTransaction()

        when (item!!.itemId) {
            R.id.main_stories_toolbar_action_new -> transaction.replace(R.id.main_frame_layout, SubmitStoryFragment.newInstance())
        }

        transaction.addToBackStack(null).commit()
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}
