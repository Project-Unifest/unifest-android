package com.unifest.android.feature.map.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ted.gun0912.clustering.clustering.TedClusterItem
import ted.gun0912.clustering.geometry.TedLatLng

// @Parcelize
// data class BoothMapModel(
//     val id: Long = 0L,
//     val name: String = "",
//     val category: String = "",
//     val description: String = "",
//     val thumbnail: String = "",
//     val location: String = "",
//     val latitude: Double = 0.toDouble(),
//     val longitude: Double = 0.toDouble(),
// ) : Parcelable, ClusteringKey {
//     override fun getPosition(): LatLng {
//         return LatLng(latitude, longitude)
//     }
// }

@Parcelize
data class BoothMapModel(
    val id: Long = 0L,
    val name: String = "",
    val category: String = "",
    val description: String = "",
    val thumbnail: String = "",
    val location: String = "",
    val latitude: Double = 0.toDouble(),
    val longitude: Double = 0.toDouble(),
) : Parcelable, TedClusterItem {
    override fun getTedLatLng(): TedLatLng {
        return TedLatLng(latitude, longitude)
    }
}