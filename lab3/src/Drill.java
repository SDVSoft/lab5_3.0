public class Drill extends Item implements Applicable {
    private static final String VERB = "просверлить дыру в";
    private static final String DESCRIPTION = "Дрель";

    Drill() {super(Drill.DESCRIPTION);}

    protected Drill(String description) {super(description);}

    public String getVerb() {return VERB;}

    public Item apply(Item it) {
        if (it instanceof PossibleToDrill) {
            System.out.println("В \"" + it + "\" просверлена дыра.");
            return ((PossibleToDrill) it).makeHole();
        }
        System.out.println("Не удалось " + VERB + " \"" + it + "\".");
        return it;
    }
}
