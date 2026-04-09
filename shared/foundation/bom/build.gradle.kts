plugins {
    `java-platform`
}

dependencies {
    constraints {
        api(project(":shared:foundation:core:common"))
        api(project(":shared:foundation:core:security"))
        api(project(":shared:foundation:core:settings"))
        api(project(":shared:foundation:core:audit"))
        api(project(":shared:foundation:feature:user"))
        api(project(":shared:foundation:feature:security-api"))
        api(project(":shared:foundation:feature:settings-api"))
        api(project(":shared:foundation:feature:audit-api"))
    }
}