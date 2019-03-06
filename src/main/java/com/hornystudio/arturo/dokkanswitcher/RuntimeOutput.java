package com.hornystudio.arturo.dokkanswitcher;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

public class RuntimeOutput {

    String process;
    String commands;

    public RuntimeOutput(String process, String commands){ //CONSTRUCTOR FOR SU COMMANDS

        this.process = process;
        this.commands = commands;

    }

    public RuntimeOutput(String commands){ //CONSTRUCTOR FOR BASE COMMANDS

        this.commands = commands;

    }


    public ArrayList ExecuteSU() throws IOException,InterruptedException { //EXECUTE SU COMMANDS

        ArrayList<String> returnedOut = new ArrayList<>();
        String output;
        String error;
        BufferedReader br;

        Process su = Runtime.getRuntime().exec(process); //GET THE SU PERMISSION
        OutputStream os = su.getOutputStream();          //CREATE OUTPUT STREAM INTERFACE (OUR ACTUAL INPUT METHOD FOR THE CONSOLE)
        InputStream is = su.getInputStream();            //CREATE INPUT STREAM INTERFACE (OUR ACTUAL OUTPUT METHOD FOR THE CONSOLE)
        InputStream er = su.getErrorStream();            //ERROR INTERFACE
        os.write(commands.getBytes());                   //EXECUTE THE COMMAND

        os.write("\nexit\n".getBytes());                 //*
        os.flush();                                      //* EXIT SHELL
        os.close();                                      //*

        br = new BufferedReader(new InputStreamReader(is)); //BUFFER FOR OUTPUT
        while ((output = br.readLine()) != null) {
            Log.d("[Output]", output);
            returnedOut.add(output);                     //BUILDING ARRAYLIST TO RETURN
        }
        br.close();                                      //CLOSING BUFFER

        br = new BufferedReader(new InputStreamReader(er)); //BUFFER FOR ERRORS
        while ((error = br.readLine()) != null) {
            Log.e("[Error]", error);
        }
        br.close();

        su.waitFor();                                       //WAIT FOR SU TO FINISH
        su.destroy();                                       //DESTROY SU.
        return returnedOut;
    }


    public ArrayList Execute() throws IOException,InterruptedException { //EXECUTE SU COMMANDS

        ArrayList<String> returnedOut = new ArrayList<>();
        String output;
        String error;
        BufferedReader br;

        Process process = Runtime.getRuntime().exec("echo\n"); //GET THE SU PERMISSION
        OutputStream os = process.getOutputStream();          //CREATE OUTPUT STREAM INTERFACE (OUR ACTUAL INPUT METHOD FOR THE CONSOLE)
        InputStream is = process.getInputStream();            //CREATE INPUT STREAM INTERFACE (OUR ACTUAL OUTPUT METHOD FOR THE CONSOLE)
        InputStream er = process.getErrorStream();            //ERROR INTERFACE
        os.write(commands.getBytes());                   //EXECUTE THE COMMAND

        os.write("\nexit\n".getBytes());                 //*
        os.flush();                                      //* EXIT SHELL
        os.close();                                      //*

        br = new BufferedReader(new InputStreamReader(is)); //BUFFER FOR OUTPUT
        while ((output = br.readLine()) != null) {
            Log.d("[Output]", output);
            returnedOut.add(output);                     //BUILDING ARRAYLIST TO RETURN
        }
        br.close();                                      //CLOSING BUFFER

        br = new BufferedReader(new InputStreamReader(er)); //BUFFER FOR ERRORS
        while ((error = br.readLine()) != null) {
            Log.e("[Error]", error);
        }
        br.close();

        process.waitFor();                                       //WAIT FOR SU TO FINISH
        process.destroy();                                       //DESTROY SU.
        return returnedOut;
    }

 }




