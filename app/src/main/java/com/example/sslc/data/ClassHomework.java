package com.example.sslc.data;

public class ClassHomework {

    String title;
    String script;
    String deadline;

    public ClassHomework(
            String title,
            String script,
            String deadline
    ) {
        this.title = title;
        this.script = script;
        this.deadline = deadline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
