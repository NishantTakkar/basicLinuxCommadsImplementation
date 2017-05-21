package com.linux.Utils;

import com.linux.Entity.Directory;

import java.util.HashMap;

/**
 * Created by nishant on 21/3/17.
 */
public class DirUtils {

    public static Directory getNewEmptyDirectory(String name){
        return new Directory(name,new HashMap<String, Directory>());
    }



}
