package com.example.zupzup_manager.data.datasource.admin

import com.example.zupzup_manager.di.FireBaseModule
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

class AdminDataSourceImpl @Inject constructor(
    @FireBaseModule.AdminRef private val adminRef: CollectionReference
) : AdminDataSource {
    override fun login(id: String, pw: String): Task<QuerySnapshot> {
        return adminRef.whereEqualTo("id", id).whereEqualTo("pw", pw).get()
    }
}