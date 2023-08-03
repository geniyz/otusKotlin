package site.geniyz.otus.common.exceptions

import site.geniyz.otus.common.models.AppLock

class RepoConcurrencyException(expectedLock: AppLock, actualLock: AppLock?): RuntimeException(
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)
