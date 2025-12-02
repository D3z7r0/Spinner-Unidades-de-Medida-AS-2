package com.example.proyectofinal;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar fragmentos
        Fragment fragmentLongitud = new com.example.proyectofinal.Longitud();
        Fragment fragmentMasa = new com.example.proyectofinal.Masa();
        Fragment fragmentVolumen = new com.example.proyectofinal.Volumen();

        // Cargar Longitud por defecto
        loadFragment(fragmentLongitud);

        // Configurar botones
        Button btnLongitud = findViewById(R.id.btnLongitud);
        Button btnMasa = findViewById(R.id.btnMasa);
        Button btnVolumen = findViewById(R.id.btnVolumen);

        btnLongitud.setOnClickListener(v -> loadFragment(fragmentLongitud));
        btnMasa.setOnClickListener(v -> loadFragment(fragmentMasa));
        btnVolumen.setOnClickListener(v -> loadFragment(fragmentVolumen));
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
