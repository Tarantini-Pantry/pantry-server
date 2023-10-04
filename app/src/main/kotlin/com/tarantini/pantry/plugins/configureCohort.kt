package com.tarantini.pantry.plugins

import com.sksamuel.cohort.endpoints.CohortConfiguration
import com.tarantini.pantry.app.Dependencies
import com.tarantini.pantry.app.livenessProbes
import com.tarantini.pantry.app.readinessProbes
import com.tarantini.pantry.app.startupProbes

fun CohortConfiguration.configureCohort(dependencies: Dependencies) {
   gc = true
   jvmInfo = true
   sysprops = true
   threadDump = true
   heapDump = true
   healthcheck("/startup", startupProbes(dependencies.ds))
   healthcheck("/liveness", livenessProbes())
   healthcheck("/readiness", readinessProbes())
}
