package com.example.smart_things.Funtions.Autentication

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AutheFuntionImpl: AutheInteractor {

    override suspend fun signInWithemailandpassword(email: String, password: String/*//corrutina, listener: AutheInteractor.Callback*/):Unit = suspendCancellableCoroutine{ continuation ->
        FirebaseAuth
        .getInstance()
        .signInWithEmailAndPassword(email,password)
        .addOnCompleteListener{
            if (it.isSuccessful){
                if (FirebaseAuth.getInstance().currentUser?.isEmailVerified!!){
                    continuation.resume(Unit)
                }else{
                    continuation.resumeWithException(
                        AuthenticationException(
                            "Error, email it is not verifield"
                        )
                    )
                }
            }else{
                continuation.resumeWithException(
                    AuthenticationException(
                        it.exception?.message
                    )
                )
            }
         }
    }

    override fun CreateUserWithemailandpassword(email: String, password: String , listener: AutheInteractor.Callback) {
        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    /*val profileupdate: UserProfileChangeRequest = UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()
                    FirebaseAuth.getInstance().currentUser?.updateProfile(profileupdate).addOnCompleteListener{
                    }*/
                    listener.AutheonSuccessful()
                }else{
                    listener.AutheonFailure(it.exception?.message)
                }
            }
    }

    /*override fun isValidedEmail(): Boolean {
        val auth = FirebaseAuth.getInstance().currentUser
        return auth?.isEmailVerified!!
    }*/

    override fun sentValideEmail(listener: AutheInteractor.Callback) {
        val auth = FirebaseAuth.getInstance().currentUser
        auth?.sendEmailVerification()?.addOnCompleteListener{
            if (it.isSuccessful){
                listener.AutheonSuccessful()
            }else{
                listener.AutheonFailure(it.exception?.message)
            }
        }
    }

    override fun LogOut() {
        FirebaseAuth.getInstance().signOut()
    }

    override suspend fun sendPasswordResetEmail(email: String):Unit = suspendCancellableCoroutine{continuation->
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener{
            if (it.isSuccessful){
                continuation.resume(Unit)
            }else{
                continuation.resumeWithException(AuthenticationException(it.exception?.message)
                )
            }
        }
    }

}