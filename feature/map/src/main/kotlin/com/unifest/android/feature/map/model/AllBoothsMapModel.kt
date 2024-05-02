package com.unifest.android.feature.map.model

import android.os.Parcelable
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.clustering.ClusteringKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class AllBoothsMapModel(
    val id: Long = 0L,
    val name: String = "",
    val category: String = "",
    val description: String = "",
    val location: String = "",
    val latitude: Double = 0.toDouble(),
    val longitude: Double = 0.toDouble(),
) : Parcelable, ClusteringKey {
    override fun getPosition(): LatLng {
        return LatLng(latitude, longitude)
    }
}

// @Parcelize
// data class BoothDetailMapModel(
//     val id: Long = 0L,
//     val name: String = "",
//     val category: String = "",
//     val description: String = "",
//     val warning: String = "",
//     val location: String = "",
//     val latitude: Double = 0.toDouble(),
//     val longitude: Double = 0.toDouble(),
//     val menus: List<MenuMapModel> = emptyList(),
// ) : Parcelable, TedClusterItem {
//     override fun getTedLatLng(): TedLatLng {
//         return TedLatLng(latitude, longitude)
//     }
// }
