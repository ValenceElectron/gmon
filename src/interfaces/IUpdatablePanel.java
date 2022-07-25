package interfaces;

import java.time.LocalTime;

public interface IUpdatablePanel {

    void update();

    void update(LocalTime peak);

    void peak();
}
