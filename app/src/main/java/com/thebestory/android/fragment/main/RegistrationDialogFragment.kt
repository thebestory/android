package com.thebestory.android.fragment.main

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.thebestory.android.R

/**
 * Created by oktai on 04.08.17.
 */

class RegistrationDialogFragment : DialogFragment() {

    companion object {

        val REGISTER = 1

        fun newInstance(username: String,
                        email: String,
                        password: String,
                        passwordAgain: String): RegistrationDialogFragment {

            val regFragment = RegistrationDialogFragment()
            val bundle = Bundle()
            bundle.putString("username", username)
            bundle.putString("email", email)
            bundle.putString("password", password)
            bundle.putString("passwordAgain", passwordAgain)
            regFragment.arguments = bundle
            return regFragment
        }

        fun newInstance(): RegistrationDialogFragment {
            return RegistrationDialogFragment()
        }

    }

    private var loginEditText: EditText? = null
    private var emailEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var passwordAgainEditText: EditText? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity.layoutInflater.inflate(R.layout.fragment_main_registration, null)

        setUpFinds(view)
        if (arguments != null && !arguments.isEmpty) {
            loginEditText?.setText(arguments.getString("username"))
            emailEditText?.setText(arguments.getString("email"))
            passwordEditText?.setText(arguments.getString("password"))
            passwordAgainEditText?.setText(arguments.getString("passwordAgain"))
        }


        val builder = AlertDialog.Builder(activity)
                .setView(view)
                .setTitle("Sign up")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Sign up", { dialog, which ->

                    val loginExtra = loginEditText?.text.toString()
                    val emailExtra = emailEditText?.text.toString()
                    val passExtra = passwordEditText?.text.toString()
                    val passAgainExtra = passwordAgainEditText?.text.toString()

                    Log.d("Register", passExtra)
                    Log.d("Register", passAgainExtra)

                    if (passExtra == passAgainExtra) {
                        val intent = Intent()
                        intent.putExtra("username", loginExtra)
                        intent.putExtra("email", emailExtra)
                        intent.putExtra("password", passExtra)
                        targetFragment.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
                    } else {
                        Toast.makeText(activity, "Your passwords don't match", Toast.LENGTH_SHORT).show()

                        val registerDialogFragment = RegistrationDialogFragment
                                .newInstance(loginExtra, emailExtra, passExtra, passAgainExtra)
                        registerDialogFragment.setTargetFragment(targetFragment, REGISTER)
                        registerDialogFragment.show(fragmentManager, registerDialogFragment.javaClass.name)
                    }
                })

        return builder.create()
    }

    private fun setUpFinds(view: View) {
        loginEditText = view.findViewById(R.id.main_registration_login) as EditText
        emailEditText = view.findViewById(R.id.main_registration_email) as EditText
        passwordEditText = view.findViewById(R.id.main_registration_pass) as EditText
        passwordAgainEditText = view.findViewById(R.id.main_registration_pass_again) as EditText

    }
}