package com.example.mapsdemo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.mapsdemo.databinding.ActivityMainBinding
import com.example.mapsdemo.localStorage.PlaceModel
import com.example.mapsdemo.localStorage.PlacesDB
import com.example.mapsdemo.ui.adapter.PlacesListAdapter
import com.example.mapsdemo.utils.Constants

class MainActivity : BaseActivity(), PlacesListAdapter.OnOptionSelected {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAdapter: PlacesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initDb()

        mBinding.btnAdd.setOnClickListener {
            startActivity(Intent(this@MainActivity, SearchLocationActivity::class.java))
        }


        mBinding.btnGetDirections.setOnClickListener {
            val intent = Intent(this@MainActivity, DirectionsActivity::class.java)
            intent.putExtra(Constants.DATA, mAdapter.list)
            startActivity(intent)
        }

        mBinding.rvLocations.layoutManager = LinearLayoutManager(this)
        mAdapter = PlacesListAdapter(this)
        mBinding.rvLocations.adapter = mAdapter

        loadLocations()

    }

    override fun onEdit(place: PlaceModel) {
        val intent = Intent(this@MainActivity, SearchLocationActivity::class.java)
        intent.putExtra(Constants.DATA, place)
        startActivity(intent)
    }

    override fun onDelete(place: PlaceModel) {
        database.placesDao().remove(place)
        loadLocations()
    }

    override fun onResume() {
        super.onResume()
        loadLocations()
    }

    private fun loadLocations() {
        val list = database.placesDao().getAll()
        mAdapter = PlacesListAdapter(this)
        mAdapter.setList(arrayListOf<PlaceModel>().apply { addAll(list) })
        mBinding.rvLocations.adapter = mAdapter
        mBinding.btnGetDirections.visibility = if (list.size > 1) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

}