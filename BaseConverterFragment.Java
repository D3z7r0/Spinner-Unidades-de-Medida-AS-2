package com.example.proyectofinal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public abstract class BaseConverterFragment extends Fragment {

    protected EditText editValor;
    protected Spinner spinnerUnidad;
    protected TextView txtResultados;
    protected TextView txtTitulo;

    // Métodos abstractos que las hijas deben implementar
    protected abstract String[] getUnits();
    protected abstract double[] getConversionFactors(); // Factores relativos a una unidad base
    protected abstract String getCategoryName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragmento__botones, container, false);

        editValor = view.findViewById(R.id.editValor);
        spinnerUnidad = view.findViewById(R.id.spinnerUnidad);
        txtResultados = view.findViewById(R.id.txtResultados);
        txtTitulo = view.findViewById(R.id.txtTituloCategoria);
        Button btnCalcular = view.findViewById(R.id.btnCalcular);

        // Configurar Título
        txtTitulo.setText(getCategoryName());

        // Configurar Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, getUnits());
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

        // Se envuelve la conversión en try-catch para manejar valores no numéricos
        try {
            double inputValue = Double.parseDouble(inputStr);
            int selectedIndex = spinnerUnidad.getSelectedItemPosition();

            double[] factors = getConversionFactors();
            String[] units = getUnits();

            // 1. Convertir la entrada a la unidad BASE (la que tenga factor 1.0)
            // Fórmula: ValorBase = ValorEntrada / FactorEntrada
            double baseValue = inputValue / factors[selectedIndex];

            // 2. Usar StringBuilder para construir el reporte
            StringBuilder sb = new StringBuilder();
            sb.append("Conversión de ").append(inputValue).append(" ").append(units[selectedIndex]).append(":\n\n");

            for (int i = 0; i < units.length; i++) {
                // Convertir de Base a Destino: ValorDestino = ValorBase * FactorDestino
                double convertedValue = baseValue * factors[i];

                // Formatear a 4 decimales
                sb.append(String.format("• %.4f %s\n", convertedValue, units[i]));
            }

            // 3. Desplegar
            txtResultados.setText(sb.toString());

        } catch (NumberFormatException e) {
            // Muestra un mensaje al usuario si el formato de entrada no es correcto
            Toast.makeText(getContext(), "Error: Por favor, ingrese un número decimal válido.", Toast.LENGTH_LONG).show();
            txtResultados.setText("Error en la entrada de valor.");
        }
    }
}
