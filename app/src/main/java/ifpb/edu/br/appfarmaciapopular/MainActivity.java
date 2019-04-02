package ifpb.edu.br.appfarmaciapopular;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import ifpb.edu.br.appfarmaciapopular.services.DatabaseTask;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapsActivity mapsActivity = new MapsActivity();
        mapsActivity.onMapReady(mapsActivity.getmMap());

        if (savedInstanceState == null) {
            // adicionar o fragmento inicial
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment, new FarmaciasFragment())
                    .addToBackStack(null)
                    .commit();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DatabaseTask databaseTask = new DatabaseTask(getApplicationContext());
        databaseTask.execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_farmacias) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, new FarmaciasFragment())
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_medicamentos) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, new MedicamentosFragment())
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_ouvidoria) {
            String numero = "136";
            Uri uri = Uri.parse("tel:" + numero);
            Intent intent = new Intent(Intent.ACTION_CALL, uri);
            startActivity(intent);
            Toast.makeText(this, "Realizando chamada de voz", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_adquirir) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, new ComoAdquirirFragment())
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_sobre) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, new SobreFragment())
                    .addToBackStack(null)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
