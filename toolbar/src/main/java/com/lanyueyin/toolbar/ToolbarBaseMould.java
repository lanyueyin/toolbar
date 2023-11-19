package com.lanyueyin.toolbar;


public abstract class ToolbarBaseMould implements ToolbarBaseMouldImp {
    private Toolbar toolbar;

    public ToolbarBaseMould(Toolbar toolbar) {
        this.toolbar = toolbar;
        toolbar.setMould(this);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
