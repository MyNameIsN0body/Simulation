package com.petproject.simulation.simulation;

import com.petproject.simulation.simulation.actions.Actions;
import com.petproject.simulation.simulation.actions.InitAction;
import com.petproject.simulation.world.Map;

import java.util.ArrayList;
import java.util.List;
/*
    Главный класс приложения, включает в себя:

    Карту
    Счётчик ходов
    Рендерер поля
    Actions - список действий, исполняемых перед стартом симуляции или на каждом ходу (детали ниже)

    Методы:

    nextTurn() - просимулировать и отрендерить один ход
    startSimulation() - запустить бесконечный цикл симуляции и рендеринга
    pauseSimulation() - приостановить бесконечный цикл симуляции и рендеринга

     */
public class Simulation {
    Map map;
    int currentTurn;
    private List<Actions> initActions = new ArrayList<>();
    private List<Actions> turnActions = new ArrayList<>();

    public Simulation(int length, int width) {
        this.map = new Map(length,width);
        // Создаем команды инициализации
        initActions.add(new InitAction(map,10,18,18,10,10));
        // Создаем команды для каждого хода
//        turnActions.add(new MoveAction(map));
//        turnActions.add(new GrassGrowthAction(map));
//        turnActions.add(new EatAction(map));
//        turnActions.add(new HuntAction(map));
    }




    public void initialize() {
        for (Actions action: initActions) {
            action.execute();
        }
    }
}
