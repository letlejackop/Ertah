package com.example.ertah

import android.util.Log
import com.example.ertah.Register.RegisterActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.xml.xpath.XPathExpression

class database {
    private val db = Firebase.firestore
    fun getAll(){

        db.collection(RegisterActivity.COLLECTION_USER_CLIENT)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    Log.d("TAG", "aa ${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }
    }
    fun getEquals(collection:String, attribute:String,value:String){
        db.collection(collection).whereEqualTo(attribute,value)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    Log.d("TAG", "aa ${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }
    }
}