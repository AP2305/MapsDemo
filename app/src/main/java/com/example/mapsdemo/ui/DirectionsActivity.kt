package com.example.mapsdemo.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mapsdemo.R
import com.example.mapsdemo.databinding.ActivitySearchLocationBinding
import com.example.mapsdemo.localStorage.PlaceModel
import com.example.mapsdemo.utils.Constants
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.libraries.places.api.Places


class DirectionsActivity : BaseActivity() {

    private lateinit var mBinding: ActivitySearchLocationBinding
    private lateinit var mapFragment: SupportMapFragment
    private var googleMap: GoogleMap? = null
    private var markedPlaces: List<PlaceModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySearchLocationBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

//        initDb()

        markedPlaces = intent.getSerializableExtra(Constants.DATA) as List<PlaceModel>

        mBinding.llSearch.visibility = View.GONE
        mBinding.rvPlaces.visibility = View.GONE

        mapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync {
            this.googleMap = it
        }

        Places.initialize(this, Constants.MAPS_API_KEY)

        mBinding.ivBack.setOnClickListener {
            onBackPressed()
        }

    }

}