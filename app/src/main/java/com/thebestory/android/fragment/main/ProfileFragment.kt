package com.thebestory.android.fragment.main

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import com.thebestory.android.R
import com.thebestory.android.TheBestoryApplication
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import android.widget.Toast
import com.thebestory.android.activity.MainActivity
import com.thebestory.android.fragment.main.stories.SubmitStoryFragment
import com.thebestory.android.model.AuthModel

/**
 * Created by oktai on 04.08.17.
 */

class ProfileFragment : Fragment() {

    var textView: TextView? = null
    var logOutButton: Button? = null
    var signInButton: Button? = null
    var toolbar: Toolbar? = null

    private val REQUEST_TOKEN = 1
    private val compositeDisposable = CompositeDisposable()

    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.main_profile, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val transaction = activity.supportFragmentManager.beginTransaction()

        when (item!!.itemId) {
            R.id.main_profile_toolbar_action_new ->
                transaction.replace(R.id.main_frame_layout, SubmitStoryFragment.newInstance())
        }

        transaction.addToBackStack(null).commit()
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_main_profile, container, false)

        setUpFindsAndToolbar(view)
        setUpClickListeners()

        if (!TheBestoryApplication.sharedPrefs.contains("token")) {
            textView?.visibility = View.VISIBLE
            textView?.text = "Here will be your personal data, if you register account"
            signInButton?.visibility = View.VISIBLE
            createLoginDialog()
        } else {
            showInterface()
        }

        return view
    }

    private fun setUpFindsAndToolbar(view: View) {
        logOutButton = view.findViewById(R.id.main_profile_log_out) as Button
        textView = view.findViewById(R.id.main_profile_title) as TextView
        signInButton = view.findViewById(R.id.main_profile_sign_in) as Button
        toolbar = view.findViewById(R.id.main_profile_toolbar) as Toolbar
        toolbar?.title = "Profile"
        (activity as MainActivity).setSupportActionBar(toolbar)
    }

    private fun setUpClickListeners() {
        logOutButton?.setOnClickListener {
            val editor: SharedPreferences.Editor = TheBestoryApplication.sharedPrefs.edit()
            editor.remove("token")
            editor.remove("username")
            editor.apply()
            Handler().post { activity.onBackPressed() }
            Toast.makeText(activity, "You have left your account", Toast.LENGTH_SHORT).show()
        }

        signInButton?.setOnClickListener {
            createLoginDialog()
        }
    }

    private fun showInterface() {
        signInButton?.visibility = View.GONE
        logOutButton?.visibility = View.VISIBLE
        textView?.visibility = View.VISIBLE
        val str = "Hello, " + TheBestoryApplication.sharedPrefs.getString("username", "%USERNAME")
        textView?.text = str
    }

    private fun createLoginDialog() {
        val loginDialogFragment = LoginDialogFragment.newInstance()
        loginDialogFragment.setTargetFragment(this, REQUEST_TOKEN)
        loginDialogFragment.show(fragmentManager, loginDialogFragment.javaClass.name)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_TOKEN -> {

                    val hashMap = HashMap<String, String>()

                    data?.getStringExtra("username")?.let { hashMap.put("username", it) }
                    data?.getStringExtra("password")?.let { hashMap.put("password", it) }

                    TheBestoryApplication
                            .getNoTokenTheBestoryAPI()
                            .postAuth(hashMap)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(getAuthObserver())
                            ?.let {
                                compositeDisposable.add(it)
                            }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        /*if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()                   //TODO: Need?
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    private fun getAuthObserver(): DisposableSingleObserver<AuthModel> {
        return object : DisposableSingleObserver<AuthModel>() {
            override fun onSuccess(t: AuthModel) {

                val editor: SharedPreferences.Editor = TheBestoryApplication.sharedPrefs.edit()

                editor.putString("token", t.jwt)
                editor.putString("username", t.user?.username)
                editor.apply()

                showInterface()
            }

            override fun onError(e: Throwable) {
                Toast.makeText(activity, "Can not sign in", Toast.LENGTH_SHORT).show()
                createLoginDialog()
            }
        }
    }

}