package com.spaceinvaders.game;

import com.spaceinvaders.actors.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EnemyFactory {

    //private ArrayList<AlienGroup> alienRows = new ArrayList<>();
    //private Swarm swarm;
    public static EnemyFactory instance;

    public static EnemyFactory getInstance(){
        if(instance == null){
            instance = new EnemyFactory();
        }
        return instance;
    }

    public Swarm getSwarm(String fileName) throws FileNotFoundException {
        Swarm swarm = new Swarm();
        ArrayList<AlienGroup> rows = getAlienRows(fileName);
        System.out.println("From getSwarm(): " + rows.size());
        for(AlienGroup row : rows){
            swarm.addAlienGroup(row);
        }
        swarm.drawBox();
        return swarm;
    }

    public ArrayList<AlienGroup> getAlienRows(String fileName) throws FileNotFoundException {
        ArrayList<AlienGroup> alienRows = new ArrayList<>();
        String line;
        HashMap<Float, Integer> rows = new HashMap<>();
        AlienGroup alienLine = new AlienGroup();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            while((line = br.readLine()) != null){
                String[] components = line.split(",");
                float x = Float.parseFloat(components[1]);
                float y = Float.parseFloat(components[2]);

                if(!rows.containsKey(y)){
                    rows.put(y, 0);
                    if(rows.size() > 1){
                        alienLine.drawBox();
                        alienRows.add(alienLine);
                        alienLine = new AlienGroup();
                    }
                }

                String alienType = components[0].toLowerCase();
                alienLine.addAlien(getAlien(alienType,x,y));


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!alienRows.contains(alienLine)){
            alienLine.drawBox();
            alienRows.add(alienLine);
        }


        return alienRows;
    }

    public Enemy getAlienRow(){
        AlienGroup group = new AlienGroup();

        Alien alien1 = new CrabAlien(100, 460);
        Alien alien2 = new CrabAlien(200, 460);
        Alien alien3 = new CrabAlien(300, 460);
        Alien alien4 = new CrabAlien(400, 460);
        Alien alien5 = new CrabAlien(500, 460);

        group.addAlien(alien1);
        group.addAlien(alien2);
        group.addAlien(alien3);
        group.addAlien(alien4);
        group.addAlien(alien5);

        return group;
    }

    public Alien getAlien(String alienType, float x, float y){
        Alien alien = null;
        if(alienType.equals("crabalien")){
            alien =  new CrabAlien(x,y);
        }else if(alienType.equals("purplealien")){
            alien = new PurpleAlien(x,y);
        }else if(alienType.equals("icealien")){
            alien = new IceAlien(x,y);
        }
        return alien;
    }




}
