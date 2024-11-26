package com.example.damn_jmhg_examenll

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.damn_jmhg_examenll.api.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val userRepository = UserRepository()

    lateinit var ibRegresarUsuarios: ImageButton;
    lateinit var rvUsuarios: RecyclerView;

    private lateinit var adapterUser: AdapterRecyclerUser;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ibRegresarUsuarios = findViewById(R.id.ibRegresarUsuariosWifi)
        rvUsuarios = findViewById(R.id.rvUsuariosWifi)

        adapterUser = AdapterRecyclerUser(emptyList())

        rvUsuarios.adapter = adapterUser
        rvUsuarios.layoutManager = LinearLayoutManager(this)

        if (isNetworkAvailable()) {
            obtenerUsuarios()
        } else {
            Log.d("Network", "No hay conexión a internet")
            Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_LONG).show()
        }

        adapterUser.setOnItemClickListener(object : AdapterRecyclerUser.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(this@MainActivity, "Click en ${adapterUser.users[position].name}", Toast.LENGTH_LONG).show()
                val intentPosts = Intent(this@MainActivity, PostsActivity::class.java)
                intentPosts.putExtra("id", adapterUser.users[position].id)
                startActivity(intentPosts)
            }

            override fun onLongItemClick(position: Int) {
                Toast.makeText(this@MainActivity, "LongClick en ${adapterUser.users[position].name}", Toast.LENGTH_LONG).show()
            }
        })

        ibRegresarUsuarios.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun obtenerUsuarios() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val usuarios = userRepository.getAllUsers()
                withContext(Dispatchers.Main) {
                    adapterUser.users = usuarios
                    adapterUser.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                Log.e("Error", e.message.toString())
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}