package com.leandrodev.auth.state

import com.leandrodev.auth.model.User
import com.leandrodev.shared.MutableViewState
import com.leandrodev.shared.createViewAction
import com.leandrodev.auth.utils.toUser
import com.leandrodev.shared.scope
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class AuthStateViewModelAndroid : AuthStateViewModel() {
    override val state: MutableViewState<AuthState> = createViewAction()

    init {
        scope.launch {
            Firebase.auth.authStateChanged.collectLatest {
                val user = Firebase.auth.currentUser?.toUser()
                state.emit(getStateFromUser(user))
            }
        }
    }

    private fun getStateFromUser(user: User?): AuthState {
        return if (user != null) {
            AuthState.Authenticated(user)
        } else {
            AuthState.Unauthenticated
        }
    }
}