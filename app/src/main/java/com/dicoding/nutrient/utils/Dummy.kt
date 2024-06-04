package com.dicoding.nutrient.utils

import com.dicoding.nutrient.data.model.history.DataListBmi
import java.util.UUID

object Dummy {

    val getListDataBmi: ArrayList<DataListBmi>
        get() {
            val listDataBmi = arrayListOf<DataListBmi>()
            listDataBmi.add(
                DataListBmi(
                UUID.randomUUID().toString(),
                20,
                30,
                "Ideal"
            )
            )
            listDataBmi.add(
                DataListBmi(
                UUID.randomUUID().toString(),
                20,
                30,
                "Ideal"
            )
            )
            listDataBmi.add(
                DataListBmi(
                UUID.randomUUID().toString(),
                20,
                30,
                "Ideal"
            )
            )
            listDataBmi.add(
                DataListBmi(
                UUID.randomUUID().toString(),
                20,
                30,
                "Ideal"
            )
            )
            listDataBmi.add(
                DataListBmi(
                UUID.randomUUID().toString(),
                20,
                30,
                "Ideal"
            )
            )
            return listDataBmi
        }
}