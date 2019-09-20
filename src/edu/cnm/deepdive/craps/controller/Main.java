package edu.cnm.deepdive.craps.controller;

import edu.cnm.deepdive.craps.model.Game;
import edu.cnm.deepdive.craps.model.Game.Round;
import java.security.SecureRandom;
import java.util.Random;

public class Main {

  public static void main(String[] args) {


    int rounds = args.length > 0 ? Integer.parseInt(args[0]) : 1;
    play(rounds);
  }

  private static void play(int rounds){
    Random rng = new SecureRandom();

    Game game = new Game(rng);
    Round round = null;
    for (int i = 0; i < rounds ; i++) {
      round = game.play();
    }

    if (rounds == 1){
      System.out.println("Rolls:");
      round.getRolls().forEach(roll -> {  System.out.printf("\t%s%n",roll); });
      System.out.printf("Outcome: %n\t%s",round.getState());
    } else {
      System.out.printf("Number Played: %n\t\t%,d%n", game.getPlays());
      System.out.printf("Number Won: %n\t\t%,d%n", game.getWins());
      System.out.printf("Win Percentage: %n\t\t%.2f %%%n", game.getPercentage()*100);
    }

  }

}
