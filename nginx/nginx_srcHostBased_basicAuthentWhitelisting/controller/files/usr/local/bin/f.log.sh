declare _b='f.begin $FUNCNAME "$@"'
declare _e='f.end $FUNCNAME "$@"'

f.init() {
    set -euo pipefail
}

u.info( ){
    echo "$@" 1>&2
}
f.log() {
    if ! ${f_log_enabled:-true}; then
        return
    fi
    if [[ "$@" == "-" ]]; then
        sed -r "s/^.*$/${indent}- &/" 1>&2
    elif [[ $# -eq 2 ]]; then
        u.info "${indent:-}- $1: $2"
    else
        u.info "${indent:-}$@"
    fi
}
f.log_res() {
    tee >(sed -r "s/.*/${indent}=> &/" && echo  1>&2)
}
f.begin(){
    set -euo pipefail
    declare fName="${1}"
    shift 1
    declare fArgs="$@"
    declare msg
    msg="- $fName"$'\t'"$fArgs"
    msg="$(x.toLine <<< ${msg})"
    if ${f_log_cwd:-false}; then
        msg+=", cwd=$(pwd)"
    fi
    f.log "${msg}"
    export indent="${indent:-}  "
}
f.end() {
        export indent="$(sed -r 's/..//' <<< "${indent:-}")"
}
f.end.and-return() {
    declare rc="${1}"
    shift 1
    f.end
    return $rc
}

