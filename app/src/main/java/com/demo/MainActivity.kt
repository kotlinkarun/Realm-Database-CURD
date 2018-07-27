package com.demo

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val realm by lazy { Realm.getDefaultInstance() }
    val id by lazy { findViewById<EditText>(R.id.editId) }
    val names by lazy { findViewById<EditText>(R.id.edit_name) }
    val mobile by lazy { findViewById<EditText>(R.id.edit_mobile) }
    val address by lazy { findViewById<EditText>(R.id.edit_address) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.init(this)

        val config = RealmConfiguration.Builder()
                .name("person.realm").build()
        Realm.setDefaultConfiguration(config)


        add.setOnClickListener {
            addRecord()
        }

        read.setOnClickListener {
            viewRecord()
        }

        update.setOnClickListener {
            updateRecord()
        }

        delete.setOnClickListener {
            deleteRecord()
        }

    }


    private fun addRecord() {
        try {
            val ran = (Math.random() * 1000).toInt()
            realm.beginTransaction()
            val student = realm.createObject(Student::class.java)
            student.roll_no = ran
            student.name =names.text.toString()
            student.mobile =mobile.text.toString()
            student.address =address.text.toString()
            realm.commitTransaction()

            Toast.makeText(applicationContext,"add data sucessfully",Toast.LENGTH_SHORT).show()

            names.text.clear()
            mobile.text.clear()
            address.text.clear()
            viewRecord()
        } catch (e: Exception) {
        }
    }


    @SuppressLint("SetTextI18n")
    private fun viewRecord() {
        try {
            val results = realm.where(Student::class.java).findAll()
            var info=""
            for (student in results) {
                info +="Roll No. : ${student.roll_no} \n Name. : ${student.name} \n" +
                  "Mobile No. : ${student.mobile} \n Address. : ${student.address} \n \n "
            }
            tv.text=info
        } catch (e: Exception) {
        }
    }

    private fun updateRecord() {
        try {
            val results = realm.where(Student::class.java)
                    .equalTo("roll_no", id.text.toString().toInt()).findAll()

            realm.beginTransaction()

            for (student in results) {
                student.name =names.text.toString()
            }
            realm.commitTransaction()
            id.text.clear()
            Toast.makeText(applicationContext,"Update data sucessfully",Toast.LENGTH_SHORT).show()
            viewRecord()
        } catch (e: Exception) {
        }
    }

    private fun deleteRecord() {
        try {
            val results = realm.where(Student::class.java)
                    .equalTo("roll_no", id.text.toString().toInt()).findAll()
            realm.beginTransaction()
            results.deleteAllFromRealm()
            realm.commitTransaction()
            id.text.clear()
            Toast.makeText(applicationContext,"Delete data sucessfully",Toast.LENGTH_SHORT).show()
            viewRecord()
        } catch (e: Exception) {
        }
    }


}












