package com.isengard.frueguas

import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(
            "FraguasIsengard",
            "onCreate: Saruman despierta las fraguas de Isengard"
        )

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }
        // Capturar el identificador y equipamiento de las tropas
        val etIdentificador = findViewById<EditText>(R.id.etIdentificador)
        val spTipoUnidad = findViewById<Spinner>(R.id.spTipoUnidad)
        val rgEquipamiento = findViewById<RadioGroup>(R.id.rgEquipamiento)
        val cbAntorcha = findViewById<CheckBox>(R.id.cbAntorcha)
        val btnEnviar = findViewById<ImageButton>(R.id.btnEnviar)

        // Colocar el foco automáticamente
        etIdentificador.requestFocus()

        // Validar cuando el usuario abandona el campo
        etIdentificador.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && etIdentificador.text.toString().trim().isEmpty()) {
                etIdentificador.error =
                    "El ejército no acepta soldados anónimos"
            }
        }

        btnEnviar.setOnClickListener {

            val identificador = etIdentificador.text.toString().trim()

            if (identificador.isEmpty()) {
                etIdentificador.error =
                    "El ejército no acepta soldados anónimos"
                etIdentificador.requestFocus()
                return@setOnClickListener
            }

            val tipoUnidad = spTipoUnidad.selectedItem.toString()

            val idEquipamiento = rgEquipamiento.checkedRadioButtonId

            val equipamiento = if (idEquipamiento != -1) {
                findViewById<android.widget.RadioButton>(idEquipamiento).text.toString()
            } else {
                "Sin equipamiento seleccionado"
            }

            Log.d("FraguasIsengard", "ID: $identificador")
            Log.d("FraguasIsengard", "Tipo: $tipoUnidad")
            Log.d("FraguasIsengard", "Equipamiento: $equipamiento")
            Log.d("FraguasIsengard", "Antorcha: ${cbAntorcha.isChecked}")

            if (!cbAntorcha.isChecked) {
                Log.d("FraguasIsengard", "¡Peligro! Unidad enviada sin fuego")
            }

            Toast.makeText(
                this,
                "¡Unidad $identificador enviada al Abismo de Helm!",
                Toast.LENGTH_LONG
            ).show()
        }

        if (savedInstanceState != null) {

            etIdentificador.setText(
                savedInstanceState.getString("ID_TROPA", "")
            )

            spTipoUnidad.setSelection(
                savedInstanceState.getInt("TIPO_UNIDAD", 0)
            )

            val equipamientoGuardado =
                savedInstanceState.getInt("EQUIPAMIENTO", -1)

            if (equipamientoGuardado != -1) {
                rgEquipamiento.check(equipamientoGuardado)
            }

            cbAntorcha.isChecked =
                savedInstanceState.getBoolean("ANTORCHA", false)

            val errorGuardado =
                savedInstanceState.getString("ERROR_ID")

            if (errorGuardado != null) {
                etIdentificador.error = errorGuardado
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val etIdentificador = findViewById<EditText>(R.id.etIdentificador)
        val spTipoUnidad = findViewById<Spinner>(R.id.spTipoUnidad)
        val rgEquipamiento = findViewById<RadioGroup>(R.id.rgEquipamiento)
        val cbAntorcha = findViewById<CheckBox>(R.id.cbAntorcha)

        outState.putString(
            "ID_TROPA",
            etIdentificador.text.toString()
        )

        outState.putInt(
            "TIPO_UNIDAD",
            spTipoUnidad.selectedItemPosition
        )

        outState.putInt(
            "EQUIPAMIENTO",
            rgEquipamiento.checkedRadioButtonId
        )

        outState.putBoolean(
            "ANTORCHA",
            cbAntorcha.isChecked
        )

        outState.putString(
            "ERROR_ID",
            etIdentificador.error?.toString()
        )

        Log.d(
            "FraguasIsengard",
            "onSaveInstanceState: Los capataces guardan los datos de la tropa"
        )
    }

    override fun onStart() {
        super.onStart()
        Log.d(
            "FraguasIsengard",
            "onStart: Las fraguas se encienden"
        )
    }

    override fun onResume() {
        super.onResume()
        Log.d(
            "FraguasIsengard",
            "onResume: Saruman supervisa la producción"
        )
    }

    override fun onPause() {
        super.onPause()
        Log.d(
            "FraguasIsengard",
            "onPause: Saruman detiene la producción temporalmente"
        )
    }

    override fun onStop() {
        super.onStop()
        Log.d(
            "FraguasIsengard",
            "onStop: Las fraguas quedan fuera de servicio"
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(
            "FraguasIsengard",
            "onDestroy: La Torre de Orthanc apaga sus fraguas"
        )
    }
}