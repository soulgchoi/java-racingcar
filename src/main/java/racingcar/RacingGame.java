package racingcar;

import java.util.List;
import utils.RandomUtils;

public class RacingGame {
    private static final int MINIMUM_RANDOM_RPM = 0;
    private static final int MAXIMUM_RANDOM_RPM = 9;

    private List<Car> cars;
    private int trial;
    private ExceptionHandler exceptionHandler = new ExceptionHandler();

    public void race() {
        prepareCars();
        prepareTrial();
        play();
        //play
        //finish
    }

    private void prepareCars() {
        String input = RacingGameView.requestCars();
        cars = exceptionHandler.setCars(input);
    }

    private void prepareTrial() {
        String input = RacingGameView.requestTrial();
        trial = exceptionHandler.setTrial(input);
    }

    private void play() {
        RacingGameView.printNewLine();
        RacingGameView.printResult();
        raceByTrial();
    }

    private void raceByTrial() {
        for (int t = 0; t < trial; t++) {
            raceByCar();
        }
    }

    private void raceByCar() {
        for (Car car : cars) {
            int currentRpm = RandomUtils.nextInt(MINIMUM_RANDOM_RPM, MAXIMUM_RANDOM_RPM);
            car.tryToMove(currentRpm);
            RacingGameView.printCarPosition(car);
        }
        RacingGameView.printNewLine();
    }
}
