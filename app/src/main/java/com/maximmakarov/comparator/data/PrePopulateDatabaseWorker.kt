package com.maximmakarov.comparator.data

import android.util.Log
import androidx.work.Worker
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject


class PrePopulateDatabaseWorker : Worker(), KoinComponent {

    val db: AppDatabase by inject()

    override fun doWork(): Worker.Result {
        return try {
            db.templateDao().insert(PREPOPULATE_TEMPLATE)
            db.attributeGroupDao().insertAll(PREPOPULATE_GROUPS)
            db.attributeDao().insertAll(PREPOPULATE_ATTRIBUTES)
            db.itemDao().insertAll(PREPOPULATE_ITEMS)
            db.itemDataDao().insertAll(PREPOPULATE_ITEMS_DATA)

            Worker.Result.SUCCESS
        } catch (ex: Exception) {
            Log.e(PrePopulateDatabaseWorker::class.java.simpleName, "Error seeding database", ex)
            Worker.Result.FAILURE
        }
    }
}