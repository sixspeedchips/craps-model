package edu.cnm.deepdive.craps.model;


import java.security.SecureRandom;
import java.util.Random;

public class Main {

  public static void main(String[] args) {


    Random rng = new SecureRandom();
    rng.setSeed(1);
    Game game = new Game(rng);

    for (int i = 0; i < 3; i++) {
      System.out.println(game.play());
      System.out.println();
    }


  }





}
