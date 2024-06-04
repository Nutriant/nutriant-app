package com.dicoding.nutrient.utils

import com.dicoding.nutrient.R
import com.dicoding.nutrient.data.model.history.DataHistoryLabel
import java.util.UUID

object DummyHistoryLabel {

    val getHistoryLabel: ArrayList<DataHistoryLabel>
        get() {
            val listHistoryLabel = arrayListOf<DataHistoryLabel>()
            listHistoryLabel.add(DataHistoryLabel(
                UUID.randomUUID().toString(),
                "Testing Label A",
                "2 Jun",
                R.drawable.img_label_history
            ))
            listHistoryLabel.add(DataHistoryLabel(
                UUID.randomUUID().toString(),
                "Testing Label A",
                "2 Jun",
                R.drawable.img_label_history
            ))
            listHistoryLabel.add(DataHistoryLabel(
                UUID.randomUUID().toString(),
                "Testing Label A",
                "2 Jun",
                R.drawable.img_label_history
            ))
            listHistoryLabel.add(DataHistoryLabel(
                UUID.randomUUID().toString(),
                "Testing Label A",
                "2 Jun",
                R.drawable.img_label_history
            ))
            return listHistoryLabel
        }
}