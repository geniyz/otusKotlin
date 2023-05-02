package site.geniyz.otus.mappers.v1.exceptions

import site.geniyz.otus.common.models.AppCommand

class UnknownAppCommand(command: AppCommand) : Throwable("Wrong command $command at mapping toTransport stage")
