plugins {
    `java-platform`
}

dependencies {
    constraints {
        api(project(":core"))
        api(project(":feature:user"))
        api(project(":testing"))
    }
}
