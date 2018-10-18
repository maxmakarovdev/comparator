package com.maximmakarov.comparator.data.database

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject


class PrePopulateDatabaseWorker(context: Context, params: WorkerParameters) : Worker(context, params), KoinComponent {

    val db: AppDatabase by inject()

    override fun doWork(): Result {
        return try {
            db.templateDao().insert(PREPOPULATE_TEMPLATE)
            db.attributeGroupDao().insert(*PREPOPULATE_GROUPS)
            db.attributeDao().insert(*PREPOPULATE_ATTRIBUTES)
            db.itemDao().insert(*PREPOPULATE_ITEMS)
            db.itemAttrDataDao().insert(*PREPOPULATE_ITEMS_DATA)

            Result.SUCCESS
        } catch (ex: Exception) {
            Log.e(PrePopulateDatabaseWorker::class.java.simpleName, "Error seeding database", ex)
            Result.FAILURE
        }
    }
}