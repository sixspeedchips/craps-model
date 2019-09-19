package edu.cnm.deepdive.craps.model;

import edu.cnm.deepdive.craps.model.Game.Roll;
import java.security.SecureRandom;
import java.util.Random;

public class Main {

  public static void main(String[] args) {

    Random rng = new SecureRandom();
    Game game = new Game(rng);
    for (int i = 0; i < 100000000; i++) {
      game.play();
    }
    System.out.println(game.getPercentage());

  }
}
