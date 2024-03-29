/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/7/18 6:50 AM
 */

package sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.DatabaseSyncable;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 07/05/2018 - 6:50.
 * <p>
 * This class last modified by User
 */
public abstract class DatabaseSynchronizer implements ValueEventListener {
    private DatabaseReference database;
    private ArrayList<String> referenceName;
    private ArrayList<DatabaseSyncable> databaseSyncables;
    private ArrayList<Boolean> syncStatus;
    public DatabaseSynchronizer(DatabaseReference database) {
        this.database = database;
        this.database.keepSynced(Boolean.TRUE);
        this.referenceName = new ArrayList<>();
        this.syncStatus = new ArrayList<>();
        this.databaseSyncables = new ArrayList<>();
        this.database.addValueEventListener(this);
    }

    public DatabaseReference getDatabase() {
        return database;
    }

    public ArrayList<DatabaseSyncable> getDatabaseSyncables() {
        return databaseSyncables;
    }

    public void add(DatabaseSyncable databaseSyncable, String referenceName) {
        database.removeEventListener(this);
        this.referenceName.add(referenceName);
        this.databaseSyncables.add(databaseSyncable);
        this.syncStatus.add(Boolean.TRUE);
        database.addValueEventListener(this);
    }

    public void changeVariable(String value) {
        for (int i = 0; i < this.databaseSyncables.size(); i++) {
            if (this.equals(value, this.databaseSyncables.get(i))) {
                this.database.child(this.referenceName.get(i)).setValue(value);
                break;
            }
        }
    }

    public void changeVariable(HashMap<String, Object> value) {
        new DatabaseSyncOperation(this).execute(value);
        /**
        ArrayList<Boolean> syncData=new ArrayList<>();
        Integer j=0;
        Boolean updateData=Boolean.TRUE;
        for (String key: value.keySet()) {
            syncData.add(Boolean.FALSE);
            for (int i = 0; i < this.databaseSyncables.size(); i++) {
                if (this.equals(value.get(key).toString(), this.databaseSyncables.get(i))) {
                    syncData.set(j,Boolean.TRUE);
                    break;
                }
            }
            j++;
        }
        for(Boolean temp:syncData){
            if(!temp){
                updateData=Boolean.FALSE;
                break;
            }
        }
        if(updateData)
            this.database.setValue(value);
         */
    }

    public void changeVariable(ArrayList<String> value) {
        for (int i = 0; i < this.databaseSyncables.size(); i++) {
            if (this.equals(value.toString(), this.databaseSyncables.get(i))) {
                this.database.child(this.referenceName.get(i)).setValue(value);
                break;
            }
        }
    }


    protected abstract void runDataChange(DataSnapshot dataSnapshot, DatabaseSyncable listener);

    protected abstract Boolean equals(String value, DatabaseSyncable listener);

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        for (int i = 0; i < this.referenceName.size(); i++) {
            if (dataSnapshot.exists()) {
                DataSnapshot reference = dataSnapshot.child(this.referenceName.get(i));
                if (reference.exists()) {
                    this.runDataChange(reference, this.databaseSyncables.get(i));
                } else {
                    this.syncStatus.set(i, Boolean.FALSE);
                }
            }
        }

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
