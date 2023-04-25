package com.example.zupzup_manager.data.datasource.admin

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface AdminDataSource {
    fun login(id: String, pw: String): Task<QuerySnapshot>
}