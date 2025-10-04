package com.ddanddan.ddanddan.presentation.friends

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(

) : ViewModel(), ContainerHost<FriendsState, FriendsSideEffect> {
    override val container =
        container<FriendsState, FriendsSideEffect>(FriendsState())
}