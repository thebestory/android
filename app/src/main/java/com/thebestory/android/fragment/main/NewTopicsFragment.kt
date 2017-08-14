/*
 * The Bestory Project
 */

package com.thebestory.android.fragment.main

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
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
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx2.Rx2Apollo

import com.thebestory.android.R
import com.thebestory.android.TheBestoryApplication
import com.thebestory.android.activity.MainActivity
import com.thebestory.android.adapter.main.TopicsAdapter
import com.thebestory.android.api.ApiMethods
import com.thebestory.android.api.LoaderResult
import com.thebestory.android.api.LoaderStatus
import com.thebestory.android.apollo.TopicsQuery
import com.thebestory.android.fragment.main.stories.SubmitStoryFragment
import com.thebestory.android.model.Topic
import com.thebestory.android.util.BankTopics
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

/**
 * Fragment for Topics screen.
 * Use the [TopicsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewTopicsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {


    override fun onRefresh() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    val thisFragment = this

    private var adapter: TopicsAdapter? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var rv: RecyclerView? = null
    private var errorTextView: TextView? = null
    private var progressView: ProgressBar? = null

    private var visitOnCreateLoader: Boolean = false

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

        adapter = TopicsAdapter(activity, TopicsAdapter.OnClickListener { v, topic ->
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                    R.anim.enter_from_right,
                    R.anim.exit_to_left,
                    R.anim.enter_from_left,
                    R.anim.exit_to_right
            ) //TODO

            //(activity!!.application as TheBestoryApplication).currentTopic = topic
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

        /*if (savedInstanceState != null && savedInstanceState.containsKey("visit")) {
            visitOnCreateLoader = savedInstanceState.getBoolean("visit")
            displayNonEmptyData()
        } else {
            if (BankTopics.getInstance().count == 0) {
                loaderManager.restartLoader(0, null, this)
            } else {
                displayNonEmptyData(true)
            }//TODO
        }*/

        return view
    }

    private fun getTopics() {


        val topicsQueryCall: ApolloCall<TopicsQuery.Data> = TheBestoryApplication.getApolloClient()
                .query(TopicsQuery(4, null, null))


        //compositeDisposable.add(Rx2Apollo.from(topicsQueryCall)
        //        .subscribeOn(Schedulers.io())
        //        .observeOn(AndroidSchedulers.mainThread())
        //        .subscribeWith(DisposableSingleObserver<Response<TopicsQuery.Data>>() {
        //        }))
    }

    /*override fun onRefresh() {
        if (BankTopics.getInstance().count != 0) {
            displayNonEmptyData(true)
        } else {
            loaderManager.restartLoader(0, null, thisFragment)
        }//TODO
        mSwipeRefreshLayout!!.isRefreshing = false
    }

    override fun onCreateLoader(id: Int, args: Bundle): Loader<LoaderResult<List<Topic>>> {
        Log.w("onCreateLoader", "Loading...")
        val temp: Loader<LoaderResult<List<Topic>>>
        temp = ApiMethods.getInstance().getTopicsList(activity)
        visitOnCreateLoader = true
        return temp
    }

    override fun onLoadFinished(loader: Loader<LoaderResult<List<Topic>>>, result: LoaderResult<List<Topic>>) {
        when (result.status) {
            LoaderStatus.OK -> {
                Log.w("onFinished", "OK")
                if (!result.data.isEmpty()) {
                    if (visitOnCreateLoader) {
                        BankTopics.getInstance().loadAndUpdateTopics(result.data) //TODO
                        displayNonEmptyData()
                    } else {
                        displayNonEmptyData(true)
                    }
                }
            }
            LoaderStatus.ERROR -> {
                displayError(result.status)
            }
            LoaderStatus.WARNING -> {
            }//TODO: Try to write this)))
        }
        visitOnCreateLoader = false
    }


    override fun onLoaderReset(loader: Loader<LoaderResult<List<Topic>>>) {
        displayEmptyData()
    }

    private fun displayEmptyData() {
        progressView!!.visibility = View.GONE
        rv!!.visibility = View.GONE
        errorTextView!!.visibility = View.VISIBLE
        errorTextView!!.setText(R.string.topics_not_found)
    }

    private fun displayNonEmptyData(flag: Boolean) {
        progressView!!.visibility = View.GONE
        errorTextView!!.visibility = View.GONE
        rv!!.visibility = View.VISIBLE
    }

    private fun displayNonEmptyData() {
        if (adapter != null) {
            adapter!!.addTopics(BankTopics.getInstance().list) //TODO
        }
        progressView!!.visibility = View.GONE
        errorTextView!!.visibility = View.GONE
        rv!!.visibility = View.VISIBLE
    }

    private fun displayError(resultType: LoaderStatus) {
        if ((if (adapter != null) adapter!!.itemCount else 0) == 0) {
            progressView!!.visibility = View.GONE
            rv!!.visibility = View.GONE
            errorTextView!!.visibility = View.VISIBLE
            val messageResId: Int
            if (resultType == LoaderStatus.ERROR) { //TODO: Add in LoaderStatus NO_INTERNET
                messageResId = R.string.no_internet
            } else {
                messageResId = R.string.error
            }
            errorTextView!!.setText(messageResId)
        } else {
            Snackbar.make(
                    activity.findViewById(R.id.main_stories_layout),
                    R.string.no_internet,
                    Snackbar.LENGTH_LONG
            ).show()
        }
    }*/

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

    /*override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putBoolean("visit", visitOnCreateLoader)
    }*/

    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @return A new instance of fragment NewTopicsFragment.
         */
        fun newInstance(): NewTopicsFragment {
            return NewTopicsFragment()
        }
    }
}// Required empty public constructor
