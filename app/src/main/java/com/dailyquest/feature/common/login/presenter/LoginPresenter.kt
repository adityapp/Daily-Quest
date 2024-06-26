package com.dailyquest.feature.common.login.presenter

import com.dailyquest.feature.common.login.LoginPresenterContract
import com.dailyquest.feature.common.login.LoginViewContract
import com.dailyquest.utils.Constants
import com.dailyquest.utils.SessionManager
import com.dailyquest.utils.isEmailValid
import com.dailyquest.utils.isPasswordValid
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId

class LoginPresenter(private val view: LoginViewContract, private val pref: SessionManager) :
    LoginPresenterContract {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    override fun login(email: String, password: String, role: String, parentUid: String) {
        view.showLoadingDialog()
        when {
            !email.isEmailValid() -> view.showFailedMessage("E-mail tidak valid!")
            !password.isPasswordValid() -> view.showFailedMessage("Kata sandi yang dimasukan salah!")
            else -> auth(email, password, role, parentUid)
        }
    }

    private fun auth(email: String, password: String, role: String, parentUid: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            it.user?.let { user ->
                if (role == Constants.ANAK) {
                    pref.setSession(parentUid, role)
                } else {
                    pref.setSession(user.uid, role)
                }
                sendTokenToDatabase()
            }
            view.navigateToHome()
        }.addOnFailureListener { _ ->
            view.showFailedMessage("Email atau kata sandi salah!")
        }
    }

    private fun sendTokenToDatabase() {
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            if (!it.isSuccessful) {
                return@addOnCompleteListener
            }
            it.result?.token?.let { token ->
                pref.getRole()?.let { role ->
                    when (role) {
                        Constants.ANAK -> sendChildrenTokenToDatabase(token)
                        Constants.ORANG_TUA -> sendParentTokenToDatabase(token)
                    }
                }
            }
        }
    }

    private fun sendParentTokenToDatabase(token: String) {
        firebaseAuth.uid?.let { uid ->
            firebaseDatabase.getReference(Constants.DATABASE_USER).child(uid)
                .updateChildren(mapOf(Constants.DATABASE_TOKEN to token))
        }
    }

    private fun sendChildrenTokenToDatabase(token: String) {
        firebaseAuth.uid?.let { uid ->
            pref.getParentUid()?.let { parentUid ->
                firebaseDatabase.getReference(Constants.DATABASE_USER).child(parentUid)
                    .child(Constants.ANAK.toLowerCase()).child(uid)
                    .updateChildren(mapOf(Constants.DATABASE_TOKEN to token))
            }
        }
    }
}