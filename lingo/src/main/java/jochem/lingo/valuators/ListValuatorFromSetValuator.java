package jochem.lingo.valuators;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jochem.mytools.Valuator;

public class ListValuatorFromSetValuator<T> implements Valuator<List<T>> {

    private Valuator<Set<T>> setValuator;

    public ListValuatorFromSetValuator(Valuator<Set<T>> setValuator) {
        this.setValuator = setValuator;
    }

    @Override
    public double valuate(List<T> list) {
        return setValuator.valuate(new HashSet<T>(list));
    }

}
