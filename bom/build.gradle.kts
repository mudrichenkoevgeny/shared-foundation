plugins {
    `java-platform`
}

dependencies {
    constraints {
        api(project(":core:common"))
        api(project(":core:security"))
        api(project(":core:settings"))
        api(project(":core:audit"))
        api(project(":core:storage"))
        api(project(":feature:user"))
        api(project(":testing"))
    }
}
