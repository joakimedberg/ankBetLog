package nackademin.controller;

import nackademin.model.Model;
import nackademin.view.View;


public abstract class Controller {

    protected abstract void setView(View view);
    protected abstract void setModel(Model model);

}