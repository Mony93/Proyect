package miprimerapp.proyecto.android.proyect;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    EditText codigo, nombre, precio, descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        codigo = (EditText) findViewById(R.id.cod);
        nombre = (EditText) findViewById(R.id.nom);
        precio = (EditText) findViewById(R.id.pre);
        descripcion = (EditText) findViewById(R.id.des);
    }

    public void registrar(View v) {
        almacen admin = new almacen(this, "almacen", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String Codigo = codigo.getText().toString();
        String Nombre = nombre.getText().toString();
        String Precio = precio.getText().toString();
        String Descripcion = descripcion.getText().toString();

        ContentValues registro = new ContentValues();

        registro.put("codigo", Codigo);
        registro.put("nombre", Nombre);
        registro.put("precio", Precio);
        registro.put("descripcion", Descripcion);


        bd.insert("almacen", null, registro);
        bd.close();

        codigo.setText("");
        nombre.setText("");
        precio.setText("");
        descripcion.setText("");

        Toast.makeText(this, "Se agrego un nuevo paciente al registro", Toast.LENGTH_SHORT).show();
    }

    public void buscar(View v) {

        almacen admin = new almacen(this, "almacen", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String Codigo = codigo.getText().toString();

        Cursor fila = bd.rawQuery("select codigo, nombre, precio, descripcion from almacen where codigo=" + Codigo, null);
        if (fila.moveToFirst()) {
            codigo.setText(fila.getString(0));
            nombre.setText(fila.getString(1));
            precio.setText(fila.getString(2));
            descripcion.setText(fila.getString(3));

        } else {
            Toast.makeText(this, "No existe el registro", Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }


    public void borrar(View v) {

        almacen admin = new almacen(this, "almacen", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String Codigo = codigo.getText().toString();

        int cant = bd.delete("almacen", "codigo=" + Codigo, null);
        bd.close();

        codigo.setText("");
        nombre.setText("");
        precio.setText("");
        descripcion.setText("");


        if (cant == 1) {
            Toast.makeText(this, "Se elimino el registro", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No existe el registro", Toast.LENGTH_SHORT).show();
        }
    }


    public void modificar(View v) {
        almacen admin = new almacen(this, "almacen", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String Codigo = codigo.getText().toString();
        String Nombre = nombre.getText().toString();
        String Precio = precio.getText().toString();
        String Descripcion = descripcion.getText().toString();


        ContentValues registro = new ContentValues();

        registro.put("codigo", Codigo);
        registro.put("nombre", Nombre);
        registro.put("precio", Precio);
        registro.put("descripcion", Descripcion);


        int cant = bd.update("almacen", registro, "codigo=" + Codigo, null);
        bd.close();

        if (cant == 1) {
            Toast.makeText(this, "Se modifico el registro del paciente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No existe el registro del paciente", Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiar(View v) {
        codigo.setText("");
        nombre.setText("");
        precio.setText("");
        descripcion.setText("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, acercade.class);
            startActivity(intent);




        }
        if (id == R.id.salir) {

            finish();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
