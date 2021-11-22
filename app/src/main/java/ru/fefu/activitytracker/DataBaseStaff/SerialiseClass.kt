package ru.fefu.activitytracker.DataBaseStaff

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SerialiseClass{

     fun listEncode(list:List<Pair<Double,Double>>):String {
        return Json.encodeToString(list)
    }
    fun listDecode(list: String):List<Pair<Double,Double>>{
        return Json.decodeFromString<List<Pair<Double,Double>>>(list)
    }
    fun itemEncode(cross_item:CrossItemEntity):String {
        return Json.encodeToString(cross_item)
    }
    fun itemDecode(cross_item: String):CrossItemEntity{
        return Json.decodeFromString<CrossItemEntity>(cross_item)
    }
}