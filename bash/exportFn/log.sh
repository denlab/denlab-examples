# -*- mode: Shell-script -*-

log.print() {
    echo  "${indent:-}- $@" 1>&2
}

log.indent.dec() {
    export indent="${indent:-}  "
}

log.begin() {
    log.indent.dec
    log.print "$@" 
}

log.indent.inc() {
    export indent="$(jq -n 'env.indent//\"\" | .[2:]')"
}

log.end() {
    log.print "$@" 
    log.indent.inc
}

log.fn.begin() {
    log.begin "$1 ..." 
}

log.fn.end() {
    log.indent.dec
}
