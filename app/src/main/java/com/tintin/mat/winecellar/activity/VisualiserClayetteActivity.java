package com.tintin.mat.winecellar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.util.Attributes;
import com.tintin.mat.winecellar.R;
import com.tintin.mat.winecellar.adapter.BouteilleAdapter;
import com.tintin.mat.winecellar.adapter.BouteilleSwipeAdapter;
import com.tintin.mat.winecellar.bo.Bouteille;
import com.tintin.mat.winecellar.bo.Cave;
import com.tintin.mat.winecellar.bo.Clayette;
import com.tintin.mat.winecellar.dao.BouteilleDao;
import com.tintin.mat.winecellar.dao.CaveDao;
import com.tintin.mat.winecellar.interfce.BouteilleInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by Mat & Audrey on 18/10/2017.
 */

public class VisualiserClayetteActivity extends AppCompatActivity implements BouteilleInterface {

    private ListView listeViewBouteilles;
    private BouteilleSwipeAdapter adapter;
    private Clayette clayette;
    private BouteilleDao bouteilleDao = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.visualiser_clayette_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiser_clayette);
        // récupération de la clayette passée en paramètre
        clayette = (Clayette) getIntent().getExtras().get("Key");
        // mettre ici le nom de la clayette passée en paramètre
        setTitle(clayette.getNom());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onResume(){
        afficherListeBouteilles();
        super.onResume();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.ajouter_bouteille:
                ajouterBouteille();
                return true;
            case R.id.modifier_cave:
                return true;
            case R.id.rechercher_bouteille :
                //TODO
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ajouterBouteille(){
        Intent appel = new Intent(this, AjouterBouteilleActivity.class);
        appel.putExtra("Key", (Serializable) clayette.getCave());
        startActivity(appel);
    }

    private void afficherListeBouteilles(){

        // récupérer les bouteilles
        // il faut recharger les bouteilles depuis la bdd pcq si on a mis à jour les bouteilles...
        BouteilleDao bouteilleDao = new BouteilleDao(this,null);
        ArrayList<Bouteille> listeBouteilles = bouteilleDao.getAllNotDegustedAssociatedWithClayette(clayette);

        listeViewBouteilles = (ListView)findViewById(R.id.listeBouteilles);
        TextView textBouteilleNb = (TextView) findViewById(R.id.textBouteilleNb);

        if (listeBouteilles == null || listeBouteilles.size() == 0){
            textBouteilleNb.setText(R.string.no_bouteille);
            textBouteilleNb.setVisibility(View.VISIBLE);
            listeViewBouteilles.setVisibility(View.INVISIBLE);
        }else {

            adapter = new BouteilleSwipeAdapter(VisualiserClayetteActivity.this, listeBouteilles, this);
            listeViewBouteilles.setAdapter(adapter);
            adapter.setMode(Attributes.Mode.Single);

            listeViewBouteilles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   // ((SwipeLayout)(listeViewBouteilles.getChildAt(position - listeViewBouteilles.getFirstVisiblePosition()))).open(true);

                    Bouteille b = (Bouteille) adapter.getItem(position);

                    Intent intent = new Intent(VisualiserClayetteActivity.this, ModifierBouteilleActivity.class);
                    //based on item add info to intent
                    intent.putExtra("Key", (Serializable) b);
                    startActivity(intent);

                }
            });
            listeViewBouteilles.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //Log.e("ListView", "OnTouch");
                    return false;
                }
            });
            listeViewBouteilles.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(getApplicationContext(), "OnItemLongClickListener", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
            listeViewBouteilles.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    //Log.e("ListView", "onScrollStateChanged");
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });

            listeViewBouteilles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //Log.e("ListView", "onItemSelected:" + position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //Log.e("ListView", "onNothingSelected:");
                }
            });

            textBouteilleNb.setVisibility(View.INVISIBLE);
            listeViewBouteilles.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void degusterBouteille(Bouteille bouteille) {

        Date today = new Date(); // Fri Jun 17 14:54:28 PDT 2016
        Calendar cal = Calendar.getInstance();
        cal.setTime(today); // don't forget this if date is arbitrary e.g. 01-01-2014
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH); // 17
        int monthOfYear = cal.get(Calendar.MONTH); // 5
        int year = cal.get(Calendar.YEAR); // 2016

        String monthString = ""+ (monthOfYear + 1);
        String dayString = ""+ dayOfMonth;
        if (monthOfYear + 1 <10 ){
            monthString = "0"+(monthOfYear+1);
        }
        if (dayOfMonth <10 ){
            dayString = "0"+dayOfMonth;
        }
        int dateDegustationIntFormat = new Integer("" + year + monthString + dayString);

        bouteille.setAnneeDegustation(dateDegustationIntFormat);
        BouteilleDao bouteilleDao = new BouteilleDao(this,null);
        bouteilleDao.modifierAnneeDegustation(bouteille);

        onResume();
    }
}