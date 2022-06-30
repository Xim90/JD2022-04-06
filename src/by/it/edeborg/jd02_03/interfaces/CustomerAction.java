package by.it.edeborg.jd02_03.interfaces;

import by.it.edeborg.jd02_03.organization.SomethingGood;

public interface CustomerAction {
    void enteredStore();//вошел в магазин (мгновенно)

    SomethingGood chooseGood();//выбрал товар (от 0,5 до 2 секунд)

    void goToQueue();

    void goOut(); //отправился на выход(мгновенно)
}
