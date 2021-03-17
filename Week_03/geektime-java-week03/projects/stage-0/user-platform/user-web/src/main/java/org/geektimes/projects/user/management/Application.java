package org.geektimes.projects.user.management;

public class Application implements ApplicationMBean {

    private String applicationName;

    public Application(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public String getApplicationName() {
        return applicationName;
    }

    @Override
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

}