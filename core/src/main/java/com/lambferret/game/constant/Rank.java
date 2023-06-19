package com.lambferret.game.constant;

public enum Rank {

    /**
     * 훈련병
     */
    RECRUIT(30),
    /**
     * 이등병
     */
    PRIVATE(35),
    /**
     * 일등병
     */
    PRIVATE_FIRST_CLASS(45),
    /**
     * 상병
     */
    CORPORAL(60),
    /**
     * 병장
     */
    SERGEANT(80),
    /**
     * 하사
     */
    STAFF_SERGEANT(150),
    /**
     * 중사
     */
    SERGEANT_FIRST_CLASS(160),
    /**
     * 상사
     */
    FIRST_SERGEANT(170),
    /**
     * 원사
     */
    SERGENT_MAJOR(180),
    /**
     * 주임원사
     */
    COMMAND_SERGEANT_MAJOR(185),
    /**
     * 육군주임원사
     */
    SERGEANT_MAJOR_OF_THE_ARMY(200),
    /**
     * 준위
     */
    MASTER_WARRANT_OFFICER(300),
    /**
     * 소위
     */
    SECOND_LIEUTENANT(340),
    /**
     * 중위
     */
    FIRST_LIEUTENANT(380),
    /**
     * 대위
     */
    CAPTAIN(500),
    /**
     * 소령
     */
    MAJOR(Integer.MAX_VALUE),
    /**
     * 중령
     */
    LIEUTENANT_COLONEL(Integer.MAX_VALUE),
    /**
     * 대령
     */
    COLONEL(Integer.MAX_VALUE),
    /**
     * 준장
     */
    BRIGADIER_GENERAL(Integer.MAX_VALUE),
    /**
     * 소장
     */
    MAJOR_GENERAL(Integer.MAX_VALUE),
    /**
     * 중장
     */
    LIEUTENANT_GENERAL(Integer.MAX_VALUE),
    /**
     * 대장
     */
    GENERAL(Integer.MAX_VALUE),
    ;
    final int salary;

    Rank(int salary) {
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }
}
