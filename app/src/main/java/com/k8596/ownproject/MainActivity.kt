package com.k8596.ownproject

import android.arch.persistence.room.Room
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity(),locationListItemDialogFragment.AddDialogListener  {

    private lateinit var db: LocationListRoomDatabase

    private lateinit var mInterstitialAd: InterstitialAd

    private var LocationList: MutableList<LocationListItem> = ArrayList()

    private lateinit var adapter: LocationListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713")

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"

        mInterstitialAd.loadAd(AdRequest.Builder().build())



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = LocationListAdapter(LocationList)
        recyclerView.adapter = adapter

        db = Room.databaseBuilder(applicationContext, LocationListRoomDatabase::class.java, "hs_db").build()
        loadLocationListItems()

        fab.setOnClickListener { view ->
            val dialog = locationListItemDialogFragment()
            dialog.show(supportFragmentManager, "AskNewItemDialogFragment")

        }

        initSwipe()
    }

    override fun onDialogPositiveClick(item: LocationListItem) {
        val handler = Handler(Handler.Callback {
            Toast.makeText(applicationContext,it.data.getString("message"), Toast.LENGTH_SHORT).show()
            adapter.update(LocationList)
            true
        })
        Thread(Runnable {
            val id = db.locationListDao().insert(item)
            item.id = id.toInt()
            LocationList.add(item)
            val message = Message.obtain()
            message.data.putString("message",getString(R.string.db_new_added))
            handler.sendMessage(message)
        }).start()

        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        } else {

            Log.d("TAG", "The interstitial wasn't loaded yet.")
        }

    }

    private fun loadLocationListItems() {

        val handler = Handler(Handler.Callback {
            Toast.makeText(applicationContext,it.data.getString("message"), Toast.LENGTH_SHORT).show()
            adapter.update(LocationList)
            true
        })
        Thread(Runnable {
            LocationList = db.locationListDao().getAll()
            val message = Message.obtain()
            if (LocationList.size > 0)
                message.data.putString("message",getString(R.string.db_locations_found))
            else
                message.data.putString("message",getString(R.string.db_no_items))
            handler.sendMessage(message)
        }).start()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initSwipe() {

        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val handler = Handler(Handler.Callback {

                    Toast.makeText(applicationContext,it.data.getString("message"), Toast.LENGTH_SHORT).show()

                    adapter.update(LocationList)
                    true
                })

                var id = LocationList[position].id
                LocationList.removeAt(position)
                Thread(Runnable {
                    db.locationListDao().delete(id)
                    val message = Message.obtain()
                    message.data.putString("message",getString(R.string.db_deleted))
                    handler.sendMessage(message)
                }).start()
            }

            // Moved
            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                return true
            }


        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}
