package com.lambferret.game.constant;

public enum Rank {

    /**
     * 훈련병
     */
    RECRUIT(20),
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
    STAFF_SERGEANT(120),
    /**
     * 중사
     */
    SERGEANT_FIRST_CLASS(140),
    /**
     * 상사
     */
    FIRST_SERGEANT(160),
    /**
     * 원사
     */
    SERGENT_MAJOR(Integer.MAX_VALUE),
    /**
     * 주임원사
     */
    COMMAND_SERGEANT_MAJOR(Integer.MAX_VALUE),
    /**
     * 육군주임원사
     */
    SERGEANT_MAJOR_OF_THE_ARMY(Integer.MAX_VALUE),
    /**
     * 준위
     */
    MASTER_WARRANT_OFFICER(Integer.MAX_VALUE),
    /**
     * 소위
     */
    SECOND_LIEUTENANT(Integer.MAX_VALUE),
    /**
     * 중위
     */
    FIRST_LIEUTENANT(Integer.MAX_VALUE),
    /**
     * 대위
     */
    CAPTAIN(Integer.MAX_VALUE),
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
