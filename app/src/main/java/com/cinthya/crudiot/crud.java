package com.cinthya.crudiot;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class crud extends AppCompatActivity {

    Toolbar menuCRUD;
    EditText  fMatricula, fNombre, fCorreo, fTelefono, fDireccion;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    ListView lvAlumnos;
    List<alumnos> listaDeAlumnos = new ArrayList<alumnos>();
    ArrayAdapter<alumnos> alumnosArrayAdapter;
    alumnos aluSelected;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crud);



        //Menu
        menuCRUD = findViewById(R.id.menuCRUD);
        setSupportActionBar(menuCRUD);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //Campos de texto
        fMatricula = findViewById(R.id.edMatricula);
        fNombre = findViewById(R.id.edNombre);
        fCorreo = findViewById(R.id.edCorreo);
        fTelefono = findViewById(R.id.edCorreo);
        fDireccion = findViewById(R.id.edDireccion);

        //Iniciar y conectar con Firebase
        iniciarFirebase();

        lvAlumnos = findViewById(R.id.lvalus);

        rellenarLista();

        lvAlumnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                aluSelected = (alumnos) parent.getItemAtPosition(position);
                fMatricula.setText(aluSelected.getMatricula());
                fNombre.setText(aluSelected.getNombre());
                fCorreo.setText(aluSelected.getCorreo());
                fTelefono.setText(String.valueOf(aluSelected.getTelefono()));
                fDireccion.setText(aluSelected.getDireccion());
            }
        });
    }

    private void rellenarLista() {
        databaseReference.child("alumnos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaDeAlumnos.clear();
                for (DataSnapshot consultarAlumnos : snapshot.getChildren()){
                    alumnos alumnosInFB =  consultarAlumnos.getValue(alumnos.class);
                    listaDeAlumnos.add(alumnosInFB);
                    alumnosArrayAdapter = new ArrayAdapter<alumnos>(crud.this, android.R.layout.simple_list_item_1, listaDeAlumnos);
                    lvAlumnos.setAdapter(alumnosArrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    //FUNCIONES
    //Fucion para conectar con Firebase
    private void iniciarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    //Funcion para comprobrar los campos
    private void comprobarCampos(){
        String Matricula = fMatricula.getText().toString();
        String Nombre = fNombre.getText().toString();
        String Correo = fCorreo.getText().toString();
        String Telefono = fTelefono.getText().toString();
        String Direccion = fDireccion.getText().toString();
        if (Matricula.isEmpty()){
            fMatricula.setError("Obligatorio");
        }else if (Nombre.isEmpty()){
            fNombre.setError("Obligatorio");
        }else if (Correo.isEmpty()){
            fCorreo.setError("Obligatorio");
        }else if (Telefono.isEmpty()){
            fTelefono.setError("Obligatorio");
        } else if (Direccion.isEmpty()){
            fDireccion.setError("Obligatorio");
        }
    }
    //Funcion para limpiar los campos
    private void limpiarCampos(){
        fMatricula.setText("");
        fNombre.setText("");
        fCorreo.setText("");
        fTelefono.setText("");
        fDireccion.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_crud, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int boton = item.getItemId();

        String Matricula = fMatricula.getText().toString();
        String Nombre = fNombre.getText().toString();
        String Correo = fCorreo.getText().toString();
        String Telefono = fTelefono.getText().toString();
        String Direccion = fDireccion.getText().toString();

        if(boton == R.id.action_clear) {
            limpiarCampos();
            Toast.makeText(this, "Limpiando...", Toast.LENGTH_SHORT).show();
            return true;
        } else if (boton == R.id.action_add) {
            if (Matricula.isEmpty() || Nombre.isEmpty() || Correo.isEmpty() || Telefono.isEmpty() || Direccion.isEmpty()) {
                comprobarCampos();
            } else {
                alumnos AddAlu = new alumnos();
                AddAlu.setAlu_ID(UUID.randomUUID().toString());
                AddAlu.setMatricula(Matricula);
                AddAlu.setNombre(Nombre);
                AddAlu.setCorreo(Correo);
                AddAlu.setTelefono(Long.parseLong(Telefono));
                AddAlu.setDireccion(Direccion);

                databaseReference.child("alumnos").child(AddAlu.getAlu_ID()).setValue(AddAlu);
                limpiarCampos();
                Toast.makeText(this, "Agragado!", Toast.LENGTH_SHORT).show();
                return true;
            }
        } else if (boton == R.id.action_edit) {
            if (aluSelected == null){
                Toast.makeText(this, "Seleccione un nombre", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (Matricula.isEmpty() || Nombre.isEmpty() || Correo.isEmpty() || Telefono.isEmpty() || Direccion.isEmpty()) {
                comprobarCampos();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Confirmar")
                        .setMessage("Al aceptar se reemplazara la informacion existente del usuario")
                        .setPositiveButton("Aceptar", (dialog, which) -> {
                            alumnos editAlu = new alumnos();

                            editAlu.setAlu_ID(aluSelected.getAlu_ID());
                            editAlu.setMatricula(fMatricula.getText().toString().trim());
                            editAlu.setNombre(fNombre.getText().toString().trim());
                            editAlu.setCorreo(fCorreo.getText().toString().trim());
                            editAlu.setTelefono(Long.parseLong(fTelefono.getText().toString().trim()));
                            editAlu.setDireccion(fDireccion.getText().toString().trim());

                            databaseReference.child("alumnos").child(editAlu.getAlu_ID()).setValue(editAlu);

                            Toast.makeText(this, "Actualizado", Toast.LENGTH_SHORT).show();
                            limpiarCampos();
                        })
                        .setNegativeButton("Cancelar", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();
                return true;

            }
            return true;
        } else if (boton == R.id.action_delete) {
            if (aluSelected == null){
                Toast.makeText(this, "Seleccione un nombre", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (Matricula.isEmpty() || Nombre.isEmpty() || Correo.isEmpty() || Telefono.isEmpty() || Direccion.isEmpty()) {
                comprobarCampos();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Eliminar")
                        .setMessage("¿Está seguro que desea eliminar este alumno?")
                        .setPositiveButton("Sí", (dialog, which) -> {
                            alumnos delAlu = new alumnos();
                            delAlu.setAlu_ID(aluSelected.getAlu_ID());

                            databaseReference.child("alumnos").child(delAlu.getAlu_ID()).removeValue();

                            Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show();

                            limpiarCampos();
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
