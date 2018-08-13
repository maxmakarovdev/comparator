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
            db.attributeGroupDao().insert(*PREPOPULATE_GROUPS)
            db.attributeDao().insert(*PREPOPULATE_ATTRIBUTES)
            db.itemDao().insert(*PREPOPULATE_ITEMS)
            db.itemAttrDataDao().insert(*PREPOPULATE_ITEMS_DATA)

            Worker.Result.SUCCESS
        } catch (ex: Exception) {
            Log.e(PrePopulateDatabaseWorker::class.java.simpleName, "Error seeding database", ex)
            Worker.Result.FAILURE
        }
    }
}