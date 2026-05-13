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
        api(project(":shared:foundation:feature:securityapi"))
        api(project(":shared:foundation:feature:settingsapi"))
        api(project(":shared:foundation:feature:auditapi"))
    }
}