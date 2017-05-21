package com.linux.Handler;

import com.linux.Entity.DirLevelManager;
import com.linux.Service.CommandServiceImpl;
import com.linux.Service.ICommandsService;

import java.util.Scanner;

/**
 * Created by nishant on 22/3/17.
 */
public class CommandLineHandler {

    DirLevelManager levelManager=new DirLevelManager();
    ICommandsService commandsService=new CommandServiceImpl(levelManager);

    public void getInput(){
        initialise();
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNextLine()){
            String line=scanner.nextLine();
            String[] inputArray=line.split(" ");
            if(inputArray.length!=0){
                String command=inputArray[0];
                switch (command){
                    case "mkdir":
                        handleMkdir(inputArray);
                        break;
                    case "cd":
                        handleCd(inputArray);
                        break;
                    case "pwd":
                        handlePwd(inputArray);
                        break;
                    case "rm":
                        handleRm(inputArray);
                        break;
                    case "session":
                        handleSessionClear(inputArray);
                        break;
                    case "ls":
                        handleLs(inputArray);
                        break;
                    case "exit":
                        handleExit();
                        break;
                    default:
                        handleDefault();
                }

            }

        }
    }

    public void initialise(){
        commandsService.initialiseDirectoryStructure();
    }




    public boolean inputError(String[] command,int len){
        if(command.length!=len){
            handleDefault();
            return true;
        }
        return false;
    }

    public void handleLs(String[] command){
        if(inputError(command, 1))return;
        System.out.print("DIRS:");
        for(String a:commandsService.listAllSubDir()){
            System.out.print("  "+a);
        }
        System.out.println();

    }

    public void handleCd(String[] command){
        if(inputError(command, 2))return;
        if(command.length!=2){
            handleDefault();
            return;
        }

        if (command[1].equals("..")){
            dotdotHandler();

        }
        else if(command[1].startsWith("/")){
            String trimmedCommand=command[1].substring(1, command[1].length());
            if(commandsService.cdFromPath(trimmedCommand.split("/"),true))
                System.out.println("SUCC: REACHED");
            else
                System.out.println("ERR: INVALID PATH");

        }else {
            if(commandsService.cdFromPath(command[1].split("/"),false))
                System.out.println("SUCC: REACHED");
            else
                System.out.println("ERR:DIRECTORY NOT FOUND");
        }

    }
    public void handleDefault(){
        System.out.println("ERR: CANNOT RECOGNIZE INPUT");
    }

    public void handleSessionClear(String[] command){
        if(inputError(command, 2))return;
        if(command[1].equalsIgnoreCase("clear")){
            commandsService.initialiseDirectoryStructure();
            System.out.println("SUCC: CLEARED: RESET TO ROOT");
        }
        else
            handleDefault();
    }

    public void handlePwd(String[] command){
        if(inputError(command, 1))return;
        System.out.println("PATH: "+commandsService.getCurrentPath());
    }

    public void handleMkdir(String[] command){
        if(inputError(command, 2))return;
        Boolean isPathFromRoot=false;
        String newCommand=command[1];
        if(command[1].startsWith("/")) {
            isPathFromRoot=true;
            newCommand = command[1].substring(1);
        }

        if (commandsService.createNewDirectory(newCommand, isPathFromRoot)){
            System.out.println("SUCC:CREATED");
        }else{
            System.out.println("ERR:DIRECTORY ALREADY EXISTS");
        }
    }

    public void handleRm(String[] command){
        if(inputError(command, 2)) return;
        Boolean isPathFromRoot=false;
        String newCommand=command[1];
        if(command[1].startsWith("/")) {
            isPathFromRoot=true;
            newCommand = command[1].substring(1);
        }
        if(commandsService.removeDir(newCommand, isPathFromRoot)){
            System.out.println("SUCC:DELETED");
        }else{
            System.out.println("ERR: DIRECTORY NOT FOUND");
        }
    }

    public void handleExit(){
        System.out.println("BYE");
        System.exit(1);
    }

    public void dotdotHandler(){

        if(commandsService.oneLevelUp()){
            System.out.println("SUCC:Reached");
        }else{
            System.out.println("ERR: Already on Root");
        }

    }


}
