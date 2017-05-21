package com.linux.Service;

import com.linux.Entity.Directory;

import java.util.Set;

/**
 * Created by nishant on 21/3/17.
 */
public interface ICommandsService {

    boolean initialiseDirectoryStructure();

    boolean createNewDirectory(String newDirectoryName,Boolean isPathFromRoot);

    Set<String> listAllSubDir();

    Directory getPresentWorkingDirectory();

    String getCurrentPath();

    boolean removeDir(String name,Boolean isPathFromRoot);

    boolean cd(String name);

    boolean cdFromPath(String[]path,Boolean isPathFromRoot);

    boolean oneLevelUp();

}
