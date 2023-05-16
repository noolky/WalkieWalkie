package com.example.walkiewalkie.AchievementStepActivity

class StepEntity() {
    var curDate: String? = null //date of th day
    var steps: String? = null   //step on the day

    constructor(curDate: String, steps: String) : this() {
        this.curDate = curDate
        this.steps = steps
    }

    override fun toString(): String {
        return "StepEntity{" +
                "curDate='" + curDate + '\'' +
                ", steps=" + steps +
                '}'
    }
}