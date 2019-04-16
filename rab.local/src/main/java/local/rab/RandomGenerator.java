package local.rab;

import java.util.Random;

public class RandomGenerator {
	Random random;
	
	public RandomGenerator() {
		random = new Random();
	}

	public Random getRandom() {
		return random;
	}
}
