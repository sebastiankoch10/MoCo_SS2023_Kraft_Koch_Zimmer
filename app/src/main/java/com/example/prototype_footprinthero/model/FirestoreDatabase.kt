package com.example.prototype_footprinthero.model

import com.google.firebase.firestore.FirebaseFirestore


class FirestoreDatabase {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun writeCO2Data(
        co2Data: ConsumptionDataList,
        collectionName: String,
        documentId: String,
        callback: (Boolean, String?) -> Unit
    ) {
        val firestoreData = co2Data.co2Data.map { consumptionData ->
            mapOf(
                "dayOfWeek" to consumptionData.dayOfWeek, "value" to consumptionData.Co2, "calendarWeek" to consumptionData.weekOfYear
            )
        }

        val dataToWrite = mapOf("co2Data" to firestoreData)

        db.collection(collectionName).document(documentId).set(dataToWrite).addOnSuccessListener {
            callback(true, null) // Erfolgreich geschrieben, kein Fehler
        }.addOnFailureListener { e ->
            val errorMessage = e.message ?: "Unbekannter Fehler"
            callback(false, errorMessage) // Fehler beim Schreiben mit Fehlermeldung
        }
    }

    fun readCO2Data(
        collectionName: String,
        documentId: String,
        callback: (List<ConsumptionData>?, Exception?) -> Unit
    ) {
        db.collection(collectionName).document(documentId).get()
            .addOnSuccessListener { documentSnapshot ->
                val firestoreData = documentSnapshot.get("co2Data") as? List<*>
                val co2DataList = firestoreData?.filterIsInstance<Map<String, Any>>()?.map { data ->
                    ConsumptionData(
                        dayOfWeek = data["dayOfWeek"] as? String ?: "",
                        Co2 = (data["value"] as? Double)?.toFloat() ?: 0f,
                        weekOfYear = data["calendarWeek"] as? Int ?: 0
                    )
                }
                callback(co2DataList, null)
            }.addOnFailureListener { e ->
                callback(null, e) // Fehler beim Lesen
            }
    }

    fun updateCO2Data(
        co2Data: List<ConsumptionData>,
        collectionName: String,
        documentId: String,
        callback: (Boolean, Any?) -> Unit
    ) {
        val firestoreData = co2Data.map { barData ->
            mapOf(
                "dayOfWeek" to barData.dayOfWeek, "value" to barData.Co2
            )
        }

        db.collection(collectionName).document(documentId).update(mapOf("co2Data" to firestoreData))
            .addOnSuccessListener {
                callback(true, null) // Erfolgreich aktualisiert
            }.addOnFailureListener { e ->
                callback(false, e) // Fehler beim Aktualisieren
            }
    }

    fun deleteCO2Data(
        collectionName: String, documentId: String, callback: (Boolean, Any?) -> Unit
    ) {
        db.collection(collectionName).document(documentId).delete().addOnSuccessListener {
            callback(true, null) // Erfolgreich gelöscht
        }.addOnFailureListener { e ->
            callback(false, e) // Fehler beim Löschen
        }
    }
}
