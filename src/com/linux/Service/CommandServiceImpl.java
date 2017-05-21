package com.linux.Service;

import com.linux.Entity.DirLevelManager;
import com.linux.Utils.DirUtils;
import com.linux.Entity.Directory;

import java.util.List;
import java.util.Set;

/**
 * Created by nishant on 21/3/17.
 */
public class CommandServiceImpl implements ICommandsService {

    DirLevelManager levelManager;
    public CommandServiceImpl(DirLevelManager dirLevelManager) {
     this.levelManager=dirLevelManager;
    }

    public boolean initialiseDirectoryStructure(){
        levelManager.clearAllLevels();
        levelManager.addLevel(DirUtils.getNewEmptyDirectory("/"));
        return true;
    }



    public boolean createNewDirectory(String newDirectoryName,Boolean isPathFromRoot){
        Directory currentDir=levelManager.getCurrentDir();

        if(newDirectoryName.contains("/")){
            String [] directories=newDirectoryName.split("/");
            Directory tempCurDir=currentDir;
            for(int i=0;i<directories.length-1;i++){
                Directory subdir=tempCurDir.getsubDirectory(directories[i]);
                if(subdir==null){
                    return false;
                }
                tempCurDir=subdir;
            }
            if(tempCurDir.getsubDirectory(directories[directories.length-1])!=null){
                return false; //already present
            }
            newDirectoryName=directories[directories.length-1];
            currentDir=tempCurDir;
        }

        if(isPathFromRoot){
            currentDir=levelManager.getRoot();
        }


        if(currentDir.isSubDirectory(newDirectoryName))return false;
        Directory subdir=DirUtils.getNewEmptyDirectory(newDirectoryName);
        currentDir.addSubDirectory(subdir);
        return true;
    }

    public Set<String> listAllSubDir(){
        Directory curDir=levelManager.getCurrentDir();
        Set<String>subDirs=curDir.getSubDirectoryMap().keySet();
        return subDirs;

    }

    public Directory getPresentWorkingDirectory(){
       return levelManager.getCurrentDir();

    }

    public String getCurrentPath(){
        List<String>levels=levelManager.getLevels();
        StringBuilder path=new StringBuilder(levels.get(0));
        for(int i=1;i<levels.size();i++){
            path.append(levels.get(i));
            path.append("/");
        }
        return path.toString();
    }

    public boolean removeDir(String name,Boolean isPathFromRoot){
        Directory parentDir=levelManager.getCurrentDir();
        if(isPathFromRoot){
            parentDir=levelManager.getRoot();

        }
        if(parentDir.removeSubDir(name)) return true;
        return false;
    }

    public boolean nestedIterator(String newDirectoryName,Directory currentDir){

        //currentDir=levelManager.getCurrentDir();

        if(newDirectoryName.contains("/")){
            String [] directories=newDirectoryName.split("/");
            Directory tempCurDir=currentDir;
            for(int i=0;i<directories.length-1;i++){
                Directory subdir=tempCurDir.getsubDirectory(directories[i]);
                if(subdir==null){
                    return false;
                }
                tempCurDir=subdir;
            }
            if(tempCurDir.getsubDirectory(directories[directories.length-1])!=null){
                return false; //already present
            }
            newDirectoryName=directories[directories.length-1];
            currentDir=tempCurDir;
        }
        return true;


    }


    public boolean checkForNestedCommand(String name){

        //check if it contains /

        return false;
    }




    public boolean cd(String name){
        Directory parentDir=levelManager.getCurrentDir();
        if(checkForNestedCommand(name)){
            nestedIterator(name,parentDir);
        }

        parentDir=levelManager.getCurrentDir();
        if(parentDir.isSubDirectory(name))
            return false;
        Directory directory=parentDir.getsubDirectory(name);
        if(directory!=null)
            levelManager.addLevel(directory);
        else
           return false;

        return true;
    }

    public boolean cdFromPath(String[]path,Boolean isPathFromRoot){
        Directory parentDir=levelManager.getCurrentDir();
        if(isPathFromRoot){
            parentDir=levelManager.getRoot();
            if(path.length==1 && path[0].equals("")){
                while(levelManager.getCurrentDir()!=parentDir){
                    levelManager.removeOneLevel();
                }
                return true;
            }
        }


        Directory tempParentDir=parentDir;
        for(int i=0;i<path.length;i++){
            if(!tempParentDir.isSubDirectory(path[i])){
                return false;
            }
            tempParentDir=tempParentDir.getsubDirectory(path[i]);
        }
        if(isPathFromRoot){
            while(levelManager.getCurrentDir()!=parentDir){
                levelManager.removeOneLevel();
            }
        }

        for(int i=0;i<path.length;i++){
            Directory directory =parentDir.getsubDirectory(path[i]);
            levelManager.addLevel(directory);
            parentDir=directory;
        }
        return true;
    }


    public boolean oneLevelUp(){

        Directory directory=levelManager.getCurrentDir();
        if(directory.getName()=="/"){
            return false;
        }
        return levelManager.removeOneLevel();


    }





}
