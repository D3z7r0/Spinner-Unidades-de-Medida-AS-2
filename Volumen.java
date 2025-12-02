package com.example.proyectofinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.example.proyectofinal.R;

public class Volumen extends Fragment {

    // Componentes de la interfaz
    private EditText editValor;
    private Spinner spinnerUnidad;
    private TextView txtResultados;
    private TextView txtTitulo;

    // Constantes específicas de Volumen
    private final String CATEGORY_NAME = "Conversor de Volumen";
    private final String[] UNITS = {"Litros", "Mililitros", "Galones (US)"};
    // Factores relativos al LITRO (la unidad base, factor 1.0)
    private final double[] CONVERSION_FACTORS = {1.0, 1000.0, 0.264172};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Usa el layout universal
        View view = inflater.inflate(R.layout.fragment_fragmento__botones, container, false);

        // Inicialización de vistas
        editValor = view.findViewById(R.id.editValor);
        spinnerUnidad = view.findViewById(R.id.spinnerUnidad);
        txtResultados = view.findViewById(R.id.txtResultados);
        txtTitulo = view.findViewById(R.id.txtTituloCategoria);
        Button btnCalcular = view.findViewById(R.id.btnCalcular);

        // Configurar Título
        txtTitulo.setText(CATEGORY_NAME);

        // Configurar Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, UNITS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnidad.setAdapter(adapter);

        // Configurar Botón
        btnCalcular.setOnClickListener(v -> performConversion());

        return view;
    }

    private void performConversion() {
        String inputStr = editValor.getText().toString();
        if (inputStr.isEmpty()) {
            Toast.makeText(getContext(), "Ingresa un valor", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double inputValue = Double.parseDouble(inputStr);
            int selectedIndex = spinnerUnidad.getSelectedItemPosition();

            // 1. Convertir la entrada a la unidad BASE (Litro)
            // Fórmula: ValorBase = ValorEntrada / FactorEntrada
            double baseValue = inputValue / CONVERSION_FACTORS[selectedIndex];

            // 2. Usar StringBuilder para construir el reporte
            StringBuilder sb = new StringBuilder();
            sb.append("Conversión de ").append(inputValue).append(" ").append(UNITS[selectedIndex]).append(":\n\n");

            for (int i = 0; i < UNITS.length; i++) {
                // Convertir de Base (Litro) a Destino: ValorDestino = ValorBase * FactorDestino
                double convertedValue = baseValue * CONVERSION_FACTORS[i];

                // Formatear a 4 decimales
                sb.append(String.format("• %.4f %s\n", convertedValue, UNITS[i]));
            }

            // 3. Desplegar
            txtResultados.setText(sb.toString());

        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Error: Valor no válido.", Toast.LENGTH_SHORT).show();
        }
    }
}
