package com.bogdan.RecyclerVewTest;

class Todo {

    private int id;
    private String task;
    private boolean isSelected = false;

    private static int f = 0;

    public Todo( String task) {
        id = ++f;
        this.task = task;
    }

    public Todo(int id, String task) {
        this.id = id;
        f = id;
        this.task = task;
    }

    public Todo(int id, String task, boolean isSelected) {
        this.id = id;
        f = id;
        this.task = task;
        this.isSelected = isSelected;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", task='" + task + '\'' +
                '}';
    }

}
