package edu.cnm.deepdive.craps.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Game {

  private Tally tally;
  private Random rng;

  public Game(Random rng) {
    this.rng = rng;
    this.tally = new Tally();
  }

  public Round play(){

    State state = State.initial();
    int point = 0;
    List<Roll> rolls = new LinkedList<>();
    Roll roll;
    do {
      roll = new Roll(rng);
      rolls.add(roll);
      state = state.next(point,roll);
      if (state == State.POINT){
        point = roll.getValue();
      }

    }while (!(state == State.WIN || state == State.LOSS));
    if (state==State.WIN){
      tally.win();
    } else {
      tally.loss();
    }
    return new Round(state, rolls);

  }

  public List<State> play(int rounds){
    List<State> score = new LinkedList<>();

    for (int i = 0; i < rounds; i++) {
      State state = State.initial();
      int point = 0;
      Roll roll;

      do {
        roll = new Roll(rng);
        state = state.next(point,roll);
        if (state == State.POINT){
          point = roll.getValue();
        }

      }while (!(state == State.WIN || state == State.LOSS));
      if (state==State.WIN){
        tally.win();
      } else {
        tally.loss();
      }
      score.add(state);
    }

    return score;
  }

  public void reset() {
    tally.reset();
  }

  public int getWins() {
    return tally.getWins();
  }

  public int getLosses() {
    return tally.getLosses();
  }

  public int getPlays() {
    return tally.getPlays();
  }

  public void win() {
    tally.win();
  }

  public void loss() {
    tally.loss();
  }

  public double getPercentage() {
    return tally.getPercentage();
  }

  private enum State {

    COME_OUT{
      @Override
      public State next(Roll roll) {
        State nextState;
        int value = roll.getValue();

        if (value == 7 || value == 11){
          nextState = WIN;
        }
        else if (value == 2 || value == 3 || value == 12){
          nextState = LOSS;
        } else {
          nextState = POINT;
        }
      return nextState;
      }
    },
    WIN,
    LOSS,
    POINT{
      @Override
      public State next(Roll roll) {
        throw new IllegalStateException();
      }
      @Override
      public State next(int point, Roll roll) {
        State nextState = this;

        int value = roll.getValue();
        if (value == 7){
          nextState = LOSS;
        } else if (value == point){
          nextState = WIN;
        }
        return nextState;
      }
    };


    public static State initial(){
      return COME_OUT;
    }

    public State next(Roll roll) {
      return this;
    }

    public State next(int point, Roll roll) {
      return next(roll);
    }
  }

  public static class Roll {

    private final int die1;
    private final int die2;

    private Roll(int die1, int die2) {
      this.die1 = die1;
      this.die2 = die2;
    }

    private Roll(Random rng) {
      this(rng.nextInt(6)+1,rng.nextInt(6)+1);
    }

    public int [] getDice(){
      return new int[] {die1,die2};
    }


    public int getDie2() {
      return die2;
    }

    public int getDie1() {
      return die1;
    }


    public int getValue(){
      return die1+die2;
    }

    @Override
    public String toString() {
      return "Roll(" + Arrays.toString(getDice()) +", Value="+ getValue() + ")";
    }
  }

  private static class Tally {

    private int wins;
    private int losses;


    private void reset(){
      wins = 0;
      losses = 0;
    }

    private int getWins() {
      return wins;
    }

    private int getLosses() {
      return losses;
    }

    private int getPlays(){
      return wins + losses;
    }


    private void win(){
      wins++;
    }
    private void loss(){
      losses++;
    }

    private double getPercentage(){
      return getPlays() > 0 ? ((double) wins) / getPlays() : 0;
    }
  }

  public static class Round{

    private final State state;
    private final List<Roll> rolls;

    private Round(State state, List<Roll> rolls) {

      this.state = state;
      this.rolls = Collections.unmodifiableList(rolls);

    }

    public State getState() {
      return state;
    }

    public List<Roll> getRolls() {
      return rolls;
    }

    @Override
    public String toString() {
      return "Round{" +
          "state=" + state +
          ", rolls=" + rolls +
          '}';
    }
  }
}
