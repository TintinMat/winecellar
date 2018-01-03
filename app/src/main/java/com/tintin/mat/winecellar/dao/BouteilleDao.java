package com.tintin.mat.winecellar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.tintin.mat.winecellar.bo.Appellation;
import com.tintin.mat.winecellar.bo.Bouteille;
import com.tintin.mat.winecellar.bo.Cave;
import com.tintin.mat.winecellar.bo.Clayette;
import com.tintin.mat.winecellar.bo.Couleur;
import com.tintin.mat.winecellar.bo.Millesime;
import com.tintin.mat.winecellar.bo.Pays;
import com.tintin.mat.winecellar.bo.Petillant;
import com.tintin.mat.winecellar.bo.Region;

import java.util.ArrayList;

/**
 * Created by Mat & Audrey on 15/10/2017.
 */

public class BouteilleDao extends DAOBase {

    public static final String TABLE_NAME = "bouteille";
    public static final String KEY = "id";
    public static final String DOMAINE = "domaine";
    public static final String MILLESIME = "millesime";
    public static final String COULEUR = "couleur";
    public static final String PETILLANT = "petillant";
    public static final String PRIX = "prix";
    public static final String LIEUDACHAT = "lieuDachat";
    public static final String DATEDACHAT = "dateDachat";
    public static final String ANNEEDEGUSTATION = "anneeDegustation";
    public static final String COMMENTAIRES = "commentaires";
    public static final String BIO = "bio";
    public static final String PHOTO = "photo";
    public static final String APOGEEMIN = "apogeeMin";
    public static final String APOGEEMAX = "apogeeMax";
    public static final String FK_CLAYETTE = "clayette_id";
    public static final String FK_APPELLATION = "appellation_id";


    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DOMAINE + " TEXT, " + MILLESIME + " INTEGER, " +
            COULEUR + " INTEGER, " + PETILLANT + " INTEGER, " + PRIX + " REAL, " + LIEUDACHAT + " TEXT, " + DATEDACHAT + " INTEGER, " +  ANNEEDEGUSTATION + " INTEGER, " +
            BIO + " INTEGER, " + COMMENTAIRES + " TEXT, " + PHOTO + " BLOB,  " + APOGEEMIN + " INTEGER, " + APOGEEMAX + " INTEGER, " +
            FK_CLAYETTE + " INTEGER, " + FK_APPELLATION + " INTEGER, " +
            "FOREIGN KEY("+ FK_CLAYETTE +") REFERENCES "+ ClayetteDao.TABLE_NAME +"("+ ClayetteDao.KEY +")" +
            "FOREIGN KEY("+ FK_APPELLATION +") REFERENCES "+ AppellationDao.TABLE_NAME +"("+ AppellationDao.KEY +")" +
            ");";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public BouteilleDao(Context pContext, DatabaseHandler databaseHandler) {
        super(pContext, databaseHandler);
    }

    /**
     * @param bouteille la cave à ajouter à la base
     */
    public long ajouter(Bouteille bouteille) {
        long ret_value = -1;

        ContentValues values = new ContentValues();
        if (bouteille.getDomaine() != null && bouteille.getDomaine().length() > 0) {
            values.put(DOMAINE, bouteille.getDomaine());
        }
        if (bouteille.getMillesime() != null && bouteille.getMillesime().getAnnee() != 0) {
            values.put(MILLESIME, bouteille.getMillesime().getAnnee());
        }
        if (bouteille.getCouleur() != null){
            values.put(COULEUR, bouteille.getCouleur().getId());
        }
        if (bouteille.getPetillant() != null) {
            values.put(PETILLANT, bouteille.getPetillant().getId());
        }
        if (bouteille.getPrix() > 0) {
            values.put(PRIX, bouteille.getPrix());
        }
        if (bouteille.getLieuDachat() != null && bouteille.getLieuDachat().length() > 0) {
            values.put(LIEUDACHAT, bouteille.getLieuDachat());
        }
        if (bouteille.getCommentaires() != null && bouteille.getCommentaires().length() > 0) {
            values.put(COMMENTAIRES, bouteille.getCommentaires());
        }
        if (bouteille.isBio()) {
            values.put(BIO, 1);
        }else {
            values.put(BIO, 0);
        }
        if (bouteille.getClayette() != null) {
            values.put(FK_CLAYETTE, bouteille.getClayette().getId());
        }
        if (bouteille.getPhoto() != null) {
            values.put(PHOTO, bouteille.getPhoto());
        }
        if (bouteille.getApogeeMin() > 0){
            values.put(APOGEEMIN, bouteille.getApogeeMin());
        }
        if (bouteille.getApogeeMax() > 0){
            values.put(APOGEEMAX, bouteille.getApogeeMax());
        }
        values.put(ANNEEDEGUSTATION, bouteille.getAnneeDegustation());
        values.put(DATEDACHAT, bouteille.getDateDachat());
        values.put(FK_APPELLATION, bouteille.getAppellation().getId());
        open();
        ret_value = mDb.insert(TABLE_NAME, null, values);
        close();
        return ret_value;
    }

