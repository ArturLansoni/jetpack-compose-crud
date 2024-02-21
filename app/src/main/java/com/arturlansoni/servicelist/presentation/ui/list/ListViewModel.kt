package com.arturlansoni.servicelist.presentation.ui.list

import androidx.lifecycle.ViewModel
import com.arturlansoni.servicelist.core.enums.ScreenStatus
import com.arturlansoni.servicelist.core.infra.auth.AuthClient
import com.arturlansoni.servicelist.core.infra.database.DatabaseClient
import com.arturlansoni.servicelist.domain.entities.Service
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val databaseClient: DatabaseClient,
    private val authClient: AuthClient,
) :
    ViewModel() {

    private val _state = MutableStateFlow(ListModel())
    val state = _state.asStateFlow<ListModel>()

    suspend fun logout() {
        authClient.signOut()
    }

    suspend fun onLoad() {
        updateStatus(ScreenStatus.Loading)
        val (error, documents) = databaseClient.getAll()
        if (error.message != null) {
            updateStatus(ScreenStatus.Error(error.message))
        } else {
            updateStatus(ScreenStatus.Success)
            val serviceList = documents?.map { doc ->
                Service(
                    id = doc.id,
                    name = doc.data?.get("name") as? String ?: "",
                    location = doc.data?.get("location") as? String ?: "",
                    rating = doc.data?.get("rating") as? Double ?: 0.0,
                    category = doc.data?.get("category") as? String ?: "",
                    imageURL = doc.data?.get("imageURL") as? String ?: ""
                )
            }
            if (serviceList != null) {
                updateServices(serviceList)
            }
        }
    }

    private fun updateStatus(status: ScreenStatus) = _state.update {
        it.copy(
            status = status
        )
    }

    private fun updateServices(services: List<Service>) = _state.update {
        it.copy(
            services = services
        )
    }

}
