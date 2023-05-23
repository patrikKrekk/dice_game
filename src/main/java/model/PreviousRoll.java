package model;

import java.util.ArrayList;
import java.util.List;

public class PreviousRoll {
    private List<Integer> rolls;
    private Integer previousRolls;

    public PreviousRoll() {
        this.rolls = new ArrayList<>();
    }

    public Integer getPreviousRolls() {
        return previousRolls;
    }

    public void setRolls(List<Integer> rolls) {
        this.rolls = rolls;
    }

    public void addRoll(int rollValue) {
        rolls.add(rollValue);
    }

    public List<Integer> getRolls() {
        return rolls;
    }
}