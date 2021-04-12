package com.brunaLopes.appchat.dataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void save(UserDb usuariow);

    @Query("select cast(case when count(*) > 0 then 1 else 0 end as BIT)"+
            "from userdb where userdb.username=:username")
    boolean findByUserName(String username);

    @Query("select username from userdb where isOnline=1")
    List<String> list_conected();
//
//    @Query("select username from userdb")
//    List<String> list_conected();

    @Query("UPDATE userdb SET isOnline=1 WHERE username =:username")
    void updateStatusToOn(String username);

    @Query("UPDATE userdb SET isOnline=1 WHERE username =:username")
    void updateStatusToOff(String username);
}
