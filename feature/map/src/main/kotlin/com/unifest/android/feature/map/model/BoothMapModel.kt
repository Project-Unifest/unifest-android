package com.unifest.android.feature.map.model

import com.naver.maps.geometry.LatLng
import com.naver.maps.map.clustering.ClusteringKey

data class BoothMapModel(
    val id: Long = 0L,
    val name: String = "",
    val category: String = "",
    val description: String = "",
    val thumbnail: String = "",
    val location: String = "",
    val latitude: Double = 0.toDouble(),
    val longitude: Double = 0.toDouble(),
    val isSelected: Boolean = false,
) : ClusteringKey {
    override fun getPosition(): LatLng {
        return LatLng(latitude, longitude)
    }
}
