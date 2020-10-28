package com.umbertoemonds.myapplication.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.umbertoemonds.myapplication.R
import com.umbertoemonds.myapplication.Utils.AlertUtils
import com.umbertoemonds.myapplication.databinding.ActivityGareBinding
import com.umbertoemonds.myapplication.model.Gare
import java.util.*

class GareActivity: AppCompatActivity() {

    private var gare: Gare? = null
    private lateinit var binding: ActivityGareBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGareBinding.inflate(layoutInflater)
        animate()
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        gare = intent.getSerializableExtra("gare") as Gare

        supportActionBar?.title = getString(R.string.gare) + gare?.gare
        binding.nbPianos.text = gare?.piano.toString()
        binding.nbBabyfoot.text = gare?.baby_foot.toString()
        binding.nbPowerStation.text = gare?.power_station.toString()
        binding.nbDistrHistoiresCourtes.text = gare?.distr_histoires_courtes.toString()

        binding.powerstation.setOnClickListener {
            AlertUtils.showMessage(this, getString(R.string.power_station))
        }

        binding.histoirescourtes.setOnClickListener {
            AlertUtils.showMessage(this, getString(R.string.distr_hc))
        }

        binding.button.setOnClickListener {
            val uri = Uri.parse("http://google.fr/search?q=gare ${gare?.gare?.toLowerCase(Locale.ROOT)}")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }

    fun animate(){
        val slide = Slide()
        slide.slideEdge = Gravity.END
        slide.setDuration(200)
        slide.setInterpolator(DecelerateInterpolator())
        window.exitTransition = slide
        window.enterTransition = slide
    }

}