    public long modifier(Bouteille bouteille){
        long ret_value = -1;

        ContentValues values = new ContentValues();
        if (bouteille.getDomaine() != null && bouteille.getDomaine().length() > 0) {
            values.put(DOMAINE, bouteille.getDomaine());
        }
        if (bouteille.getMillesime() != null && bouteille.getMillesime().getAnnee() != 0) {
            values.put(MILLESIME, bouteille.getMillesime().getAnnee());
        }
        if (bouteille.getCouleur() != null){
            values.put(COULEUR, bouteille.getCouleur().getId());
        }
        if (bouteille.getPetillant() != null) {
            values.put(PETILLANT, bouteille.getPetillant().getId());
        }
        if (bouteille.getPrix() > 0) {
            values.put(PRIX, bouteille.getPrix());
        }
        if (bouteille.getLieuDachat() != null && bouteille.getLieuDachat().length() > 0) {
            values.put(LIEUDACHAT, bouteille.getLieuDachat());
        }
        if (bouteille.getCommentaires() != null && bouteille.getCommentaires().length() > 0) {
            values.put(COMMENTAIRES, bouteille.getCommentaires());
        }
        if (bouteille.isBio()) {
            values.put(BIO, 1);
        }else {
            values.put(BIO, 0);
        }
        if (bouteille.getClayette() != null) {
            values.put(FK_CLAYETTE, bouteille.getClayette().getId());
        }
        if (bouteille.getPhoto() != null) {
            values.put(PHOTO, bouteille.getPhoto());
        }
        if (bouteille.getApogeeMin() > 0){
            values.put(APOGEEMIN, bouteille.getApogeeMin());
        }
        if (bouteille.getApogeeMax() > 0){
            values.put(APOGEEMAX, bouteille.getApogeeMax());
        }
        values.put(ANNEEDEGUSTATION, bouteille.getAnneeDegustation());
        values.put(DATEDACHAT, bouteille.getDateDachat());
        values.put(FK_APPELLATION, bouteille.getAppellation().getId());
        open();
        ret_value = mDb.update(TABLE_NAME, values, KEY  + " = ?", new String[] {String.valueOf(bouteille.getId())});
        close();
        return ret_value;

    }

    public long modifierAnneeDegustation(Bouteille bouteille) {
        long ret_value = -1;

        ContentValues values = new ContentValues();
        values.put(ANNEEDEGUSTATION, bouteille.getAnneeDegustation());
        open();
        ret_value = mDb.update(TABLE_NAME, values, KEY  + " = ?", new String[] {String.valueOf(bouteille.getId())});
        close();
        return ret_value;
    }

    public long nbBouteilles(){
        long cnt = 0;
        open();
        cnt  = DatabaseUtils.queryNumEntries(mDb, TABLE_NAME);
        close();
        return cnt;
    }

