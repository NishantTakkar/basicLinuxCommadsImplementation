package com.linux.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Created by nishant on 21/3/17.
 */
public class DirLevelManager {

    private Stack<Directory> levelManager=new Stack<>();

    public boolean removeOneLevel(){
        if(levelManager.isEmpty()) {
           // return false;
        }
        levelManager.pop();
        return true;
    }

    public List<String> getLevels(){
        List<Object> levelList=Arrays.asList(levelManager.toArray());
        List<String> stringLevelList=new ArrayList<>();
        for(Object o:levelList){
            Directory directory=(Directory)o;
            stringLevelList.add(directory.getName());
        }
        return stringLevelList;
    }

    public boolean addLevel(Directory directory){
        levelManager.push(directory);
        return true;
    }

    public Directory getCurrentDir(){
        return levelManager.peek();

    }

    public boolean clearAllLevels(){
        levelManager.clear();
        return true;
    }

    public Directory getRoot(){
        return levelManager.firstElement();
    }

}