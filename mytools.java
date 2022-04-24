// package org.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import org.json.JSONObject;


// import org.json.JSONObject;

public class mytools {
    
    public static void main(String[] args) {

        mytools oMytools = new mytools();
        // System.out.println("Test1");

        // String sFileName = "error.log.1.txt";
        String sFileName = "error.log.1";
        boolean bJson = false;
        int iMaxArgument = args.length;
        String sOutFileName = "OutFile.log";
        boolean bPetunjukPenggunaan = false;

        if (args.length != 0){
            for (int i = 0; i < iMaxArgument; i++){
                if (args[i].equals("")){
                    i = iMaxArgument;
                }
                else {
                    if (args[i].equals("-t")){
                        if ((i + 1) < iMaxArgument ){
                            if (args[i + 1].equals("json")){
                                bJson = true;                                
                            }

                            // sOutFileName = args[i + 1];
                        }
                        else{
                            i = iMaxArgument;
                        }

                    }
                    else if(args[i].equals("-o")){
                        sOutFileName = args[i + 1];
                        // System.out.println(sOutFileName);

                        if ((i + 1) < iMaxArgument){
                            sOutFileName = args[i + 1];
                            // System.out.println(sOutFileName);
                        }
                        else {                            
                            i = iMaxArgument;
                        }

                    }
                    else if(args[i].equals("-h")){
                        bPetunjukPenggunaan = true;
                    }

                    

                }                
            } 
        }
        

        try{
            // Open file
            File fFile = new File(sFileName);
            Scanner sScanner = new Scanner(fFile);

            JSONObject oJsonObject = new JSONObject();
            String sTextString = "";
            boolean bWrite = false;
            
            // Write to output file
            try {

                FileWriter fileWriter = new FileWriter(sOutFileName);
                // fileWriter.write(sTextString);
                bWrite = true;

                // display file
                while(sScanner.hasNextLine()){

                    String sData = sScanner.nextLine();
                    if (bJson) {
                        oJsonObject = oMytools.toJson(sData);  
                        fileWriter.write(oJsonObject.toString());
                    }
                    else {
                        sTextString = oMytools.toText(sData);
                        fileWriter.write(sTextString);
                    }

                    // fileWriter.write(sTextString);
                    bWrite = true;

                }

                if (bWrite){
                    fileWriter.close();
                    System.out.println("File selesai ditulis");                    
                    System.out.println("File Name : " + sOutFileName);
                }

            } catch (Exception e) {
                //TODO: handle exception
                System.out.println("Penulisan file Error : " + e.getMessage());
            }
 
        } catch (FileNotFoundException e){
            System.out.println("Open file error : " + e.getMessage());
            e.printStackTrace();
        }

        if (bPetunjukPenggunaan){
            oMytools.petunjukPenggunaan();
        }
    }

    public String toText(String sInputStringData){
        // System.out.println(sInputStringData);
        return sInputStringData;
    }

    public JSONObject toJson(String sInputStringData){

        String[] sJsonData;
        sJsonData = new String[4];
        int iLenSJsonData = sJsonData.length;

        String sTemp = "";
        int iIndexAwal = 0;
        int iIndexAkhir = 0;
        String sTempData = sInputStringData;

        if (sTempData.contains("[")){
            for (int i = 0; i < 4; i++){
                if (i < iLenSJsonData - 1) {
                    iIndexAwal = sTempData.indexOf("[");
                    iIndexAkhir = sTempData.indexOf("]");
                    sJsonData[i] = sTempData.substring(iIndexAwal + 1, iIndexAkhir);
                    // System.out.println(sJsonData[i]);
                    sTempData = sTempData.substring(iIndexAkhir + 1);
                    // System.out.println(sTempData);
                                    
                }
                else {
                    sJsonData[i] = sTempData;
                }
            }          
        }


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date", sJsonData[0]);
        jsonObject.put("hostname", sJsonData[1]);
        jsonObject.put("aplication", sJsonData[2]);
        jsonObject.put("message", sJsonData[3]);
        //System.out.println(jsonObject);
        return jsonObject;

    }

    public void petunjukPenggunaan(){
        System.out.println("=======================================================================================");
        System.out.println("Program ini adalah untuk memindahkan file log error.log.1 ke dalam file json atau text.");
        System.out.println("java mytools -t json -o outputFile.json -h.");
        System.out.println("Argument : ");
        System.out.println("-t json : tipe file output adalah json");
        System.out.println("-o outputFile.json : nama file output adalah outputFile.json");
        System.out.println("-h : petunjuk penggunaan");
        System.out.println("=======================================================================================");
    }



}