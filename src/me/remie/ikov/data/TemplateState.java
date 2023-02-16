package me.remie.ikov.template.data;

import me.remie.ikov.template.TemplateScript;
import simple.api.ClientAccessor;
import simple.api.ClientContext;

import java.util.Random;

/**
 * Created by Reminisce on Feb 15, 2023 at 10:30 PM
 *
 * @author Seth Davis <sethdavis321@gmail.com>
 * @Discord Reminisce#1707 <138751815847116800>
 */
public class TemplateState extends ClientAccessor<ClientContext> {

    private final TemplateScript script;

    private final Random random = new Random();

    private boolean cursesPrayerEnabled = false;

    public TemplateState(TemplateScript script) {
        super(script.ctx);
        this.script = script;
    }

    /**
     * Gets whether the player has the curses prayers enabled.
     * @return true if the player has the curses prayers enabled, false otherwise
     */
    public boolean isCursesPrayerEnabled() {
        return cursesPrayerEnabled;
    }

    /**
     * Sets whether the player has the curses prayers enabled.
     * @param cursesEnabled true if the player has the curses prayers enabled, false otherwise
     */
    public void setCursesPrayerEnabled(final boolean cursesEnabled) {
        this.cursesPrayerEnabled = cursesEnabled;
    }

    /**
     * Returns a random number between min and max, normally distributed around the mean.
     * @param min minimum value
     * @param max maximum value
     * @return random number
     */
    public int nextGaussianRandom(int min, int max) {
        int range = max - min;
        int mean = min + range / 2;
        int deviation = range / 6; // 99.7% of values will fall within the range [mean - 3*deviation, mean + 3*deviation]
        return (int) (random.nextGaussian() * deviation + mean);
    }

}
