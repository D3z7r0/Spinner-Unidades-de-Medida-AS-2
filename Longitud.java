package com.example.proyectofinal;

import android.os.Bundle;
import android.text.Html;
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

import java.text.DecimalFormat;

public class Longitud extends Fragment {

    // Componentes de la interfaz
    private EditText editValor;
    private Spinner spinnerUnidad;
    private TextView txtResultados;
    private TextView txtTitulo;

    // Constantes específicas de Longitud
    private final String CATEGORY_NAME = "Conversor de Longitud";
    private final String[] UNITS = {"Metros", "Kilómetros", "Centímetros", "Pulgadas", "Pies"};
    // Factores relativos al METRO (la unidad base, factor 1.0)
    private final double[] CONVERSION_FACTORS = {1.0, 0.001, 100.0, 39.3701, 3.28084};

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

            // 1. Lógica de cálculo (igual que antes)
            double baseValue = inputValue / CONVERSION_FACTORS[selectedIndex];

            // 2. Preparar el StringBuilder y el Formato Numérico
            StringBuilder sb = new StringBuilder();

            // El formato "#.####" significa: muestra hasta 4 decimales,
            // pero si es entero (10.0), no muestres los ceros (10).
            DecimalFormat df = new DecimalFormat("#.####");

            sb.append("<small>Conversión de: <b>")
                    .append(df.format(inputValue))
                    .append(" ")
                    .append(UNITS[selectedIndex])
                    .append("</b></small><br/><br/>");

            // 3. Bucle con diseño HTML
            for (int i = 0; i < UNITS.length; i++) {
                double convertedValue = baseValue * CONVERSION_FACTORS[i];

                // Diseño: Nombre de unidad en negrita, Valor en color y letra un poco más grande
                sb.append("<b>").append(UNITS[i]).append(":</b><br/>");
                sb.append("<font color='#009688'>").append(df.format(convertedValue)).append("</font>");
                sb.append("<br/><br/>"); // Doble salto de línea para separar
            }

            // 4. Renderizar el HTML en el TextView
            // Usamos Html.fromHtml para que Android entienda las etiquetas <b> y <font>
            txtResultados.setText(Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_COMPACT));

        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Error: Valor no válido.", Toast.LENGTH_SHORT).show();
        }
    }
}
