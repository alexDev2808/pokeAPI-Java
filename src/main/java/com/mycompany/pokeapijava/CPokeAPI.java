/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pokeapijava;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.json.JSONObject;

/**
 *
 * @author alexh2808
 */
public class CPokeAPI {
    
    public void MostrarPokemon(
            JTable tbPokemon, 
            JTextField buscador,
            JTextField nombre,
            JTextField peso,
            JTextField altura,
            JTextField expBase,
            JLabel foto
            ) {
        
        DefaultTableModel modelo = new DefaultTableModel();
        String[] colNames = {"Nombre", "Peso", "Altura"};
        modelo.setColumnIdentifiers(colNames);
        
        tbPokemon.setModel(modelo);
        
        try {
            
            URL url = new URL("https://pokeapi.co/api/v2/pokemon/" + buscador.getText());
            
            HttpURLConnection conn = (HttpURLConnection)url.openConnection(); // abrir conexion
            
            conn.setRequestMethod("GET");
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            
            while((line = reader.readLine()) != null) {
                response.append(line);
            }
            
            reader.close();
            
            JSONObject jsonObj = new JSONObject(response.toString());
            
            String name = jsonObj.getString("name");
            int weight = jsonObj.getInt("weight");
            int heigth = jsonObj.getInt("height");
            int experience = jsonObj.getInt("base_experience");
            
            modelo.addRow(new Object[]{name, weight, heigth});
            
            nombre.setText(name);
            peso.setText(String.valueOf(weight));
            altura.setText(String.valueOf(heigth));
            expBase.setText(String.valueOf(experience));
            
            String imgURL = jsonObj.getJSONObject("sprites").getString("front_default");
            
            ImageIcon icon = new ImageIcon(new URL(imgURL));
            
            foto.setIcon(icon);
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ingrese solo id o nombre del pokemon existente, error: " + e);
        }
        
        
    }
    
}
