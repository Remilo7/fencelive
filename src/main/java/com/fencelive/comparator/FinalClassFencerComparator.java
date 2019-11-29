package com.fencelive.comparator;

import com.fencelive.model.viewModel.FinalClassFencerModel;

import java.util.Comparator;

public class FinalClassFencerComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {

        int result;

        FinalClassFencerModel fencer1 = (FinalClassFencerModel)o1;
        FinalClassFencerModel fencer2 = (FinalClassFencerModel)o2;

        result = Double.compare(fencer1.getTableau(), fencer2.getTableau());

        if (result == 0){

            result = Integer.compare(fencer1.getPlace(), fencer2.getPlace());
        }

        return result;
    }
}
