/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jonat
 */

import java.awt.*;
import javax.swing.*;


class pressed extends JPanel{
    boolean isBlack = false;
    boolean isAlive = true;
    
    public pressed(){
        super();
    }
    int getCounter(){
        if(isAlive){
        return 1;
    }
        else{
            return 0;
        }
    }
    boolean isItBlack(){
        return isBlack;
    }
     boolean isItAlive(){
        return isAlive;
    }
    void changeColor(){
        isBlack = !isBlack;
    }
    void changeLifeCondition(){
        isAlive = !isAlive;
    }
}



public class JonathansGameOfLife {

    final static int numOfNodes = 10000;
    
    static int loopLeft(double aNode){
        if(aNode % 100 == 0){// if Node is on left-most column
        double width = Math.sqrt(numOfNodes);
        int delta = 1;
        double diff = aNode - delta;
            return (int) (1 + 2 * diff - width * Math.floor(diff /width));    
    }
        else{
            return (int)aNode - 1 ;
        }
    }
    
    static int loopRight(double aNode){
        if((aNode + 1) % 100 == 0){ // if Node is on right-most column
        double width = Math.sqrt(numOfNodes); 
        int delta = 1;
        double diff = aNode - delta;
            return (int) ( aNode - width + delta);//width * Math.floor(diff /width));    
    }
        return (int) aNode + 1;
    }
    
    static int loopUp(double aNode, int delta){ // top left is -1, top center is 0, top right is 1
        if(aNode < 99 && aNode >= 0){// if Node is on top-most row
        double width = Math.sqrt(numOfNodes); 
     
            return (int) ((width * width - width) + aNode + delta);    
    }
        return (int) aNode - 10 + delta;
    }
    
    static int loopDown(double aNode, int delta){ // bottom left is -1, bottom center is 0, bottom right is 1
        double width = Math.sqrt(numOfNodes); 
        if(aNode > 9899 && aNode <= 9999){ // if Node is on bottom-most row
        
     
            return (int) ( aNode - (width * width - width) + delta);    
    }
        return (int) (aNode + width + delta);
    }
    
     static int randomBinary(){  
        return (int)(Math.random() * 2);
     }
     
     
    static class LifeThread extends Thread{
       
        static pressed[] nodes = new pressed[numOfNodes];
        
        static int j = 0;
         static public void addPanel(pressed node){
            nodes[j] = node;
            j++;
        }
        
        @Override
        public void run(){
            
            for(;;){
              for(int i = 0; i < numOfNodes ; i++){
                 
                     int neighbors = nodes[loopDown(i,- 1)].getCounter() + nodes[loopDown(i,0)].getCounter() + nodes[loopDown(i,1)].getCounter() + nodes[loopLeft(i)].getCounter() + nodes[loopRight(i)].getCounter() + nodes[loopUp(i, -1)].getCounter() + nodes[loopUp(i,0)].getCounter() +  nodes[loopUp(i, 1)].getCounter() ;
                     if (nodes[i].isItAlive()==true){
                      if( neighbors < 2 && neighbors > 3  ){
                           nodes[i].changeLifeCondition();
                           nodes[i].changeColor();
                           
                      }  
                     }
                     if (nodes[i].isItAlive()==false){
                      if( neighbors == 3 ){
                           nodes[i].changeLifeCondition();
                           nodes[i].changeColor();
                           
                      }  
                     }
            }
        }
            
    }
    }
    
    public static void main(String[] args) {
        JFrame universe = new JFrame("universe");
        universe.setSize(500,500);
        JPanel Earth = new JPanel();
        universe.setVisible(true);
        universe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Earth.setLayout(new GridLayout(100,100));
        LifeThread ConwayLife = new LifeThread();
        for(int i = 0; i < numOfNodes; i++){
            pressed aNode = new pressed();
            randomBinary();
            if(randomBinary() == 1){
                aNode.setBackground(Color.WHITE);
            }
            else{
                 aNode.setBackground(Color.BLACK);
            }
            LifeThread.addPanel(aNode);
            Earth.add(aNode);
      
           }
         universe.getContentPane().add(Earth);
        
        ConwayLife.start();
        
    }
    
}
