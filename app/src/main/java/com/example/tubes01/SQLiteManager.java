package com.example.tubes01;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SQLiteManager extends SQLiteOpenHelper
{
    private static SQLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "DokterDB";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Dokter";
    private static final String TABLE_NAME1 = "Pertemuan";
    private static final String COUNTER = "Counter";

    private static final String ID_DOKTER = "idDokter";
    private static final String NAMA_DOKTER = "title";
    private static final String SPESIALISASI = "desc";
    private static final String NO_HP= "noHp";
    private static final String DELETED= "deleted";

    private static final String ID_PERTEMUAN = "idPertemuan";
    private static final String PASIEN = "pasien";
    private static final String KELUHAN = "keluhan";
    private static final String TANGGAL= "tanggal";
    private static final String WAKTU= "waktu";
    private static final String DELETED1= "deleted";


    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    public SQLiteManager(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context)
    {
        if(sqLiteManager == null)
            sqLiteManager = new SQLiteManager(context);

        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {

        StringBuilder sql, sql1;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_DOKTER)
                .append(" INT, ")
                .append(NAMA_DOKTER)
                .append(" TEXT, ")
                .append(SPESIALISASI)
                .append(" TEXT, ")
                .append(NO_HP)
                .append(" TEXT, ")
                .append(DELETED)
                .append(" TEXT)");

        sqLiteDatabase.execSQL(sql.toString());
        sql1= new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME1)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_PERTEMUAN)
                .append(" INT, ")
                .append(PASIEN)
                .append(" TEXT, ")
                .append(ID_DOKTER)
                .append(" INT, ")
                .append(KELUHAN)
                .append(" TEXT, ")
                .append(TANGGAL)
                .append(" TEXT, ")
                .append(WAKTU)
                .append(" TEXT, ")
                .append(DELETED1)
                .append(" TEXT)");
        sqLiteDatabase.execSQL(sql1.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {

    }

    public void addDokterToDatabase(Dokter dokter)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_DOKTER, dokter.getId());
        contentValues.put(NAMA_DOKTER, dokter.getNama());
        contentValues.put(SPESIALISASI, dokter.getSpesialis());
        contentValues.put(NO_HP, dokter.getNoHp());
        contentValues.put(DELETED, getStringFromDate(dokter.getDeleted()));

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }
    public void addPertemuanToDatabase(Pertemuan pertemuan)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_PERTEMUAN, pertemuan.getId());
        contentValues.put(PASIEN, pertemuan.getPasien());
        contentValues.put(ID_DOKTER, pertemuan.getIdDokter());
        contentValues.put(KELUHAN, pertemuan.getKeluhan());
        contentValues.put(TANGGAL, pertemuan.getTanggal());
        contentValues.put(WAKTU, pertemuan.getWaktu());
        contentValues.put(DELETED1, getStringFromDate(pertemuan.getDeleted1()));

        sqLiteDatabase.insert(TABLE_NAME1, null, contentValues);
    }

    public void populateDokterListArray()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null))
        {
            if(result.getCount() != 0)
            {
                while (result.moveToNext())
                {
                    int idDokter = result.getInt(1);
                    String nama = result.getString(2);
                    String spesialis = result.getString(3);
                    String noHp = result.getString(4);
                    String stringDeleted = result.getString(5);
                    Date deleted= getDateFromString(stringDeleted);
                    Dokter dokter= new Dokter(idDokter,nama,spesialis,noHp, deleted);
                    Dokter.dokterArrayList.add(dokter);
                }
            }
        }
    }
    public void populatePertemuanListArray()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME1, null))
        {
            if(result.getCount() != 0)
            {
                while (result.moveToNext())
                {
                    int idPertemuan = result.getInt(1);
                    String pasien = result.getString(2);
                    int idDokter = result.getInt(3);
                    String keluhan = result.getString(4);
                    String tanggal = result.getString(5);
                    String waktu = result.getString(6);
                    String stringDeleted = result.getString(7);
                    Date deleted1= getDateFromString(stringDeleted);
                    Pertemuan pertemuan= new Pertemuan(idPertemuan, pasien, idDokter,keluhan,tanggal,waktu, deleted1);
                    Pertemuan.pertemuanArrayList.add(pertemuan);
                }
            }
        }
    }
    public ArrayList<Dokter> readCourses() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<Dokter> courseModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                courseModalArrayList.add(new Dokter(cursorCourses.getInt(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3),
                        cursorCourses.getString(4)));
            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();
        return courseModalArrayList;
    }

    public void updateDokterInDB(Dokter dokter)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_DOKTER, dokter.idDokter);
        contentValues.put(NAMA_DOKTER, dokter.nama);
        contentValues.put(SPESIALISASI, dokter.spesialis);
        contentValues.put(NO_HP, dokter.noHp);
        contentValues.put(DELETED, getStringFromDate(dokter.getDeleted()));

        sqLiteDatabase.update(TABLE_NAME, contentValues, ID_DOKTER + " =? ", new String[]{String.valueOf(dokter.getId())});
    }
    public void updatePertemuanInDB(Pertemuan pertemuan)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_DOKTER, pertemuan.idPertemuan);
        contentValues.put(KELUHAN, pertemuan.keluhan);
        contentValues.put(TANGGAL, pertemuan.tanggal);
        contentValues.put(WAKTU, pertemuan.waktu);
        contentValues.put(DELETED1, getStringFromDate(pertemuan.getDeleted1()));

        sqLiteDatabase.update(TABLE_NAME1, contentValues, ID_PERTEMUAN + " =? ", new String[]{String.valueOf(pertemuan.getId())});
    }
    private String getStringFromDate(Date date)
    {
        if(date == null)
            return null;
        return dateFormat.format(date);
    }

    private Date getDateFromString(String string)
    {
        try
        {
            return dateFormat.parse(string);
        }
        catch (ParseException | NullPointerException e)
        {
            return null;
        }
    }
}
