package com.petproject.simulation.simulation;

public final class GameMessenger {
    private GameMessenger() {
    }

    private final static String START_BANNER = """
                \u001B[36m╔═════════════════════════════════════════════════════════════════════════════╗\u001B[0m
                \u001B[36m║\u001B[33m                                  СИМУЛЯЦИЯ                                  \u001B[36m║\u001B[0m
                \u001B[36m╠═════════════════════════════════════════════════════════════════════════════╣\u001B[0m
                \u001B[36m║ Это симуляция 2D‑мира, где травоядные ищут траву, а хищники охотятся на     ║\u001B[0m
                \u001B[36m║ травоядных. Каждый ход существа принимают решения: двигаться, питаться или  ║\u001B[0m
                \u001B[36m║ атаковать. Ваша задача — наблюдать за динамикой экосистемы и пробовать      ║\u001B[0m
                \u001B[36m║ находить баланс между видами.                                               ║\u001B[0m
                \u001B[36m╚═════════════════════════════════════════════════════════════════════════════╝\u001B[0m
            """;

    private final static String SELECT_MODE_TEXT = """
                \u001B[36m╔══════════════════════════════════════════════╗\u001B[0m
                \u001B[36m║\u001B[33m            ВЫБОР РЕЖИМА СИМУЛЯЦИИ            \u001B[36m║\u001B[0m
                \u001B[36m╠══════════════════════════════════════════════╣\u001B[0m
                \u001B[36m║  \u001B[34m1.\u001B[0m \u001B[36mПошаговый режим (ручное управление)      ║\u001B[0m
                \u001B[36m║  \u001B[32m2.\u001B[0m \u001B[36mАвтоматический режим (бесконечный цикл)  ║\u001B[0m
                \u001B[36m╚══════════════════════════════════════════════╝\u001B[0m
            Укажите режим (\u001B[34m1\u001B[0m или \u001B[32m2\u001B[0m): 
            """;

    private final static String AUTO_MENU = """
                                    \u001B[36m╔═══════════════════════════════════╗\u001B[0m
                                    \u001B[36m║\u001B[33m     УПРАВЛЕНИЕ СИМУЛЯЦИЕЙ         \u001B[36m║\u001B[0m
                                    \u001B[36m╠═══════════════════════════════════╣\u001B[0m
                                    \u001B[36m║\u001B[32m  run/r       Старт симуляции      \u001B[36m║\u001B[0m
                                    \u001B[36m║\u001B[34m  pause/p    Пауза                 \u001B[36m║\u001B[0m
                                    \u001B[36m║\u001B[36m  step/s     Сделать шаг вручную   \u001B[36m║\u001B[0m  
                                    \u001B[36m║\u001B[34m  menu/m     Показать меню         \u001B[36m║\u001B[0m  
                                    \u001B[36m║\u001B[31m  exit/e     Выход                 \u001B[36m║\u001B[0m  
                                    \u001B[36m╚═══════════════════════════════════╝\u001B[0m
            """;

    private final static String START_INFO = "\uD83D\uDE80 Симуляция запущена";
    private final static String STEP_INFO = "\uD83D\uDEB6 Сделан ход";
    private final static String PAUSE_INFO = "⌛ Пауза";
    private final static String EXIT_INFO = "\uD83D\uDEAB Выход...";

    public static void displayIntro() {
        System.out.println("\n".repeat(3) + START_BANNER);
    }

    public static void showAutoModeMenu() {
        System.out.println(AUTO_MENU);
    }

    public static void showInfoStep() {
        System.out.println(STEP_INFO);
    }

    public static void showInfoPause() {
        System.out.println(PAUSE_INFO);
    }

    public static void showInfoStart() {
        System.out.println(START_INFO);
    }

    public static void showInfoExit() {
        System.out.println(EXIT_INFO);
    }

    public static void showStatus(GameStats stats) {

        System.out.printf("""
                                  \u001B[33m Состояние игры:\u001B[0m
                      ⏱️ Ход номер: \u001B[32m%-2d\u001B[0m     🌱 Травы:      \u001B[32m%-2d\u001B[0m
                      🦖 Хищников:  \u001B[32m%-2d\u001B[0m     🦌 Травоядных: \u001B[32m%-2d\u001B[0m
                """, stats.getTurn(), stats.getGrass(), stats.getPredators(), stats.getHerbivore());
        System.out.println("\n".repeat(3));
    }

}
