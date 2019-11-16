package com.fencelive.comparator;

import com.fencelive.model.viewModel.ClassFencerViewModel;

import java.util.Comparator;

public class ClassFencerComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {

        int result;

        ClassFencerViewModel fencer1 = (ClassFencerViewModel)o1;
        ClassFencerViewModel fencer2 = (ClassFencerViewModel)o2;

        result = Double.compare(fencer1.getInd1(), fencer2.getInd1());

        if (result == 0){

            result = Integer.compare(fencer1.getInd2(), fencer2.getInd2());

            if (result == 0){
                result = Integer.compare(fencer1.getInd3(), fencer2.getInd3());
            }
        }

        return result;
    }
}
