package com.linux.Entity;

import java.util.Map;

/**
 * Created by nishant on 21/3/17.
 */
public class Directory {

    private final String name;
    private final Map<String,Directory>subDirectoryMap;

    public Directory(String name, Map<String, Directory> subDirectoryMap) {
        this.name = name;
        this.subDirectoryMap = subDirectoryMap;
    }

    public String getName() {
        return name;
    }

    public Map<String, Directory> getSubDirectoryMap() {
        return subDirectoryMap;
    }

    public boolean isSubDirectory(String name){
        if(subDirectoryMap.get(name)!=null)
            return true;
        return false;
    }

    public Directory getsubDirectory(String name){
        Directory directory=subDirectoryMap.get(name);
        if(directory!=null)
            return directory;
        else
            return null;

    }

    public boolean addSubDirectory(Directory subdir){
        if(isSubDirectory(subdir.getName())) return false;  //subdirectory already present
        subDirectoryMap.put(subdir.getName(),subdir);
        return true;
    }

    public boolean removeSubDir(String name){
        if(!isSubDirectory(name)) return false;
        subDirectoryMap.remove(name);
        return true;
    }

}
