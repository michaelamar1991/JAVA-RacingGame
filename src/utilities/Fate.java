package utilities;

import java.util.Random;

/**
 * @version 1.2 16 May 2018
 * @author Aviv Yehuda, Michael Amar
 * @ID 304797376, 308104215
 * @Campus Beer-Sheva
 */
public class Fate {

	private static Random rand = new Random();

	public static boolean breakDown(double failureProbability) {
		return rand.nextFloat() <= failureProbability;
	}
	public static boolean generateFixable() {
		return rand.nextInt(10) > 7;
	}

	public static Mishap generateMishap() {
		return new Mishap(generateFixable(), generateTurns(), generateReduction());
	}

	private static float generateReduction() {
		return rand.nextFloat();
	}

	private static int generateTurns() {
		return rand.nextInt(5) + 1;
	}

	public static void setSeed(int seed) {
		rand.setSeed(seed);
	}

}
