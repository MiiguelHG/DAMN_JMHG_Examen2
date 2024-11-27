package com.example.damn_jmhg_examenll

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.damn_jmhg_examenll.api.entities.UserEntity
import com.example.damn_jmhg_examenll.api.repositories.UserRepository
import com.example.damn_jmhg_examenll.room.data.UserDatabase
import com.example.damn_jmhg_examenll.room.entities.Address
import com.example.damn_jmhg_examenll.room.entities.Company
import com.example.damn_jmhg_examenll.room.entities.Geo
import com.example.damn_jmhg_examenll.room.entities.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val userRepository = UserRepository()
    private lateinit var db: UserDatabase

    lateinit var ibRegresarUsuarios: ImageButton;
    lateinit var rvUsuarios: RecyclerView;

    private lateinit var adapterUser: AdapterRecyclerUser;
    private lateinit var adapterUserRoom: AdapterRecyclerUserRoom;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = UserDatabase.getInstance(this)

        ibRegresarUsuarios = findViewById(R.id.ibRegresarUsuariosWifi)
        rvUsuarios = findViewById(R.id.rvUsuariosWifi)
        val tvNoInternet: TextView = findViewById(R.id.tvNoInternet)

        // Para obtener los usuarios de la API
        adapterUser = AdapterRecyclerUser(emptyList())


        // Para obtener los usuarios de la base de datos
        adapterUserRoom = AdapterRecyclerUserRoom(emptyList())

        rvUsuarios.layoutManager = LinearLayoutManager(this)

        if (isNetworkAvailable()) {
            rvUsuarios.adapter = adapterUser
            tvNoInternet.visibility = TextView.GONE
            obtenerUsuarios()
        } else {
            rvUsuarios.adapter = adapterUserRoom
            tvNoInternet.visibility = TextView.VISIBLE
            Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_LONG).show()
            obtenerUsuariosRoom()
        }

        adapterUser.setOnItemClickListener(object : AdapterRecyclerUser.OnItemClickListener {
            override fun onItemClick(position: Int) {
//                Toast.makeText(this@MainActivity, "Click en ${adapterUser.users[position].name}", Toast.LENGTH_LONG).show()
                val intentPosts = Intent(this@MainActivity, PostsActivity::class.java)
                intentPosts.putExtra("id", adapterUser.users[position].id)
                intentPosts.putExtra("name", adapterUser.users[position].name)
                startActivity(intentPosts)
            }

            override fun onLongItemClick(position: Int) {
                // Toast.makeText(this@MainActivity, "LongClick en ${adapterUser.users[position].name}", Toast.LENGTH_LONG).show()
                guardarUsuarioRoom(adapterUser.users[position])
            }
        })

        adapterUserRoom.setOnItemClickListener(object : AdapterRecyclerUserRoom.OnItemClickListener {
            override fun onLongItemClick(position: Int) {
                //Toast.makeText(this@MainActivity, "LongClick en ${adapterUserRoom.users[position].name}", Toast.LENGTH_LONG).show()
                CoroutineScope(Dispatchers.IO).launch {
                    db.userDao().delete(adapterUserRoom.users[position])
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Usuario eliminado de la base de datos", Toast.LENGTH_LONG).show()
                    }
                }
                adapterUserRoom.notifyDataSetChanged()
            }
        })

        ibRegresarUsuarios.setOnClickListener {
            finish()
        }
    }

    private fun guardarUsuarioRoom(userEntity: UserEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = db.userDao().getById(userEntity.id).firstOrNull()
                if (user != null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "El usuario ya existe en la base de datos", Toast.LENGTH_LONG).show()
                    }
                } else {
                    val newGeo = Geo(
                        lat = userEntity.address.geo.lat,
                        lng = userEntity.address.geo.lng
                    )

                    val newAddress = Address(
                        street = userEntity.address.street,
                        suite = userEntity.address.suite,
                        city = userEntity.address.city,
                        zipcode = userEntity.address.zipcode,
                        geo = newGeo
                    )

                    val newCompany = Company(
                        nameCompany = userEntity.company.name,
                        catchPhrase = userEntity.company.catchPhrase,
                        bs = userEntity.company.bs
                    )
                    val newUser = User(
                        id = userEntity.id,
                        name = userEntity.name,
                        username = userEntity.username,
                        email = userEntity.email,
                        phone = userEntity.phone,
                        website = userEntity.website,
                        address = newAddress,
                        company = newCompany
                    )
                    withContext(Dispatchers.Main) {
                        db.userDao().add(newUser)
                        Toast.makeText(this@MainActivity, "Usuario guardado en la base de datos", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    Log.e("Error", e.message.toString())
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun obtenerUsuariosRoom() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val usuarios = db.userDao().getAll().collect { userList ->
                    withContext(Dispatchers.Main) {
                        adapterUserRoom.users = userList
                        adapterUserRoom.notifyDataSetChanged()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                Log.e("Error", e.message.toString())
            }
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