package com.arturlansoni.servicelist.presentation.ui.form

import androidx.lifecycle.ViewModel
import com.arturlansoni.servicelist.core.enums.ScreenStatus
import com.arturlansoni.servicelist.core.infra.database.DatabaseClient
import com.arturlansoni.servicelist.domain.entities.Service
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(private val databaseClient: DatabaseClient) : ViewModel() {

    private val _state = MutableStateFlow(FormModel())
    val state = _state.asStateFlow<FormModel>()

    suspend fun onLoad(id: String) {
        updateStatus(ScreenStatus.Loading)
        val (error, doc) = databaseClient.get(id)
        if (error.message != null) {
            updateStatus(ScreenStatus.Error(error.message))
        } else {
            updateStatus(ScreenStatus.Success)
            updateService(Service(
                id = doc?.data?.get("id") as? String ?: "",
                name = doc?.data?.get("name") as? String ?: "",
                location = doc?.data?.get("location") as? String ?: "",
                rating = doc?.data?.get("rating") as? Double ?: 0.0,
                category = doc?.data?.get("category") as? String ?: "",
                imageURL = doc?.data?.get("imageURL") as? String ?: ""
            ))
        }
    }

    suspend fun onCreate(service: Service) {
        updateStatus(ScreenStatus.Loading)
        val (error, _) = databaseClient.create(service)
        if (error.message != null) {
            updateStatus(ScreenStatus.Error(error.message))
        } else {
            updateStatus(ScreenStatus.Success)
        }
    }

    suspend fun onUpdate(service: Service) {
        updateStatus(ScreenStatus.Loading)
        val (error, _) = databaseClient.update(service)
        if (error.message != null) {
            updateStatus(ScreenStatus.Error(error.message))
        } else {
            updateStatus(ScreenStatus.Success)
        }
    }

    suspend fun onDelete(id: String) {
        updateStatus(ScreenStatus.Loading)
        val (error, _) = databaseClient.delete(id)
        if (error.message != null) {
            updateStatus(ScreenStatus.Error(error.message))
        } else {
            updateStatus(ScreenStatus.Success)
        }
    }

    private fun updateStatus(status: ScreenStatus) = _state.update {
        it.copy(
            status = status
        )
    }
    private fun updateService(service: Service) = _state.update {
        it.copy(
            service = service
        )
    }
}