    public ArrayList<Bouteille> getAllNotDegustedAssociatedWithCave(Cave cave){
        ArrayList<Bouteille> listeBouteilles = new ArrayList<Bouteille>();
        open();
        String whereClause = " WHERE "+TABLE_NAME+"."+FK_CLAYETTE+"="+ClayetteDao.TABLE_NAME+"."+ClayetteDao.KEY +
                " AND "+ClayetteDao.TABLE_NAME+"."+ClayetteDao.FK_CAVE+"="+cave.getId() +
                " AND "+AppellationDao.TABLE_NAME+"."+AppellationDao.KEY+"="+TABLE_NAME+"."+FK_APPELLATION ;
        Cursor cursor = mDb.rawQuery( "SELECT "+TABLE_NAME+".*, "+ AppellationDao.TABLE_NAME+"."+AppellationDao.NOM +
                " FROM " + TABLE_NAME+", "+ ClayetteDao.TABLE_NAME + ", "+ AppellationDao.TABLE_NAME + whereClause, null);
        if (cursor != null && cursor.getCount() >0 && cursor.moveToFirst()) {
            do {
                if (cursor.getInt(cursor.getColumnIndex(ANNEEDEGUSTATION)) == 0) {
                    Bouteille bouteille = new Bouteille();
                    bouteille.setId(cursor.getInt(cursor.getColumnIndex(KEY)));
                    bouteille.setPhoto(cursor.getBlob(cursor.getColumnIndex(PHOTO)));
                    bouteille.setDomaine(cursor.getString(cursor.getColumnIndex(DOMAINE)));
                    if (cursor.getInt(cursor.getColumnIndex(MILLESIME)) > 0) {
                        bouteille.setMillesime(new Millesime(cursor.getInt(cursor.getColumnIndex(MILLESIME))));
                    }
                    Appellation appellation = new Appellation();
                    appellation.setNom(cursor.getString(cursor.getColumnIndex(AppellationDao.NOM)));
                    bouteille.setAppellation(appellation);
                    listeBouteilles.add(bouteille);
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return listeBouteilles;
    }


    public ArrayList<Bouteille> getAllNotDegustedAssociatedWithClayette(Clayette clayette){
        ArrayList<Bouteille> listeBouteilles = new ArrayList<Bouteille>();
        open();
        String whereClause = " WHERE "+TABLE_NAME+"."+FK_CLAYETTE+"="+ClayetteDao.TABLE_NAME+"."+ClayetteDao.KEY +
                " AND "+ClayetteDao.TABLE_NAME+"."+ClayetteDao.KEY+"="+clayette.getId() +
                " AND "+AppellationDao.TABLE_NAME+"."+AppellationDao.KEY+"="+TABLE_NAME+"."+FK_APPELLATION ;
        Cursor cursor = mDb.rawQuery( "SELECT "+TABLE_NAME+".*, "+ AppellationDao.TABLE_NAME+"."+AppellationDao.NOM +
                " FROM " + TABLE_NAME+", "+ ClayetteDao.TABLE_NAME + ", "+ AppellationDao.TABLE_NAME + whereClause, null);
        if (cursor != null && cursor.getCount() >0 && cursor.moveToFirst()) {
            do {
                if (cursor.getInt(cursor.getColumnIndex(ANNEEDEGUSTATION)) == 0) {
                    Bouteille bouteille = new Bouteille();
                    bouteille.setId(cursor.getInt(cursor.getColumnIndex(KEY)));
                    bouteille.setPhoto(cursor.getBlob(cursor.getColumnIndex(PHOTO)));
                    bouteille.setDomaine(cursor.getString(cursor.getColumnIndex(DOMAINE)));
                    if (cursor.getInt(cursor.getColumnIndex(MILLESIME)) > 0) {
                        bouteille.setMillesime(new Millesime(cursor.getInt(cursor.getColumnIndex(MILLESIME))));
                    }
                    Appellation appellation = new Appellation();
                    appellation.setNom(cursor.getString(cursor.getColumnIndex(AppellationDao.NOM)));
                    bouteille.setAppellation(appellation);
                    listeBouteilles.add(bouteille);
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return listeBouteilles;
    }

    public ArrayList<Bouteille> getAllDegusted() {
        ArrayList<Bouteille> listeBouteilles = new ArrayList<Bouteille>();
        open();
        String whereClause = " WHERE " + AppellationDao.TABLE_NAME + "." + AppellationDao.KEY + "=" + TABLE_NAME + "." + FK_APPELLATION ;
        String orderByClause = " ORDER BY " + TABLE_NAME + "." + ANNEEDEGUSTATION + " DESC " ;
        Cursor cursor = mDb.rawQuery("SELECT " + TABLE_NAME + ".*, " + AppellationDao.TABLE_NAME + "." + AppellationDao.NOM +
                " FROM " + TABLE_NAME + ", " + AppellationDao.TABLE_NAME + whereClause, null);
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                if (cursor.getInt(cursor.getColumnIndex(ANNEEDEGUSTATION)) > 0) {
                    Bouteille bouteille = new Bouteille();
                    bouteille.setId(cursor.getInt(cursor.getColumnIndex(KEY)));
                    bouteille.setPhoto(cursor.getBlob(cursor.getColumnIndex(PHOTO)));
                    bouteille.setDomaine(cursor.getString(cursor.getColumnIndex(DOMAINE)));
                    bouteille.setAnneeDegustation(cursor.getInt(cursor.getColumnIndex(ANNEEDEGUSTATION)));
                    if (cursor.getInt(cursor.getColumnIndex(MILLESIME)) > 0) {
                        bouteille.setMillesime(new Millesime(cursor.getInt(cursor.getColumnIndex(MILLESIME))));
                    }
                    Appellation appellation = new Appellation();
                    appellation.setNom(cursor.getString(cursor.getColumnIndex(AppellationDao.NOM)));
                    bouteille.setAppellation(appellation);
                    listeBouteilles.add(bouteille);
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return listeBouteilles;
    }


    public Bouteille getWithAllDependencies(Bouteille b){

        open();

        String query1 = "SELECT "+TABLE_NAME+".*, "+
                AppellationDao.TABLE_NAME+"."+AppellationDao.KEY + " as "+AppellationDao.TABLE_NAME+"_"+AppellationDao.KEY + ", " +
                AppellationDao.TABLE_NAME+"."+AppellationDao.NOM + " as "+AppellationDao.TABLE_NAME+"_"+AppellationDao.NOM + ", " +
                AppellationDao.TABLE_NAME+"."+AppellationDao.FK_REGION + " as "+AppellationDao.TABLE_NAME+"_"+AppellationDao.FK_REGION + ", " +
                RegionDao.TABLE_NAME+"."+RegionDao.KEY + " as "+RegionDao.TABLE_NAME+"_"+RegionDao.KEY + ", " +
                RegionDao.TABLE_NAME+"."+RegionDao.NOM + " as "+RegionDao.TABLE_NAME+"_"+RegionDao.NOM + ", " +
                RegionDao.TABLE_NAME+"."+RegionDao.FK_PAYS + " as "+RegionDao.TABLE_NAME+"_"+RegionDao.FK_PAYS + ", " +
                PaysDao.TABLE_NAME+"."+PaysDao.KEY + " as "+PaysDao.TABLE_NAME+"_"+PaysDao.KEY + ", " +
                PaysDao.TABLE_NAME+"."+PaysDao.NOM + " as "+PaysDao.TABLE_NAME+"_"+PaysDao.NOM
                ;
        String query2 = ", " + ClayetteDao.TABLE_NAME+"."+ClayetteDao.KEY + " as "+ClayetteDao.TABLE_NAME+"_"+ClayetteDao.KEY + ", " +
                ClayetteDao.TABLE_NAME+"."+ClayetteDao.NOM + " as "+ClayetteDao.TABLE_NAME+"_"+ClayetteDao.NOM + ", " +
                ClayetteDao.TABLE_NAME+"."+ClayetteDao.FK_CAVE + " as "+ClayetteDao.TABLE_NAME+"_"+ClayetteDao.FK_CAVE + ", " +
                CaveDao.TABLE_NAME+"."+CaveDao.KEY + " as "+CaveDao.TABLE_NAME+"_"+CaveDao.KEY + ", " +
                CaveDao.TABLE_NAME+"."+CaveDao.NOM + " as "+CaveDao.TABLE_NAME+"_"+CaveDao.NOM + ", " +
                CaveDao.TABLE_NAME+"."+CaveDao.NBBOUTEILLES + " as "+CaveDao.TABLE_NAME+"_"+CaveDao.NBBOUTEILLES ;
        String from1 = " FROM " +
                TABLE_NAME+", "+  AppellationDao.TABLE_NAME + ", "+RegionDao.TABLE_NAME
                + ", " + PaysDao.TABLE_NAME;
        String from2 = ", " + ClayetteDao.TABLE_NAME +", "+ CaveDao.TABLE_NAME ;
        String whereClause1 = " WHERE "+AppellationDao.TABLE_NAME+"."+AppellationDao.KEY+"="+TABLE_NAME+"."+FK_APPELLATION +
                " AND "+AppellationDao.TABLE_NAME+"."+AppellationDao.FK_REGION+"="+RegionDao.TABLE_NAME+"."+RegionDao.KEY +
                " AND "+RegionDao.TABLE_NAME+"."+RegionDao.FK_PAYS+"="+PaysDao.TABLE_NAME+"."+PaysDao.KEY +
                " AND "+TABLE_NAME+"."+KEY+"="+b.getId();

        String whereClause2 = " AND "+TABLE_NAME+"."+FK_CLAYETTE+"="+ClayetteDao.TABLE_NAME+"."+ClayetteDao.KEY +
                " AND "+ClayetteDao.TABLE_NAME+"."+ClayetteDao.FK_CAVE+"="+CaveDao.TABLE_NAME+"."+CaveDao.KEY;

        String query = query1+query2;
        String from = from1+from2;
        String whereClause = whereClause1+whereClause2;

        Cursor cursor = mDb.rawQuery( query + from + whereClause, null);
        Bouteille bouteille = null;
        if (cursor != null && cursor.getCount() >0 && cursor.moveToFirst()) {
            bouteille = new Bouteille();
            bouteille.setId(cursor.getInt(cursor.getColumnIndex(KEY)));
            bouteille.setPhoto(cursor.getBlob(cursor.getColumnIndex(PHOTO)));
            bouteille.setDomaine(cursor.getString(cursor.getColumnIndex(DOMAINE)));
            if (cursor.getInt(cursor.getColumnIndex(MILLESIME)) > 0) {
                bouteille.setMillesime(new Millesime(cursor.getInt(cursor.getColumnIndex(MILLESIME))));
            }
            Pays p = new Pays(cursor.getString(cursor.getColumnIndex(PaysDao.TABLE_NAME+"_"+PaysDao.NOM)));
            p.setId(cursor.getInt(cursor.getColumnIndex(PaysDao.TABLE_NAME+"_"+PaysDao.KEY)));
            Region r = new Region();
            r.setId(cursor.getInt(cursor.getColumnIndex(RegionDao.TABLE_NAME+"_"+RegionDao.KEY)));
            r.setNom(cursor.getString(cursor.getColumnIndex(RegionDao.TABLE_NAME+"_"+RegionDao.NOM)));
            r.setPays(p);
            Appellation appellation = new Appellation();
            appellation.setNom(cursor.getString(cursor.getColumnIndex(AppellationDao.TABLE_NAME+"_"+AppellationDao.NOM)));
            appellation.setId(cursor.getInt(cursor.getColumnIndex(AppellationDao.TABLE_NAME+"_"+AppellationDao.KEY)));
            bouteille.setAppellation(appellation);
            appellation.setRegion(r);
            Cave ca = new Cave();
            ca.setId(cursor.getInt(cursor.getColumnIndex(CaveDao.TABLE_NAME+"_"+CaveDao.KEY)));
            ca.setNom(cursor.getString(cursor.getColumnIndex(CaveDao.TABLE_NAME+"_"+CaveDao.NOM)));
            ca.setNbBouteillesTheoriques(cursor.getColumnIndex(CaveDao.TABLE_NAME+"_"+CaveDao.NBBOUTEILLES));
            Clayette c = new Clayette();
            c.setId(cursor.getInt(cursor.getColumnIndex(ClayetteDao.TABLE_NAME+"_"+ClayetteDao.KEY)));
            c.setNom(cursor.getString(cursor.getColumnIndex(ClayetteDao.TABLE_NAME+"_"+ClayetteDao.NOM)));
            c.setCave(ca);
            bouteille.setClayette(c);
            bouteille.setApogeeMin(cursor.getInt(cursor.getColumnIndex(APOGEEMIN)));
            bouteille.setApogeeMax(cursor.getInt(cursor.getColumnIndex(APOGEEMAX)));
            bouteille.setAnneeDegustation(cursor.getInt(cursor.getColumnIndex(ANNEEDEGUSTATION)));
            bouteille.setBio(false);
            if (cursor.getInt(cursor.getColumnIndex(BIO)) == 1){
                bouteille.setBio(true);
            }
            bouteille.setCommentaires(cursor.getString(cursor.getColumnIndex(COMMENTAIRES)));
            Couleur cou = new Couleur();
            cou.setId(cursor.getInt(cursor.getColumnIndex(COULEUR)));
            cou.setNom(Couleur.getNom(cou.getId()));
            bouteille.setCouleur(cou);
            bouteille.setDateDachat(cursor.getInt(cursor.getColumnIndex(DATEDACHAT)));
            bouteille.setLieuDachat(cursor.getString(cursor.getColumnIndex(LIEUDACHAT)));
            Petillant pe = new Petillant();
            pe.setId(cursor.getInt(cursor.getColumnIndex(PETILLANT)));
            pe.setNom(Petillant.getNom(pe.getId()));
            bouteille.setPetillant(pe);
            bouteille.setPrix(cursor.getFloat(cursor.getColumnIndex(PRIX)));

        }else {
            // la cave n'existe plus, on refait la requete sans la cave
            cursor = mDb.rawQuery( query1 + from1 + whereClause1, null);

            if (cursor != null && cursor.getCount() >0 && cursor.moveToFirst()) {
                bouteille = new Bouteille();
                bouteille.setId(cursor.getInt(cursor.getColumnIndex(KEY)));
                bouteille.setPhoto(cursor.getBlob(cursor.getColumnIndex(PHOTO)));
                bouteille.setDomaine(cursor.getString(cursor.getColumnIndex(DOMAINE)));
                if (cursor.getInt(cursor.getColumnIndex(MILLESIME)) > 0) {
                    bouteille.setMillesime(new Millesime(cursor.getInt(cursor.getColumnIndex(MILLESIME))));
                }
                Pays p = new Pays(cursor.getString(cursor.getColumnIndex(PaysDao.TABLE_NAME+"_"+PaysDao.NOM)));
                p.setId(cursor.getInt(cursor.getColumnIndex(PaysDao.TABLE_NAME+"_"+PaysDao.KEY)));
                Region r = new Region();
                r.setId(cursor.getInt(cursor.getColumnIndex(RegionDao.TABLE_NAME+"_"+RegionDao.KEY)));
                r.setNom(cursor.getString(cursor.getColumnIndex(RegionDao.TABLE_NAME+"_"+RegionDao.NOM)));
                r.setPays(p);
                Appellation appellation = new Appellation();
                appellation.setNom(cursor.getString(cursor.getColumnIndex(AppellationDao.TABLE_NAME+"_"+AppellationDao.NOM)));
                appellation.setId(cursor.getInt(cursor.getColumnIndex(AppellationDao.TABLE_NAME+"_"+AppellationDao.KEY)));
                bouteille.setAppellation(appellation);
                appellation.setRegion(r);
                bouteille.setApogeeMin(cursor.getInt(cursor.getColumnIndex(APOGEEMIN)));
                bouteille.setApogeeMax(cursor.getInt(cursor.getColumnIndex(APOGEEMAX)));
                bouteille.setAnneeDegustation(cursor.getInt(cursor.getColumnIndex(ANNEEDEGUSTATION)));
                bouteille.setBio(false);
                if (cursor.getInt(cursor.getColumnIndex(BIO)) == 1){
                    bouteille.setBio(true);
                }
                bouteille.setCommentaires(cursor.getString(cursor.getColumnIndex(COMMENTAIRES)));
                Couleur cou = new Couleur();
                cou.setId(cursor.getInt(cursor.getColumnIndex(COULEUR)));
                cou.setNom(Couleur.getNom(cou.getId()));
                bouteille.setCouleur(cou);
                bouteille.setDateDachat(cursor.getInt(cursor.getColumnIndex(DATEDACHAT)));
                bouteille.setLieuDachat(cursor.getString(cursor.getColumnIndex(LIEUDACHAT)));
                Petillant pe = new Petillant();
                pe.setId(cursor.getInt(cursor.getColumnIndex(PETILLANT)));
                pe.setNom(Petillant.getNom(pe.getId()));
                bouteille.setPetillant(pe);
                bouteille.setPrix(cursor.getFloat(cursor.getColumnIndex(PRIX)));
            }
        }
        cursor.close();
        close();
        return bouteille;
    }

    public int supprimer(Bouteille bouteille){
        open();
        int ret = mDb.delete(TABLE_NAME, " "+KEY+"=?", new String[]{new Long(bouteille.getId()).toString()});
        close();
        return ret;
    }

}