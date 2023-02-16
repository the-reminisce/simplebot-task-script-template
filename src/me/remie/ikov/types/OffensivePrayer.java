package me.remie.ikov.template.types;

import simple.api.filters.SimplePrayers;

/**
 * Created by Reminisce on Feb 15, 2023 at 10:30 PM
 *
 * @author Seth Davis <sethdavis321@gmail.com>
 * @Discord Reminisce#1707 <138751815847116800>
 */
public enum OffensivePrayer {

    NONE("None", null),
    EAGLE_EYE("Eagle eye", SimplePrayers.Prayers.EAGLE_EYE),
    MYSTIC_MIGHT("Mystic might", SimplePrayers.Prayers.MYSTIC_MIGHT),
    CHIVALRY("Chivalry", SimplePrayers.Prayers.CHIVALRY),
    PIETY("Piety", SimplePrayers.Prayers.PIETY),
    RIGOUR("Rigour", SimplePrayers.Prayers.RIGOUR),
    AUGURY("Augury", SimplePrayers.Prayers.AUGURY),
    TURMOIL("Turmoil", SimplePrayers.Prayers.TURMOIL),
    ANGUISH("Anguish", SimplePrayers.Prayers.ANGUISH),
    TORMENT("Torment", SimplePrayers.Prayers.TORMENT);

    private final String name;

    private final SimplePrayers.Prayers prayer;

    OffensivePrayer(String name, SimplePrayers.Prayers prayer) {
        this.name = name;
        this.prayer = prayer;
    }

    /**
     * Gets the name of the prayer.
     *
     * @return The name of the prayer.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the prayer.
     *
     * @return
     */
    public SimplePrayers.Prayers getPrayer() {
        return prayer;
    }

    /**
     * Gets the name of the prayer. This is used for the combobox.
     *
     * @return The name of the prayer.
     */
    @Override
    public String toString() {
        return name;
    }

}
