package com.adamnoorapps.recipefinder.ui.login

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AccountServiceImpl : AccountService{
    override fun createAnonymousAccount(onResult: (Throwable?) -> Unit) {
        Firebase.auth.signInAnonymously()
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                onResult(it.exception)
                if(it.exception == null)
                    Firebase.firestore.collection("USERS").document(Firebase.auth.currentUser?.uid ?: "")
                        .get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful && !task.result.exists()) {
                                Firebase.auth.currentUser?.uid?.let { it1 ->
                                    Firebase.firestore.collection("USERS").document(it1)
                                        .set(mapOf("savedRecipes" to emptyList<String>()))
                                }
                            }
                        }
            }

    }

    override fun linkAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        val credential = EmailAuthProvider.getCredential(email, password)

        Firebase.auth.currentUser!!.linkWithCredential(credential)
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun signOut() {
        Firebase.auth.signOut()
    }

    override fun signUp(email: String, password: String, confirmPassword: String, onResult: (Throwable?) -> Unit) {
        if (password != confirmPassword) {
            onResult(IllegalArgumentException("Passwords do not match"))
            return
        }
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun editDisplayName(displayName: String, onResult: (Throwable?) -> Unit) {
        Firebase.auth.currentUser?.updateProfile(
            userProfileChangeRequest {
                this.displayName = displayName
            }
        )?.addOnCompleteListener { onResult(it.exception) } ?: onResult(NullPointerException("No user is currently signed in"))
    }
}