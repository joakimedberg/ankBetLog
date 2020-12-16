package nackademin.controller;

import nackademin.model.Model;
import nackademin.view.View;
interface Controller {

    void setView(View view);
    void setModel(Model model);

}