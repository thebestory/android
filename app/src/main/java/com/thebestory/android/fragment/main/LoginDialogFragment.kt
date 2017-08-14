package com.thebestory.android.fragment.main

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.support.v4.app.DialogFragment
import android.widget.EditText
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.thebestory.android.R
import com.thebestory.android.TheBestoryApplication
import com.thebestory.android.model.Account
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by oktai on 07.08.17.
 */

class LoginDialogFragment : DialogFragment() {

    companion object {
        fun newInstance(): LoginDialogFragment {
            return LoginDialogFragment()
        }
    }

    private val compositeDisposable = CompositeDisposable()

    var loginEditText: EditText? = null
    var passwordEditText: EditText? = null
    var signUpButton: Button? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = activity.layoutInflater.inflate(R.layout.fragment_main_login, null)

        setUpFinds(view)
        setUpClickListeners()

        val builder = AlertDialog.Builder(activity)
                .setView(view)
                .setTitle("Sign in")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Sign in", { dialog, which ->
                    val intent = Intent()
                    intent.putExtra("username", loginEditText?.text.toString())
                    intent.putExtra("password", passwordEditText?.text.toString())
                    targetFragment.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
                })

        return builder.create()
    }

    private fun setUpClickListeners() {
        signUpButton?.setOnClickListener {
            createRegistrationDialog(Bundle())
        }
    }

    private fun setUpFinds(view: View) {
        loginEditText = view.findViewById(R.id.main_login_login_edittext) as EditText
        passwordEditText = view.findViewById(R.id.main_login_password_edittext) as EditText
        signUpButton = view.findViewById(R.id.main_login_sign_up) as Button
    }

    private fun createRegistrationDialog(data: Bundle) {
        val registerDialogFragment = RegistrationDialogFragment.newInstance()
        registerDialogFragment.setTargetFragment(this, RegistrationDialogFragment.Companion.REGISTER)
        if (!data.isEmpty) {
            registerDialogFragment.arguments = data
        }
        registerDialogFragment.show(fragmentManager, registerDialogFragment.javaClass.name)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RegistrationDialogFragment.Companion.REGISTER -> {

                    val hashMap = HashMap<String, String>()
                    val bundle = Bundle()


                    data?.getStringExtra("username")?.let {
                        hashMap.put("username", it)
                        bundle.putString("username", it)
                    }
                    data?.getStringExtra("email")?.let {
                        hashMap.put("email", it)
                        bundle.putString("email", it)
                    }
                    data?.getStringExtra("password")?.let {
                        hashMap.put("password", it)
                        bundle.putString("password", it)
                    }


                    TheBestoryApplication
                            .getNoTokenTheBestoryAPI()
                            .postRegister(hashMap)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(getRegisterObserver(bundle))
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

    private fun getRegisterObserver(data: Bundle): DisposableSingleObserver<Account> {
        return object : DisposableSingleObserver<Account>() {
            override fun onSuccess(t: Account) {
                Toast.makeText(activity, "You have successfully signed up", Toast.LENGTH_SHORT).show()
            }

            override fun onError(e: Throwable) {
                Toast.makeText(activity, "Can not sign up", Toast.LENGTH_SHORT).show()
                createRegistrationDialog(data)
            }
        }
    }

}