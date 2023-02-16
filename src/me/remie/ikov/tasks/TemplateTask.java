package me.remie.ikov.template.tasks;

import me.remie.ikov.template.TemplateScript;
import me.remie.ikov.template.data.TemplateSettings;
import me.remie.ikov.template.data.TemplateState;
import simple.api.script.task.Task;

/**
 * Created by Reminisce on Feb 15, 2023 at 10:30 PM
 *
 * @author Seth Davis <sethdavis321@gmail.com>
 * @Discord Reminisce#1707 <138751815847116800>
 */
public abstract class TemplateTask extends Task {

    public final TemplateScript script;

    public TemplateTask(TemplateScript script) {
        super(script.ctx);
        this.script = script;
    }

    /**
     * Gets the script instance.
     *
     * @return the script instance.
     */
    public TemplateScript getScript() {
        return script;
    }

    /**
     * Gets the settings instance. This holds all the settings for the script.
     *
     * @return the settings instance.
     */
    public TemplateSettings getSettings() {
        return script.getSettings();
    }

    /**
     * Gets the state instance. This holds all the state specific variables for the script.
     *
     * @return the state instance.
     */
    public TemplateState getState() {
        return script.getState();
    }

    /**
     * Sets the status of the script. This is a helper since it's not exposed in the task class.
     *
     * @param status the status to set.
     */
    public void setStatus(String status) {
        script.setScriptStatus(status);
    }

}
