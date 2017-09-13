# -*- mode: Shell-script -*-
. log.sh

log.begin "[lib]: load ..."

lib.f() {
    log.print "I don't do much !"
}

log.print "[lib]: load done.."

log.end "[lib]: load done"
