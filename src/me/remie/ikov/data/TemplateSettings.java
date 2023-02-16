package me.remie.ikov.template.data;

import me.remie.ikov.template.types.OffensivePrayer;

/**
 * Created by Reminisce on Feb 15, 2023 at 10:30 PM
 *
 * @author Seth Davis <sethdavis321@gmail.com>
 * @Discord Reminisce#1707 <138751815847116800>
 */
public class TemplateSettings {

    private final OffensivePrayer offensivePrayer;

    public TemplateSettings(final OffensivePrayer offensivePrayer) {
        this.offensivePrayer = offensivePrayer;
    }

    public OffensivePrayer getOffensivePrayer() {
        return offensivePrayer;
    }

}
