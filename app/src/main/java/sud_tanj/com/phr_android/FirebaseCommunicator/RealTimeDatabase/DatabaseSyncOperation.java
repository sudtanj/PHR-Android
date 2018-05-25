/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/25/18 5:55 AM
 */

package sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase;

import android.os.AsyncTask;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.HashMap;

import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.Inteface.DatabaseSyncable;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 25/05/2018 - 5:55.
 * <p>
 * This class last modified by User
 */
public class DatabaseSyncOperation extends AsyncTask<HashMap<String, Object>,Void,Void> {
    private DatabaseSynchronizer databaseSynchronizer;
    public DatabaseSyncOperation(DatabaseSynchronizer databaseSynchronizer) {
        this.databaseSynchronizer=databaseSynchronizer;
    }

    @Override
    protected Void doInBackground(HashMap<String, Object>... params) {
        for(HashMap<String,Object> value:params){
            ArrayList<Boolean> syncData=new ArrayList<>();
            Integer j=0;
            Boolean updateData=Boolean.TRUE;
            for (String key: value.keySet()) {
                syncData.add(Boolean.FALSE);
                for (int i = 0; i < this.databaseSynchronizer.getDatabaseSyncables().size(); i++) {
                    if (this.databaseSynchronizer.equals(value.get(key).toString(), this.databaseSynchronizer.getDatabaseSyncables().get(i))) {
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
                this.databaseSynchronizer.getDatabase().setValue(value);
        }
        return null;
    }

}
