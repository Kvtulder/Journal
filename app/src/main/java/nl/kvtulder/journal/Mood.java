package nl.kvtulder.journal;

/**
 * Created by kasper on 2/24/18.
 */

public enum Mood {
    HAPPY,
    SAD,
    AWESOME,
    RELAXED,
    CRYING;

    // Returns the resource id of a smiley corresponding to the chosen mood
    public int getSmiley() {
        switch (this) {
            case HAPPY:
                return R.drawable.happy;
            case SAD:
                return R.drawable.sad;
            case CRYING:
                return R.drawable.crying;
            case AWESOME:
                return R.drawable.awesome;
            case RELAXED:
                return R.drawable.relaxed;
            default:
                return R.drawable.in_love;
        }
    }
}


