package com.brunaLopes.appchat.dataBase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserDb {
    @PrimaryKey(autoGenerate = true)
    public int id_User;

    @NonNull
    @ColumnInfo(name = "username")
    private String username;

    private boolean isOnline;

    public UserDb(String username) {
        this.username = username;
    }

    public void setId(int id) {
        this.id_User = id;
    }
    public int getId() {
        return id_User;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setIsOnline(boolean isOnline){
        this.isOnline = isOnline;
    }

    public boolean getIsOnline() {
        return isOnline;
    }

}
