import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Demo {
    /*
long $TIME$ = System.nanoTime();
try {
    $SELECTION$
} finally {
    $TIME$ = System.nanoTime() - $TIME$;
    System.out.printf("$TIME$ = %dms%n", ($TIME$/1_000_000));
}
     */
    private final String name;
    private final String season;

    public Demo(String name, int season) {
        this.name = name;
        this.season = switch (season) {
            case 0 -> "summer";
            case 1 -> "autumn";
            case 2 -> "winter";
            case 3 -> "spring";
            default -> throw new IllegalArgumentException("Invalid season: " + season);
        };
    }

    @Override
    public String toString() {
        return "Demo{" +
                "name='" + name + '\'' +
                ", season='" + season + '\'' +
                '}';
    }

    public static void main(String... args) {
        System.out.println(); // sout
        System.out.println("args = " + Arrays.toString(args)); // soutv
        System.out.println("Demo.main"); // soutm
        System.out.println("args = " + Arrays.deepToString(args)); // soutp
        Demo devStaff = new Demo("DevStaff", 2);
        System.out.println("devStaff = " + devStaff);

        for (int i = 0; i < 10; i++) { // fori
            for (int j = 0; j < 20; j++) {
                System.out.println("i * j = " + i * j);
            }
        }

        List<String> cities = List.of("Chania", "Rethymnon", "Heraklion");
        for (String city : cities) { // iter
            System.out.println("city = " + city);
        }

        long time = System.nanoTime();
        try {
            BigInteger fact200k = IntStream.rangeClosed(1, 2_000_000)
                    .parallel()
                    .mapToObj(BigInteger::valueOf)
                    .reduce(BigInteger.ONE, BigInteger::multiply);
            System.out.println("fact200k.bitLength() = " + fact200k.bitLength());
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }
    }


}
