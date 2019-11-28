package com.fencelive.model.viewModel;

import java.util.List;

public class TableauFightsForm {

    private List<GroupFightsViewModel> tableauFights;

    public TableauFightsForm() {
    }

    public TableauFightsForm(List<GroupFightsViewModel> tableauFights) {
        this.tableauFights = tableauFights;
    }

    public List<GroupFightsViewModel> getTableauFights() {
        return tableauFights;
    }

    public void setTableauFights(List<GroupFightsViewModel> tableauFights) {
        this.tableauFights = tableauFights;
    }
}
